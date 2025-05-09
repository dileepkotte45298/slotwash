package com.slotwash.metrics;

import static com.slotwash.Constants.MONGO_OP_COUNTERS;
import static com.slotwash.Constants.OP;
import static com.slotwash.Constants.INSERT;
import static com.slotwash.Constants.QUERY;
import static com.slotwash.Constants.UPDATE;
import static com.slotwash.Constants.DELETE;
import static com.slotwash.Constants.MEGABYTES_GAUGE_UNIT;
import static com.slotwash.Constants.COUNT_GAUGE_UNIT;
import static com.slotwash.Constants.RESIDENT;
import static com.slotwash.Constants.TYPE;
import static com.slotwash.Constants.AVAILABLE;
import static com.slotwash.Constants.UPTIME_MILLIS;
import static com.slotwash.Constants.OPCOUNTERS;
import static com.slotwash.Constants.THRESHOLD;
import static com.slotwash.Constants.CURRENT;
import static com.slotwash.Constants.USED;
import static com.slotwash.Constants.TOTAL;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import java.time.Instant;
import java.util.Arrays;
import org.bson.Document;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.stereotype.Component;

@Component
public class Database implements HealthIndicator {

  private MongoDatabase db;

  private Instant collectionTime;
  private Document stats = null;

  private Document dbstats;

  public Database(MeterRegistry registry, MongoClient mongoClient, MongoProperties properties) {

    registry
        .more()
        .counter(MONGO_OP_COUNTERS, Arrays.asList(Tag.of(OP, INSERT)), this, Database::opInserts);
    registry
        .more()
        .counter(MONGO_OP_COUNTERS, Arrays.asList(Tag.of(OP, QUERY)), this, Database::opQuery);
    registry
        .more()
        .counter(MONGO_OP_COUNTERS, Arrays.asList(Tag.of(OP, UPDATE)), this, Database::opUpdate);
    registry
        .more()
        .counter(MONGO_OP_COUNTERS, Arrays.asList(Tag.of(OP, DELETE)), this, Database::opDelete);

    registry.more().counter("mongo_uptime", Arrays.asList(), this, Database::uptime);

    Gauge.builder("mongo_memory", this, Database::residentMem)
        .description("Mongo memory usage")
        .baseUnit(MEGABYTES_GAUGE_UNIT)
        .tags(TYPE, RESIDENT)
        .register(registry);

    Gauge.builder("mongo_connections", this, Database::connectionCount)
        .description("Mongo connection count")
        .baseUnit(COUNT_GAUGE_UNIT)
        .tags(TYPE, CURRENT)
        .register(registry);

    Gauge.builder("mongo_connections", this, Database::connectionCount)
        .description("Mongo connection count")
        .baseUnit(COUNT_GAUGE_UNIT)
        .tags(TYPE, CURRENT)
        .register(registry);

    Gauge.builder("mongo_disk", this, Database::fsTotal)
        .description("Mongo total disk space")
        .baseUnit(MEGABYTES_GAUGE_UNIT)
        .tags(TYPE, TOTAL)
        .register(registry);

    Gauge.builder("mongo_disk", this, Database::fsUsed)
        .description("Mongo used disk space")
        .baseUnit(MEGABYTES_GAUGE_UNIT)
        .tags(TYPE, USED)
        .register(registry);

    db = mongoClient.getDatabase(properties.getDatabase());
  }

  private void getInfo() {
    if (stats == null
        || collectionTime == null
        || collectionTime.isBefore(Instant.now().minusSeconds(5))) {
      try {
        stats = db.runCommand(new Document("serverStatus", 1));
      } catch (Exception e) {
        stats = new Document();
      }
      try {
        dbstats = db.runCommand(new Document("dbStats", 1).append("scale", 1024 * 1024));
      } catch (Exception e) {
        dbstats = new Document();
      }
      collectionTime = Instant.now();
    }
  }

  Document getStats() {
    getInfo();
    return stats;
  }

  Document getDbStats() {
    getInfo();
    return dbstats;
  }

  long opInserts(Database this) {
    Document statistics = getStats().get(OPCOUNTERS, Document.class);
    if (statistics == null) return 0L;
    Number n = statistics.get(INSERT, Number.class);
    return n.longValue();
  }

  long opQuery(Database this) {
    Document statistics = getStats().get(OPCOUNTERS, Document.class);
    if (statistics == null) return 0L;
    Number n = statistics.get(QUERY, Number.class);
    return n.longValue();
  }

  long opUpdate(Database this) {
    Document statistics = getStats().get(OPCOUNTERS, Document.class);
    if (statistics == null) return 0L;
    Number n = statistics.get(UPDATE, Number.class);
    return n.longValue();
  }

  long opDelete(Database this) {
    Document statistics = getStats().get(OPCOUNTERS, Document.class);
    if (statistics == null) return 0L;
    Number n = statistics.get(DELETE, Number.class);
    return n.longValue();
  }

  long uptime(Database this) {
    Document statistics = getStats();
    if (!statistics.containsKey(UPTIME_MILLIS)) return 0L;
    return statistics.getLong(UPTIME_MILLIS);
  }

  long virtualMem(Database this) {
    Document statistics = getStats().get("mem", Document.class);
    if (statistics == null) return 0L;
    Number n = statistics.get("virtual", Number.class);
    return n.longValue();
  }

  long residentMem(Database this) {
    Document statistics = getStats().get("mem", Document.class);
    if (statistics == null) return 0L;
    Number n = statistics.get(RESIDENT, Number.class);
    return n.longValue();
  }

  long connectionCount(Database this) {
    Document statistics = getStats().get("connections", Document.class);
    if (statistics == null) return 0L;
    Number n = statistics.get(CURRENT, Number.class);
    return n.longValue();
  }

  double fsUsed(Database this) {
    if (getDbStats().containsKey("fsUsedSize")) {
      return getDbStats().getDouble("fsUsedSize");
    } else {
      return 0.0D;
    }
  }

  double fsTotal(Database this) {
    if (getDbStats().containsKey("fsTotalSize")) {
      return getDbStats().getDouble("fsTotalSize");
    } else {
      return 0.0D;
    }
  }

  /** Alert if <5 GB available */
  @Override
  public Health health() {

    Double avail = Math.round(fsTotal() * 100) / 100.0;
    Double used = Math.round(fsUsed() * 100) / 100.0;
    Double threshold = avail - 5 * 1024;
    if (used == 0.0D) {
      return Health.up()
          .withDetail(AVAILABLE, avail)
          .withDetail("Used", used)
          .withDetail(THRESHOLD, threshold)
          .build();
    }

    if (threshold < used) {

      return Health.down()
          .withDetail(AVAILABLE, avail)
          .withDetail("Used", used)
          .withDetail(THRESHOLD, threshold)
          .build();
    } else {
      return Health.up()
          .withDetail(AVAILABLE, avail)
          .withDetail("Used", used)
          .withDetail(THRESHOLD, threshold)
          .build();
    }
  }
}

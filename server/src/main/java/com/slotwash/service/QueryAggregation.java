package com.slotwash.service;

import java.io.StringWriter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.bson.BsonArray;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.mongodb.client.AggregateIterable;

@Service
public class QueryAggregation {

    @Autowired VelocityEngine velocityEngine;
    @Autowired MongoTemplate mongoTemplate;

    private static final Logger logger = LoggerFactory.getLogger(QueryAggregation.class);

    public String getRenderedQuery(String queryName, Map<String, Object> properties) {
        VelocityContext velocityContext = new VelocityContext();
        properties.entrySet().forEach(entry -> {
            velocityContext.put(entry.getKey(), entry.getValue());
        });
        StringBuilder filePath = new StringBuilder("/templates/").append(queryName).append(".vm");
        Template template = velocityEngine.getTemplate(filePath.toString());
        StringWriter writer = new StringWriter();
        template.merge(velocityContext, writer);
        return writer.toString();
    }

    public List<Document> runAggregationQuery(String query, Map<String, Object> props, String collection) {
        String renderedQuery = getRenderedQuery(query, props);
        logger.info( "Rendered Query on Collection {} is : /n {}",query+collection , renderedQuery);
        BsonArray docArray = BsonArray.parse(renderedQuery);
        List<Bson> aggregate = docArray.getValues().stream().map(v -> v.asDocument()).collect(Collectors.toList());
        AggregateIterable<Document> result = mongoTemplate
                .getCollection(collection)
                .aggregate(aggregate)
                .allowDiskUse(true)
                .batchSize(100);
        List<Document> res = StreamSupport.stream(result.spliterator(), false).collect(Collectors.toList());
        return  res;
    }

}

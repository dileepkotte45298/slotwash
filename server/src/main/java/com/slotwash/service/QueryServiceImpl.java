package com.slotwash.service;

import java.io.StringWriter;
import java.util.HashMap;
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
import com.mongodb.client.AggregateIterable;

public class QueryServiceImpl implements QueryService{

    private static final Logger logger = LoggerFactory.getLogger(QueryServiceImpl.class);

    @Autowired VelocityEngine velocityEngine;
    @Autowired MongoTemplate mongoTemplate;

    @Override
    public String renderQueryTemplate(String query, Map<String, Object> props) {
        VelocityContext velocityContext = new VelocityContext();
        props.entrySet().forEach(entry -> velocityContext.put(entry.getKey(), entry.getValue()));
        StringBuilder filePath = new StringBuilder("/templates/").append(query).append(".vm");
        Template template = velocityEngine.getTemplate(filePath.toString());
        StringWriter writer = new StringWriter();
        template.merge(velocityContext, writer);
        return writer.toString();
    }



    @Override
    public Map<String, Object> getBaseProperties() {
        Map<String, Object> baseProperties = new HashMap<>();
        return baseProperties;
    }

    @Override
    public List<Document> executeResource(String query, String collectioName, Map<String, Object> props) {
        
        String renderedQuery = renderQueryTemplate(query, props);
        logger.debug( "Rendered Query on Collection {} is : /n {}",query+collectioName , renderedQuery);

        BsonArray docArray = BsonArray.parse(renderedQuery);
        List<Bson> aggregate = docArray.getValues().stream().map(v -> v.asDocument()).collect(Collectors.toList());

        AggregateIterable<Document> result = mongoTemplate
                .getCollection(collectioName)
                .aggregate(aggregate)
                .allowDiskUse(true)
                .batchSize(100);

        return StreamSupport.stream(result.spliterator(), false).collect(Collectors.toList());
    }
    
}

package com.slotwash.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

import org.bson.Document;

public interface QueryService {

    String renderQueryTemplate(String query, Map<String,Object> props);

    Map<String,Object> getBaseProperties();

    List<Document> executeResource(String query , String collectioName, Map<String,Object> props);

    static long toTimestamp(LocalDateTime localDateTime, ZoneId ZoneId){
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId);
        return zonedDateTime.toInstant().toEpochMilli();
    }

    
} 
package com.slotwash.config;

import java.util.Properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(ignoreUnknownFields = true)
public class QuartzProperties {

    private Properties quartz;

    public void setQuartz(Properties quartz){
        this.quartz = quartz;
    }

    public Properties getQuartz(){
        return quartz;
    }
    
}

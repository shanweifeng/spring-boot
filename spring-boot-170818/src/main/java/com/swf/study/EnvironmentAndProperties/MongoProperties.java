package com.swf.study.EnvironmentAndProperties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "spring.data.mongodb")
@Data
public class MongoProperties {

	private String host;
    private int port ;//= DBPort.PORT;
    private String uri ;//= "mongodb://localhost/test";
    private String database;
}

package com.swf.study.EnvironmentAndProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@EnableConfigurationProperties(MongoProperties.class)
@Data
public class PropertiesForAnnotation {

	@Autowired
	private MongoProperties mongoProperties;
}

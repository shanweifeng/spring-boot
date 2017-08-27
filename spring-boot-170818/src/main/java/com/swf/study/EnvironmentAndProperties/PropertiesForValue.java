package com.swf.study.EnvironmentAndProperties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@Data
public class PropertiesForValue {

	@Value("spring.datasource.url")
	private String url;
}

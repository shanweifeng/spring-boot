package com.swf.study.EnvironmentAndProperties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "wisely")//1.5后locations取消了 使用当前模式代替之前的locations
@PropertySource("classpath:config/wisely.properties")
@Data
public class WiselySettings {

	private String name;
	private String gender;
	private String email;
}

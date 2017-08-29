package com.swf.study.EnvironmentAndProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import lombok.Data;

@Configuration//主要是@Configuration 将类暴露给spring管理
@EnableConfigurationProperties(MongoProperties.class)
@Data
public class GetEnvironment implements EnvironmentAware {
	
	@Autowired
	private MongoProperties mongoProperties;

	//实现EnvironmentAware接口的获取系统环境变量 通过当前接口可以获取相关系统变量  在controller、service等spring管理的类中均可以实现EnvironmentAware接口获取系统变量
	public void setEnvironment(Environment environment) {
		System.out.println("======在GetEnvironment的GetEnvironment。setEnvironment输出对系统环境变量获取的结果,可以在controller、service等spring管理的类中实现EnvironmentAware接口来获取系统变量包括在配置文件中配置的属性");
        //通过 environment 获取到系统属性.
        System.out.println(environment.getProperty("JAVA_HOME"));
       System.out.println("mongoProperties.getDatabase()="+mongoProperties.getDatabase());
        //通过 environment 同样能获取到application.properties配置的属性.
        System.out.println(environment.getProperty("spring.datasource.url"));
       
        //获取到前缀是"spring.datasource." 的属性列表值.
        RelaxedPropertyResolver relaxedPropertyResolver = new RelaxedPropertyResolver(environment, "spring.datasource.");
        System.out.println("spring.datasource.url="+relaxedPropertyResolver.getProperty("url"));
        System.out.println("spring.datasource.driverClassName="+relaxedPropertyResolver.getProperty("driverClassName"));
	}

}

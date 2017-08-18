package com.swf.web.interrupter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 手动设定资源映射
 */
@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter{

	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/statics/**").addResourceLocations("classpath:/public/","classpath:/static/");//可以设定绝对路径的文件夹
        super.addResourceHandlers(registry);
    }
}

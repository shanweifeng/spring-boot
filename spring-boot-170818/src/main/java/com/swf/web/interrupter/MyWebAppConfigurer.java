package com.swf.web.interrupter;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
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
        System.out.println("ResourceHandlerRegistry添加");
        super.addResourceHandlers(registry);
    }
	
	@Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 多个拦截器组成一个拦截器链

        // addPathPatterns 用于添加拦截规则

        // excludePathPatterns 用户排除拦截

        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**");
        System.out.println("InterceptorRegistry添加");
        super.addInterceptors(registry);

    }
}

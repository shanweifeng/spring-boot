package com.swf;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.swf.monitor.util.Shanhy;
import com.swf.service.DemoService;

/**
 * 
 * @author Administrator
 *
 */
@Configuration
public class MyConfig {

	 /**
     *  这里只是测试使用，没有实际的意义.
     */
    /*@Bean(name="testDemoService")
    public DemoService filterRegistrationBean(@Qualifier("shanhyB") Shanhy shanhy){
    	DemoService helloService = new DemoService();
        shanhy.dispaly();
        // 其它处理代码.
        return helloService;
    }*/
}

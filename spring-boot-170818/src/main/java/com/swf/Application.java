package com.swf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

/**
 * spring boot 启动类
 * @author Administrator
 * spring boot开发参考:http://412887952-qq-com.iteye.com/?page=12
 */

//@RestController 启动类不需要注册bean 所以不需要
@SpringBootApplication
public class Application {
	/**
	 * 其中@SpringBootApplication申明让spring boot自动给程序进行必要的配置，等价于以默认属性使用@Configuration，@EnableAutoConfiguration和@ComponentScan
	 * @RestController返回json字符串的数据，直接可以编写RESTFul的接口
	 * 两种启动方式第一种在当前类中直接右键 run as -> java application 
	 * 第二种:右键project – Run as – Maven build – 在Goals里输入spring-boot:run ,然后Apply,最后点击Run。(第二种也是启动了main方法)
	 * 再启动的时候需要注意运行环境，有可能会因为运行环境导致运行异常
	 */
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	/**
	 * 第二种方法 fastjson 
	 * @return
	 */
	@Bean
    public HttpMessageConverters fastJsonHttpMessageConverters() {
       FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
       FastJsonConfig fastJsonConfig = new FastJsonConfig();
       fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
       fastConverter.setFastJsonConfig(fastJsonConfig);
       HttpMessageConverter<?> converter = fastConverter;
       return new HttpMessageConverters(converter);
    }
	/**
	 * 第一种方法 启动类继承WebMvcConfigurerAdapter  实现下面的方法
	 */
	/*@Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
      
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
 
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.PrettyFormat
        );
        fastConverter.setFastJsonConfig(fastJsonConfig);
      
        converters.add(fastConverter);
    }*/
}

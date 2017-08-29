package com.swf;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.HttpMessageConverter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.swf.study.EnvironmentAndProperties.WiselySettings;
import com.swf.study.banner.MyBanner;

/**
 * spring boot 启动类
 * @author Administrator
 * spring boot开发参考:http://412887952-qq-com.iteye.com/?page=12
 */

//@RestController 启动类不需要注册bean 所以不需要
@SpringBootApplication
//@ComponentScan(basePackages = {"monitor","com.swf"})//设定扫描路径
@ServletComponentScan//这个就是扫描相应的Servlet包; @ServletComponentScan
//@EnableConfigurationProperties(WiselySettings.class) 1.5以后@ConfigurationProperties中取消了locations
//@Import(DruidConfiguration.class)//手动引入
//@Configuration
public class Application {//如果将当前类移动位置可能存在类不能自动注入到spring容器汇总，这里需要自定义springboot自动扫描路径以确保能扫描到所有需要注册的类
	/**
	 * 其中@SpringBootApplication申明让spring boot自动给程序进行必要的配置，等价于以默认属性使用@Configuration，@EnableAutoConfiguration和@ComponentScan
	 * @RestController返回json字符串的数据，直接可以编写RESTFul的接口
	 * 两种启动方式第一种在当前类中直接右键 run as -> java application 
	 * 第二种:右键project – Run as – Maven build – 在Goals里输入spring-boot:run ,然后Apply,最后点击Run。(第二种也是启动了main方法)
	 * 再启动的时候需要注意运行环境，有可能会因为运行环境导致运行异常
	 */
	
	public static void main(String[] args) {
		//SpringApplication.run(Application.class, new String[]{"hello,","单伟峰"});// new String[]{"hello,","单伟峰"}
		SpringApplication application = new SpringApplication(Application.class);
		//application.setBannerMode(Banner.Mode.OFF);//关闭spring boot图标
		//application.setBannerMode(Banner.Mode.LOG);
		application.setBanner(new MyBanner());
        application.run(new String[]{"hello,","单伟峰"});
	}
	
	@Bean 
    public MultipartConfigElement multipartConfigElement() { 
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //// 设置文件大小限制 ,超了，页面会抛出异常信息，这时候就需要进行异常信息的处理了;
        factory.setMaxFileSize("128KB"); //KB,MB  上传的单个文件大小
        /// 设置总上传数据总大小
        factory.setMaxRequestSize("256KB"); //上传的总文件大小
        //Sets the directory location where files will be stored.
        //factory.setLocation("路径地址");
        return factory.createMultipartConfig(); 
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

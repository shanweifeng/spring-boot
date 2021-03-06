package com.swf.monitor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.Configuration;

import com.swf.monitor.util.ShanhyA;
import com.swf.monitor.util.ShanhyB;

/**
 * 实现自己实例化bean并注册为Spring管理
 * @author Administrator
 *
 */
@Configuration
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor{

	//当前注释在多数据源切换时可用
	/*postProcessBeanFactory()是bean配置的工厂方法，在这个方法中可以获取到我们所有在postProcessBeanDefinitionRegistry方法中注册的所有bean，在这里我们可以进行属性的设置等操作。

	// 这里可以设置属性，例如
	       BeanDefinition beanDefinition = beanFactory.getBeanDefinition("dataSourceA");
	       MutablePropertyValues mutablePropertyValues = beanDefinition.getPropertyValues();
	       //加入属性.
	       mutablePropertyValues.addPropertyValue("driverClassName", "com.mysql.jdbc.Driver");
	       mutablePropertyValues.addPropertyValue("url", "jdbc:mysql://localhost:3306/test");
	       mutablePropertyValues.addPropertyValue("username", "root");
	       mutablePropertyValues.addPropertyValue("password", "123456");*/
	
	//bean 的名称生成器.
    private BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();
	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory arg0) throws BeansException {
		// 获取注入的spring bran
		System.out.println("MyBeanDefinitionRegistryPostProcessor.postProcessBeanFactory()");
	}

	@Override
	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		// 将bean注入到spring中
		System.out.println("MyBeanDefinitionRegistryPostProcessor.postProcessBeanDefinitionRegistry()");
	      
	       /*
	        * 在这里可以注入bean.
	        */
	       registerBean(registry, "shanhyA", ShanhyA.class);
	       registerBean(registry, "shanhyB", ShanhyB.class);
	}

	/**
     * 提供公共的注册方法。
     * @param beanDefinitionRegistry
     * @param name
     * @param beanClass
     *  先执行：postProcessBeanDefinitionRegistry()方法，
     *  在执行：postProcessBeanFactory()方法。
     */
    private void registerBean(BeanDefinitionRegistry registry,String name,Class<?> beanClass){
       AnnotatedBeanDefinition annotatedBeanDefinition  = new AnnotatedGenericBeanDefinition(beanClass);
         //可以自动生成name
       String beanName = (name != null?name:this.beanNameGenerator.generateBeanName(annotatedBeanDefinition, registry));
       //bean注册的holer类.
       BeanDefinitionHolder beanDefinitionHolder = new BeanDefinitionHolder(annotatedBeanDefinition, beanName);
       //使用bean注册工具类进行注册.
       BeanDefinitionReaderUtils.registerBeanDefinition(beanDefinitionHolder, registry);
    }
}

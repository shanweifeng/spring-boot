package com.swf.monitor.ehcache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import lombok.extern.slf4j.Slf4j;

/**
 * 缓存配置.
 * @author swf
 * @version 1.0
 */
@Slf4j
@Configuration
@EnableCaching//标注启动缓存.
public class CacheConfiguration {

	private Logger logger = LoggerFactory.getLogger(CacheConfiguration.class);
	/**
	 * ehcache 主要的管理器
	 * @param bean
	 * @return
	 */
	@Bean
	public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean bean) {
		logger.info("CacheConfiguration.ehCacheCacheManager() @bean, ehcache 主要的管理器");
		return new EhCacheCacheManager(bean.getObject());
	}
	
	/**
	 * 根据shared与否的设置，spring分别通过CacheManager.create()或new CacheManager()
	 * 的方式来创建一个ehcache()基地也就是说通过这个来设置cache的基地是这里的spring独用，还是
	 * 跟别的（如hibernate的Ehcache）cache共享
	 * @return
	 */
	@Bean
	public EhCacheManagerFactoryBean ehCacheManagerFacrotyBean() {
		logger.info("CacheConfiguration.ehCacheManagerFacrotyBean() 创建cacheManage");
		EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
		cacheManagerFactoryBean.setConfigLocation(new ClassPathResource("config/ehcache.xml"));
		cacheManagerFactoryBean.setShared(true);
		return cacheManagerFactoryBean;
	}
}

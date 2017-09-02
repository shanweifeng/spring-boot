package com.swf.monitor.redis.cache;

import java.lang.reflect.Method;

import javax.el.TypeConverter;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * RedisCacheConfig这里也可以不用继承：CachingConfigurerSupport，也就是直接一个普通的Class就好了；
 * 这里主要我们之后要重新实现 key的生成策略，只要这里修改KeyGenerator，
 * 其它位置不用修改就生效了。普通使用普通类的方式的话，那么在使用@Cacheable的时候还需要指定KeyGenerator的名称;这样编码的时候比较麻烦。
 * @author Administrator
 *
 */
//@Component
//@EnableCaching//标注启动缓存. 使用redis实现缓存机制
public class RedisCacheConfig extends CachingConfigurerSupport{//extends CachingConfigurerSupport 重写keyGenerator生成缓存key策略

	/**
     * 缓存管理器.
     * @param redisTemplate
     * @return
     */
    //@Bean
    public CacheManager cacheManager(RedisTemplate<?,?> redisTemplate) {
       CacheManager cacheManager = new RedisCacheManager(redisTemplate);
       /* //设置缓存过期时间
       // rcm.setDefaultExpiration(60);//秒
       //设置value的过期时间
       Map<String,Long> map=new HashMap();
       map.put("test",60L);
       rcm.setExpires(map);*/
       return cacheManager;
    }
    
    /**
     * redis模板操作类,类似于jdbcTemplate的一个类;
     * 虽然CacheManager也能获取到Cache对象，但是操作起来没有那么灵活；
     * 这里在扩展下：RedisTemplate这个类不见得很好操作，我们可以在进行扩展一个我们
     * 自己的缓存类，比如：RedisStorage类;
     * @param factory : 通过Spring进行注入，参数在application.properties进行配置；
     * @return
     */
    //@Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory factory) {
    	/*GenericToStringSerializer：使用Spring转换服务进行序列化；
    	JacksonJsonRedisSerializer：使用Jackson 1，将对象序列化为JSON；
    	Jackson2JsonRedisSerializer：使用Jackson 2，将对象序列化为JSON；
    	JdkSerializationRedisSerializer：使用Java序列化；
    	OxmSerializer：使用Spring O/X映射的编排器和解排器（marshaler和unmarshaler）实现序列化，用于XML序列化；
    	StringRedisSerializer：序列化String类型的key和value。实际上是String和byte数组之间的转换*/
    	
       /*RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
       redisTemplate.setConnectionFactory(factory);
      
       //key序列化方式;（不然会出现乱码;）,但是如果方法上有Long等非String类型的话，会报类型转换错误；
       //所以在没有自己定义key生成策略的时候，以下这个代码建议不要这么写，可以不配置或者自己实现ObjectRedisSerializer
       //或者JdkSerializationRedisSerializer序列化方式;
       RedisSerializer<String> redisSerializer = new StringRedisSerializer();//Long类型不可以会出现异常信息;
       redisTemplate.setKeySerializer(redisSerializer);
       redisTemplate.setHashKeySerializer(redisSerializer);
       
       return redisTemplate;*/
    	//下面这种自定义序列化后redis中key就不会出现乱码
    	StringRedisTemplate template = new StringRedisTemplate(factory);
        //定义key序列化方式
        RedisSerializer<String> redisSerializer = new StringRedisSerializer();//Long类型会出现异常信息;需要我们上面的自定义key生成策略，一般没必要
        template.setKeySerializer(redisSerializer);
        //定义value的序列化方式
        
        Jackson2JsonRedisSerializer<Object> redisSerializerValue = new Jackson2JsonRedisSerializer<Object>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        redisSerializerValue.setObjectMapper(om);
        
        //GenericToStringSerializer<Object> redisSerializerValue = new GenericToStringSerializer<Object>(Object.class);
        //ConversionService conversionService = new ConversionService();
        //redisSerializerValue1.setConversionService(conversionService);
        
        template.setValueSerializer(redisSerializerValue);
        template.setHashValueSerializer(redisSerializerValue);//序列化的多种方式
        template.afterPropertiesSet();
        return template;
    }
    
    /**
     * 自定义key.
     * 此方法将会根据类名+方法名+所有参数的值生成唯一的一个key,即使@Cacheable中的value属性一样，key也会不一样。  如果在方法@Cacheable中设置了key  则该方法不起作用
     */
    @Override
    public KeyGenerator keyGenerator() {//重写CachingConfigurerSupport中的方法  如果不重写  使用的是spring默认序列化方式
       System.out.println("RedisCacheConfig.keyGenerator()");
       return new KeyGenerator() {
           public Object generate(Object o, Method method, Object... objects) {
              // This will generate a unique key of the class name, the method name
              //and all method parameters appended.
              StringBuilder sb = new StringBuilder();
              sb.append(o.getClass().getName());
              sb.append(method.getName());
              for (Object obj : objects) {
                  sb.append(obj.toString());
              }
              System.out.println("keyGenerator=" + sb.toString());
              return sb.toString();
           }
       };
    }
}

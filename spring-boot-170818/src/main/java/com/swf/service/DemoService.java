package com.swf.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.swf.entity.Demo;
import com.swf.entity.User;
import com.swf.monitor.multi.datasource.TargetDataSource;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.swf.dao.DemoRepository;
import com.swf.dao.mapper.UserMapper;

@Service("demoService")
public class DemoService {

	
	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@Resource
	private DemoRepository demoRepository;
	
	//这里的单引号不能少，否则会报错，被识别是一个对象;  
    public static final String CACHE_KEY = "'demoInfo'";  
     
    /** 
     * value属性表示使用哪个缓存策略，缓存策略在ehcache.xml 
    */  
    public static final String DEMO_CACHE_NAME = "demo";  
	
   
	public DemoService() {
		System.out.println("DemoService.DemoService()");
	    System.out.println("DemoService.DemoService()");
	    System.out.println("DemoService.DemoService()");
	}
	
	
	@Transactional
    public void save(Demo demo){
       demoRepository.save(demo);
    }
	
	@TargetDataSource("ds3")
	public void saveByJDBCTemplate(Demo demo) {
		String sql = "insert into test(`name`) values(?)";
		jdbcTemplate.update(sql, new Object[]{demo.getName()});
	}
	
	//@Transactional(readOnly=true)
	//@CachePut(value = DEMO_CACHE_NAME,key = "'demoInfo_'+#updated.getId()")修改缓存
	//@Cacheable(value="demoInfo",key="'com.swf.service.'+#id") //缓存,这里没有指定key.
	//@Cacheable(value="demoInfo")
	 @Cacheable(value=DEMO_CACHE_NAME,key="'demoInfo_'+#id")//添加缓存
	public Demo getById(long id){
		System.err.println("DemoInfoServiceImpl.findById()=========从数据库中进行获取的....id="+id);
	    String sql = "select *from test where id=?";
	    RowMapper<Demo> rowMapper = new BeanPropertyRowMapper<Demo>(Demo.class);
	    return jdbcTemplate.queryForObject(sql, rowMapper,id);
	}
	
	public Demo getById1(long id){
		System.err.println("DemoInfoServiceImpl.getById1()=========从数据库中进行获取的....id="+id);
		String sql = "select *from test where id=?";
		RowMapper<Demo> rowMapper = new BeanPropertyRowMapper<Demo>(Demo.class);
		return jdbcTemplate.queryForObject(sql, rowMapper,id);
	}
	
	@TargetDataSource("ds1")
	public User getByIdD(long id){
		System.err.println("DemoInfoServiceImpl.getByIdD()=========从数据库中进行获取的....id="+id);
		String sql = "select * from user where id=?";
		RowMapper<User> rowMapper = new BeanPropertyRowMapper<User>(User.class);
		return jdbcTemplate.queryForObject(sql, rowMapper,id);
	}
	
	@Resource
    private RedisTemplate<String,String> redisTemplate;
   
    public void test(){
       ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
       valueOperations.set("mykey4", "random1="+Math.random());
       System.out.println(valueOperations.get("mykey4"));
    }
    
    //@CacheEvict(value = DEMO_CACHE_NAME,key = "'demoInfo_'+#updated.getId()")//清除指定缓存 
    @CacheEvict(value="demoInfo")//删除所有缓存
    public void deleteFromCache(long id) {
       System.out.println("DemoInfoServiceImpl.delete().从缓存中删除.");
    }
    
    @Autowired
    private UserMapper userMappper;
   
    @TargetDataSource("ds1")
    public User getMybatiesById(Integer name){
        return userMappper.getById(name);
    }
    
    @TargetDataSource("ds1")
    public List<User> likeName(String name){
    	System.out.println("name="+name);
        return userMappper.likeName(name);
    }
    
    @TargetDataSource("ds1")
    public List<Object> load(){
    	//PageHelper.startPage(1,1);
    	return userMappper.getLog("敖琪111");
    }
}

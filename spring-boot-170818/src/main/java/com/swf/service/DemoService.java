package com.swf.service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.swf.entity.Demo;
import com.swf.dao.DemoRepository;

@Service("demoService")
public class DemoService {

	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@Resource
	private DemoRepository demoRepository;
	
	public DemoService() {
		System.out.println("DemoService.DemoService()");
	    System.out.println("DemoService.DemoService()");
	    System.out.println("DemoService.DemoService()");
	}
	
	
	@Transactional
    public void save(Demo demo){
       demoRepository.save(demo);
    }
	
	@Transactional
	public void saveByJDBCTemplate(Demo demo) {
		String sql = "insert into test(name,info) values(?,?)";
		jdbcTemplate.update(sql, new Object[]{demo.getName(),demo.getInfo()});
	}
	
	//@Transactional(readOnly=true)
	@Cacheable(value="demoInfo") //缓存,这里没有指定key.
	public Demo getById(long id){
		System.err.println("DemoInfoServiceImpl.findById()=========从数据库中进行获取的....id="+id);
	    String sql = "select *from test where id=?";
	    RowMapper<Demo> rowMapper = new BeanPropertyRowMapper<Demo>(Demo.class);
	    return jdbcTemplate.queryForObject(sql, rowMapper,id);
	}
	
	@Resource
    private RedisTemplate<String,String> redisTemplate;
   
    public void test(){
       ValueOperations<String,String> valueOperations = redisTemplate.opsForValue();
       valueOperations.set("mykey4", "random1="+Math.random());
       System.out.println(valueOperations.get("mykey4"));
    }
    
    @CacheEvict(value="demoInfo")
    public void deleteFromCache(long id) {
       System.out.println("DemoInfoServiceImpl.delete().从缓存中删除.");
    }
}

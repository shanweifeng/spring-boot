package com.swf.service;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.swf.entity.Demo;
import com.swf.dao.DemoRepository;

@Service
public class DemoService {

	@Resource
	private JdbcTemplate jdbcTemplate;
	
	@Resource
	private DemoRepository demoRepository;
	
	@Transactional
    public void save(Demo demo){
       demoRepository.save(demo);
    }
	
	@Transactional
	public void saveByJDBCTemplate(Demo demo) {
		String sql = "insert into test(name,info) values(?,?)";
		jdbcTemplate.update(sql, new Object[]{demo.getName(),demo.getInfo()});
	}
	
	public Demo getById(long id){
	    String sql = "select *from test where id=?";
	    RowMapper<Demo> rowMapper = new BeanPropertyRowMapper<Demo>(Demo.class);
	    return jdbcTemplate.queryForObject(sql, rowMapper,id);
	}
}

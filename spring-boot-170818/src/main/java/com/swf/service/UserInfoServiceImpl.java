package com.swf.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.swf.dao.UserInfoRepository;
import com.swf.entity.Demo;
import com.swf.entity.SysPermission;
import com.swf.entity.UserInfo;

@Service
public class UserInfoServiceImpl {

	@Resource  
    private UserInfoRepository userInfoRepository; 
	
	@Resource
   	private JdbcTemplate jdbcTemplate;
      
    public UserInfo findByUsername(String username) {
       System.out.println("UserInfoServiceImpl.findByUsername()");  
       return userInfoRepository.findByUsername(username);  
       /*String sql = "select * from user_info where username=?";
	    RowMapper<UserInfo> rowMapper = new BeanPropertyRowMapper<UserInfo>(UserInfo.class);
	    return jdbcTemplate.queryForObject(sql, rowMapper,username);*/
    }
    
    public List<SysPermission> findPerssionByUserId(Integer userId){
    
    	/*String sql = "select * from user_info where username=?";
	    RowMapper<UserInfo> rowMapper = new BeanPropertyRowMapper<UserInfo>(UserInfo.class);
	    return jdbcTemplate.queryForObject(sql, rowMapper,username);*/
    	return null;
    }
}

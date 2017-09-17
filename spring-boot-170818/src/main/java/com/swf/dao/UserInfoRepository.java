package com.swf.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.swf.entity.UserInfo;
/**
 * UserInfo持久化类; 
 * @author Administrator
 *
 */
public interface UserInfoRepository extends CrudRepository<UserInfo,Long> {
	/**通过username查找用户信息;*/  
	//@Query("select * from user_info where username=?")
    public UserInfo findByUsername(String username);  
}

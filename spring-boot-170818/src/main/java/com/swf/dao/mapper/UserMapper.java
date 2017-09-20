package com.swf.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.swf.entity.User;

public interface UserMapper {

	@Select("select * from User where realname = #{name}")
    public List<User> likeName(String name);
   
    @Select("select *from User where id = #{id}")
    public User getById(long id);
   
    @Select("select name from User where id = #{id}")
    public String getNameById(long id);
    
    @Select("select *from User ")
    public List<User> load();
    
    @Select("select * from log where username = #{name}")
    public List<Object> getLog(String name);
}

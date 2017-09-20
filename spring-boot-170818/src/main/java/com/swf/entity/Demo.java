package com.swf.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
@Entity//加入这个注解，Demo就会进行持久化了，在这里没有对@Table进行配置，请自行配置
@Table(name="test")
public class Demo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3081515663327595060L;
	@Id
	@GeneratedValue
	private long id; //id
    private String name; //name
    //@JSONField(serialize=false)
    private String info;
}

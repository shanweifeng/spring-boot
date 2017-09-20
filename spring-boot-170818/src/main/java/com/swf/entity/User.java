package com.swf.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

//@Entity
//@Table(name="user")
public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6489656881604336534L;

	private Integer id;
	private String username;
	private String realname;
	private String password;
	private Short locked;
	private Date create_time;
	private String mobile_number;
	private String email;
	private String sms_code;
	private Short department_id;
	
	@Override  
    public String toString() {  
       return "User [id=" + id + ", username=" + username + ", realname=" + realname + ", password=" + password  
              + ", locked=" + locked + ", create_time=" + create_time+", mobile_number=" + mobile_number + 
              ", email=" + email +", sms_code=" + sms_code +", department_id=" + department_id +"]";  
    }  

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Short getLocked() {
		return locked;
	}
	public void setLocked(Short locked) {
		this.locked = locked;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getMobile_number() {
		return mobile_number;
	}
	public void setMobile_number(String mobile_number) {
		this.mobile_number = mobile_number;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSms_code() {
		return sms_code;
	}
	public void setSms_code(String sms_code) {
		this.sms_code = sms_code;
	}
	public Short getDepartment_id() {
		return department_id;
	}
	public void setDepartment_id(Short department_id) {
		this.department_id = department_id;
	}
	
}

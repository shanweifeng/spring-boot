package com.swf.test;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.swf.Application;
import com.swf.entity.Demo;
import com.swf.service.DemoService;
import com.swf.service.UserInfoServiceImpl;

import net.minidev.json.JSONObject;

////SpringJUnit支持，由此引入Spring-Test框架支持！
@RunWith(SpringJUnit4ClassRunner.class)

////指定我们SpringBoot工程的Application启动类
@SpringBootTest
///由于是Web项目，Junit需要模拟ServletContext，因此我们需要给我们的测试类加上@WebAppConfiguration。
@WebAppConfiguration
public class TestSpringBoot {
	@Resource
    private DemoService demoService;
   
	@Resource
    private UserInfoServiceImpl userInfoServiceImpl;
	
	@Test
    public void testGetName(){
       /*Assert.assertEquals("hello",demoService.getById(2));*/
		Demo demo=new Demo();
		demo.setName("单伟峰");
		demo.setInfo("info");
		demoService.saveByJDBCTemplate(demo);
		
		System.out.println("demoService.getById(8)"+demoService.getById(8));
    }
	
	@Test
	public void test() {
		System.out.println("获取信息:"+com.alibaba.fastjson.JSONObject.toJSONString(userInfoServiceImpl.findByUsername("admin")));
	}
}

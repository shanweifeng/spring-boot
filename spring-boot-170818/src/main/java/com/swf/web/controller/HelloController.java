package com.swf.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.swf.entity.Demo;
import com.swf.service.DemoService;
import com.swf.study.EnvironmentAndProperties.PropertiesForAnnotation;
import com.swf.study.EnvironmentAndProperties.PropertiesForValue;
import com.swf.study.EnvironmentAndProperties.WiselySettings;

@RestController// 标记为：restful
public class HelloController {

	
	private Logger logger = LoggerFactory.getLogger(HelloController.class);
	@Resource
	private DemoService demoService;
	
	@Resource
	private PropertiesForValue properties;
	
	@Resource
	private PropertiesForAnnotation pe;
	
	@Resource
	private WiselySettings wiselySettings;
	
	//没有指定为主数据源.
    @Autowired
    private DataSource dataSource;
   
    @Autowired
    @Qualifier("ds1")
    private DataSource dataSource1;
    
    @Resource
    private JdbcTemplate jdbcTemplate;
	
    @RequestMapping("/datasource")
    public void testDatasource() {
    	/*System.out.println("dataSource"+dataSource);
    	System.out.println("dataSource1"+dataSource1);
    	System.out.println("jdbcTemplate"+jdbcTemplate);*/
    	System.out.println("demoService.getById(6)"+demoService.getById(6));
    	System.out.println("demoService.getById1(6)"+demoService.getById1(6));
    	System.out.println("demoService.getByIdD(3):"+demoService.getByIdD(3));
    }
    
    @RequestMapping("/mybaties")
    public void testMybatis() {
    	System.out.println(demoService.getMybatiesById(3));
    }
    
    @RequestMapping("/load")
    public void load() {
    	System.out.println(JSON.toJSONString(demoService.likeName("系统管理员")));
    	System.out.println(JSON.toJSONString(demoService.load()));
    }
	
	@RequestMapping("/getwiselySettings")
	public String getwiselySettings() {
		return wiselySettings.getEmail()+"  "+wiselySettings.getGender()+"  "+wiselySettings.getName();
	}
	
	@RequestMapping("/getEnvironment")
	public String getEnvironment() {
		return pe.getMongoProperties().getHost();
	}
	
	@RequestMapping("/")
    public String hello(){
       return"Hello world! 这里将数据访问单独提出放到了controller中";
    }
	
	/**
	 * 我们在编写接口的时候，时常会有需求返回json数据，那么在spring boot应该怎么操作呢？主要是在class中加入注解@RestController,。
	 * 返回JSON之步骤： (1)编写一个实体类Demo
	 * (2)编写DemoController；
	 * (3)在DemoController加上@RestController和@RequestMapping注解；
	 * 其实Spring Boot也是引用了JSON解析包Jackson，那么自然我们就可以在Demo对象上使用Jackson提供的json属性的注解，对时间进行格式化，对一些字段进行忽略等等
	 */

	@RequestMapping("/json")
    public Object json(){
		Demo d = new Demo();
		d.setId(100);
		d.setName("swf");
		//d.setInfo("这个是测试fastJson中的@JSONField是否起作用，有用则不会返回");
		List<Demo> dl = new ArrayList<>();//Lists.newArrayList(d);
		dl.add(d);
        return dl;
    }
	@RequestMapping("/exception")
	public Object exceptionTest()
	{
		return 100/0;
	}
	
	/**
     * 测试保存数据方法.
     * @return
     */
    @RequestMapping("/save")
    public String save(){
       Demo d = new Demo();
       d.setName("Angel");
       demoService.save(d);//保存数据.
       return"ok.Demo2Controller.save";
    }
    
    @RequestMapping("/saveByJDBCTemplate")
    public String saveByJDBCTemplate(){
       Demo d = new Demo();
       d.setName("水电费水电费水电费12");
       //d.setInfo("单伟峰是多少山东省地方");
       demoService.saveByJDBCTemplate(d);//保存数据.
       return"ok.Demo2Controller.saveByJDBCTemplate";
    }
    
    @RequestMapping("/getById/{id}")
    public Object getById(@PathVariable("id") int id){
    	logger.info("HelloController.getById(@PathVariable(\"id\") int id)-》"+id);
       return demoService.getById(id);//保存数据.
    }
    
    @RequestMapping("/delete/{id}")
    public Object deleteFromCache(@PathVariable("id")int id) {
    	demoService.deleteFromCache(id);
    	return "删除成功";
    }
    
    /*@Resource
    private DataSource dataSource;
    @RequestMapping("/getBeans")
    public Object getBeans() {
    	//StatViewServlet t=SpringUtil.getBean(StatViewServlet.class);
    	//System.out.println(t.get);
    	System.out.println("dataSource:"+dataSource);
    	return "getBeans";
    }*/
    
    @RequestMapping("/getPropertiesForValue")
    public String getPropertiesForValue() {
    	return "返回的Url:"+properties.getUrl();
    }
}

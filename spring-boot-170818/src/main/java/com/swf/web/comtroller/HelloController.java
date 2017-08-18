package com.swf.web.comtroller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swf.entity.Demo;
import com.swf.web.service.DemoService;

@RestController// 标记为：restful
public class HelloController {

	@Resource
	private DemoService demoService;
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
		d.setId(100l);
		d.setName("swf");
		d.setInfo("这个是测试fastJson中的@JSONField是否起作用，有用则不会返回");
        return d;
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
       d.setName("Angel");
       d.setInfo("swf");
       demoService.saveByJDBCTemplate(d);//保存数据.
       return"ok.Demo2Controller.saveByJDBCTemplate";
    }
    
    @RequestMapping("/getById/{id}")
    public Object getById(@PathVariable("id") int id){
       return demoService.getById(id);//保存数据.
    }
}
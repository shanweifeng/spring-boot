package com.swf.web.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.swf.monitor.util.Shanhy;
/**
 * 用于测试shiro管理权限
 * @author Administrator
 *
 */
@Controller
public class ShiroController {

	@RequestMapping("/index")
	@RequiresPermissions("userInfo:view")
	public String index() {
		return "index";
	}
	
	@RequestMapping(value="/login",method=RequestMethod.GET)
    public String login(){
       return"login";
    }
	
	// 登录提交地址和applicationontext-shiro.xml配置的loginurl一致。 (配置文件方式的说法)  
    @RequestMapping(value="/login",method=RequestMethod.POST)  
    public String login(HttpServletRequest request, Map<String, Object> map) throws Exception {  
       System.out.println("HomeController.login()");  
       // 登录失败从request中获取shiro处理的异常信息。  
       // shiroLoginFailure:就是shiro异常类的全类名.  
       String exception = (String) request.getAttribute("shiroLoginFailure");  
   
       System.out.println("exception=" + exception);  
       String msg = "";  
       if (exception != null) {  
           if (UnknownAccountException.class.getName().equals(exception)) {  
              System.out.println("UnknownAccountException -- > 账号不存在：");  
              msg = "UnknownAccountException -- > 账号不存在：";  
           } else if (IncorrectCredentialsException.class.getName().equals(exception)) {  
              System.out.println("IncorrectCredentialsException -- > 密码不正确：");  
              msg = "IncorrectCredentialsException -- > 密码不正确：";  
           } else if ("kaptchaValidateFailed".equals(exception)) {  
              System.out.println("kaptchaValidateFailed -- > 验证码错误");  
              msg = "kaptchaValidateFailed -- > 验证码错误";  
           } else {  
              msg = "else >> "+exception;  
              System.out.println("else -- >" + exception);  
           }  
       }  
       map.put("msg", msg);  
       // 此方法不处理登录成功,由shiro进行处理.  
       return "/login";  
    } 
    
    /** 
     * 用户添加; 
     * @return 
     */  
    @RequestMapping("/userAdd")  
    @RequiresPermissions("userInfo:add")//权限管理;  
    public String userInfoAdd(){  
       return "userInfoAdd";  
    }
    
    /** 
     * 用户添加; 
     * @return 
     */  
    @RequestMapping("/userView")  
    @RequiresPermissions("userInfo:view")//权限管理;  
    public String userView(){  
       return "userInfoView";  
    } 
    
    @Resource(name = "shanhyA")
    private Shanhy shanhyA;
 
    @Autowired
    @Qualifier("shanhyB")
    private Shanhy shanhyB;
 
    @RequestMapping("/test")
    public String test(){
       shanhyA.dispaly();
       shanhyB.dispaly();
       return"test";
    }
}



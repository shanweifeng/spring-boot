package com.swf.web.interrupter;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

/**
 * 全局异常处理
 * @author Administrator
 *
 */

@RestControllerAdvice //这样可以直接返回json格式数据 如果没有前后端分离 则使用@ControllerAdvice
@Slf4j
public class GlobalDefaultExceptionHandler {

	Logger logger=LoggerFactory.getLogger(GlobalDefaultExceptionHandler.class);
	
	@ExceptionHandler(value = Exception.class)
	@ResponseStatus(value = HttpStatus.OK)//表示请求成功
    public String defaultErrorHandler(HttpServletRequest req, Exception e)  {
//      // If the exception is annotated with @ResponseStatus rethrow it and let
//      // the framework handle it - like the OrderNotFoundException example
//      // at the start of this post.
//      // AnnotationUtils is a Spring Framework utility class.
//      if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
//          throw e;
//
//      // Otherwise setup and send the user to a default error-view.
//      ModelAndView mav = new ModelAndView();
//      mav.addObject("exception", e);
//      mav.addObject("url", req.getRequestURL());
//      mav.setViewName(DEFAULT_ERROR_VIEW);
//      return mav;
      
      //打印异常信息：
       e.printStackTrace();
       logger.error("GlobalDefaultExceptionHandler.defaultErrorHandler()",e);
 
       /*
        * 返回json数据或者String数据：
        * 那么需要在方法上加上注解：@ResponseBody
        * 添加return即可。
        */
       
       /*
        * 返回视图：
        * 定义一个ModelAndView即可，
        * 然后return;
        * 定义视图文件(比如：error.html,error.ftl,error.jsp);
        *
        */
       return "请求异常了";
  }
}

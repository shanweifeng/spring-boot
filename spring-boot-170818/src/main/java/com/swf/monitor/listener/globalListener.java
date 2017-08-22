package com.swf.monitor.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class globalListener implements ServletContextListener{

	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("ServletContex初始化");
		
	}

	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("ServletContex销毁");
	}

}

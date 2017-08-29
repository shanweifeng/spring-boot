package com.swf.study.commandLineRunner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
//@Order(1)
public class MuStartupRunner2 implements CommandLineRunner {

	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(">>>>>>>>>>>>>>>服务启动执行，执行加载数据等操作 22222222 <<<<<<<<<<<<<");
	}

}

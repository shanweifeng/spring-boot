package com.swf.study.commandLineRunner;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
//@Order(2)
public class MyStartupRunner1 implements CommandLineRunner {//接收参数

	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println(Arrays.asList(args));
        System.out.println(">>>>>>>>>>>>>>>服务启动执行，执行加载数据等操作11111111<<<<<<<<<<<<<");
	}

}

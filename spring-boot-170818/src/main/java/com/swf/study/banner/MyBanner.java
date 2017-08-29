package com.swf.study.banner;

import java.io.PrintStream;

import org.springframework.boot.Banner;
import org.springframework.core.env.Environment;

public class MyBanner implements Banner {

	public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
		System.out.println("*****************************************************");
		System.out.println("********************<shanweifeng>********************");
		System.out.println("************************<单伟峰>***********************");
		System.out.println("********************<shanweifeng>********************");
		System.out.println("*****************************************************");
	}

}

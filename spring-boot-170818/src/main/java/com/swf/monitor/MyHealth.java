package com.swf.monitor;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class MyHealth implements HealthIndicator {

	public Health health() {
		// TODO Auto-generated method stub 自定义health 检查
		/*int errorCode = check(); // perform some specific health check
        if (errorCode != 0) {
        return Health.down().withDetail("Error Code", errorCode).build();
        }*/
		System.out.println("《自定义health检查，springboot 自带监控和管理生产环境,更多的细节和探索，需要自己看看源码和spring boot的官方文档。》");
        return Health.up().build();
	}

}

package com.meta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@EnableHystrix
public class TalkieAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(TalkieAdminApplication.class, args);
	}



}

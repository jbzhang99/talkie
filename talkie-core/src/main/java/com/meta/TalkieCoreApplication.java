package com.meta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@ServletComponentScan
@ImportResource(locations={"classpath*:conf/spring/application*.xml"})
@EnableScheduling
public class TalkieCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(TalkieCoreApplication.class, args);
	}
}

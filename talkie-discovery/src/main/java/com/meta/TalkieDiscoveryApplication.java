package com.meta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class TalkieDiscoveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(TalkieDiscoveryApplication.class, args);
    }
}

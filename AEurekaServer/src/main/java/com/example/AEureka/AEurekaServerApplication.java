package com.example.AEureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class AEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AEurekaServerApplication.class, args);
	}

}

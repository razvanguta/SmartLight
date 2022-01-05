package com.is.smartlight;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SmartLightApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmartLightApplication.class, args);
	}

}

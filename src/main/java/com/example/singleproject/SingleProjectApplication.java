package com.example.singleproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class SingleProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SingleProjectApplication.class, args);
	}

}

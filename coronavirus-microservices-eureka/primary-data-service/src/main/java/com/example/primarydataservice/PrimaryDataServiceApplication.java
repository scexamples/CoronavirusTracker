package com.example.primarydataservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class PrimaryDataServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrimaryDataServiceApplication.class, args);
	}

}

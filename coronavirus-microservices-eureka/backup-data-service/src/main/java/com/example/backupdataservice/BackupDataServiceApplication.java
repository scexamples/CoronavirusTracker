package com.example.backupdataservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BackupDataServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackupDataServiceApplication.class, args);
	}

}

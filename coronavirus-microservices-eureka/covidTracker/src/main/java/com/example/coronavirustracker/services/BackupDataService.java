package com.example.coronavirustracker.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


@Service
public class BackupDataService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "fallbackReadBackup",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value="1000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value="5"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value="50"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value="5000")

            })
    public String readBackup(){

        String dataFromBackupService = "";

        dataFromBackupService = restTemplate.getForObject("http://backup-data-service/covidTrackerData", String.class);

        return dataFromBackupService;

    }

    public String fallbackReadBackup(){

        return "";

    }

    public void writeBackup(String dataForProcessing){

      restTemplate.postForObject("http://backup-data-service/covidTrackerData", dataForProcessing, String.class);

    }

}

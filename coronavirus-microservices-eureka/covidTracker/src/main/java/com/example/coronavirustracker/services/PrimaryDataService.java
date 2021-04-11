package com.example.coronavirustracker.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

@Service
public class PrimaryDataService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "fallbackFetchVirusData",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value="1000"),
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value="5"),
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value="50"),
                    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value="5000")

            })
    public String fetchVirusData(){

        String dataFromPrimaryService = "";

        dataFromPrimaryService = restTemplate.getForObject("http://primary-data-service/", String.class);

        return dataFromPrimaryService;

    }

    //return empty and make decision in central service (CoronavirusTrackerService)
    public String fallbackFetchVirusData(){

        return "";

    }


}

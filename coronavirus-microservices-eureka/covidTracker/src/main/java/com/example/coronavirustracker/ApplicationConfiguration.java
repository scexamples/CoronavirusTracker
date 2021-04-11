package com.example.coronavirustracker;

import com.example.coronavirustracker.services.CoronavirusTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
public class ApplicationConfiguration {

    @Autowired
    public CoronavirusTrackerService coronavirusTrackerService;

    @EventListener(ApplicationReadyEvent.class)
    public void doSomethingAfterStartup() {
        try {
            coronavirusTrackerService.fetchPrimaryVirusData();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}

package com.example.coronavirustracker.controllers;

import com.example.coronavirustracker.services.CoronavirusTrackerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class HomeController {

    @Autowired
    public CoronavirusTrackerService coronavirusTrackerService;

    @GetMapping("/")
    public String home(Model model) throws IOException, InterruptedException {

        model.addAttribute("locationStats", coronavirusTrackerService.getAllStats());
        model.addAttribute("totalGlobalCases", coronavirusTrackerService.getTotalGlobalCases());
        model.addAttribute("localDate", coronavirusTrackerService.getProcessingDate());

        return "home";
    }
}

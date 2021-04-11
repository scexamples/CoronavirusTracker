package com.example.primarydataservice.resources;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class PrimaryDataResource {

    private static final String VIRUS_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    @RequestMapping (value="/", produces={"text/plain"})
    public static String fetchVirusData() throws IOException, InterruptedException {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DATA_URL))
                .build();

        String dataForProcessing = "";

        try{
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            dataForProcessing = response.body();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return dataForProcessing;

    }
}

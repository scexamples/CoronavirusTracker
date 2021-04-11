package com.example.coronavirustracker.services;

import static org.apache.commons.csv.CSVFormat.DEFAULT;

import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.coronavirustracker.models.Country;
import com.example.coronavirustracker.models.LocationStats;
import com.example.coronavirustracker.models.State;

@Service
public class CoronavirusTrackerService {

    private static LocalDate processingDate;
    private static List<LocationStats> allStats;
    private static int totalGlobalCases;


    @Autowired
    private BackupDataService backupDataService;
 
    @Autowired
    private PrimaryDataService primaryDataService;

    @Scheduled(cron = "0 * * * * *")
    public void fetchPrimaryVirusData() throws IOException, InterruptedException {

        processingDate = LocalDate.now();
        System.out.println("fetchPrimaryVirusData Running: " + LocalDateTime.now());
        String dataForProcessing = "";

        try {
            dataForProcessing = primaryDataService.fetchVirusData();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(dataForProcessing.isEmpty()){
            dataForProcessing = backupDataService.readBackup();
        } else {
            backupDataService.writeBackup(dataForProcessing);
        }

        if(!dataForProcessing.isEmpty()){
            StringReader csvBodyReader = new StringReader(dataForProcessing);
            Iterable<CSVRecord> records = DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);

            allStats = processDisplayModelData(processRecords(records));
        }

    }

    private Map<String, Country> processRecords(Iterable<CSVRecord> records){

        Map<String,Country> countries = new TreeMap<>();

        for (CSVRecord record : records) {

            // read country
            if(record.get("Country/Region").isEmpty()){
                continue;
            }

            Country currCountry = new Country(record.get("Country/Region"));

            if(countries.containsKey(record.get("Country/Region"))) {
                currCountry = countries.get(record.get("Country/Region"));
            } else {
                countries.put(record.get("Country/Region"), currCountry);
            }

            if(record.get("Province/State").isEmpty()){
                currCountry.setNumberOfCases(Integer.parseInt(record.get(record.size()-1)));
            } else{
                State currState = new State(record.get("Province/State"), currCountry, Integer.parseInt(record.get(record.size()-1)));
                currCountry.getStates().add(currState);
            }

        }

        return countries;
    }

    private List<LocationStats> processDisplayModelData(Map<String,Country> countries){

        List<LocationStats> newStats = new ArrayList<>();
        totalGlobalCases = 0;
        for(Country country : countries.values()){
            LocationStats newLocationStats = new LocationStats();
            newLocationStats.setCountry(country.getName());
            newLocationStats.setLatestTotalCases(country.getNumberOfCases());
            newStats.add(newLocationStats);
            totalGlobalCases += country.getNumberOfCases();
        }

        return newStats;
    }

    public List<LocationStats> getAllStats() {
        return allStats;
    }

    public int getTotalGlobalCases() {
        return totalGlobalCases;
    }

    public LocalDate getProcessingDate() {
        return processingDate;
    }
}

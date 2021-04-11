package com.example.backupdataservice.resources;

import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.Optional;

@RestController
//@RequestMapping(value="/*", produces={"text/plain"})
public class BackupDataResource {

    private static final String COVID_DATA_DEFAULT_FILE_PATH = "covidDataBackup.txt";

    //take a param that decides what file to write to
    @PostMapping(value="/{typeOfData}")
    protected static void writeBackup(@RequestBody String dataForProcessing, @PathVariable String typeOfData){
    	System.out.println("in writeBackup");
    	
        Path pathForWriteBackup = null;
        if(typeOfData != null && !typeOfData.isEmpty()){
            pathForWriteBackup = Paths.get(typeOfData + ".txt");
        } else {
            pathForWriteBackup = Paths.get(COVID_DATA_DEFAULT_FILE_PATH);
        }
        try{
            Files.writeString(pathForWriteBackup, dataForProcessing, StandardOpenOption.CREATE);
        } catch (Exception e){
            e.printStackTrace();
        }



    }

    //take a param that decides what file/resource to read from
    @GetMapping(value="/{typeOfData}", produces={"text/plain"})
    protected static String readBackup(@PathVariable String typeOfData){
    	System.out.println("******************* In readBackup *******************");
    	
        String dataForProcessing = "";
        Path pathForReadBackup = null;

        if(typeOfData != null && !typeOfData.isEmpty()){
            pathForReadBackup = Paths.get(typeOfData + ".txt");
        } else {
            pathForReadBackup = Paths.get(COVID_DATA_DEFAULT_FILE_PATH);
        }

        try{
            dataForProcessing = Files.readString(pathForReadBackup);
        } catch (FileNotFoundException fnfe){
        	System.out.println(fnfe.getMessage());
        } catch (Exception e){
            e.printStackTrace();
        }

        return dataForProcessing;
    }

}

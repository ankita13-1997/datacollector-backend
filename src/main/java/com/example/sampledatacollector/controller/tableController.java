package com.example.sampledatacollector.controller;


import com.opencsv.exceptions.CsvException;
import io.swagger.annotations.ApiParam;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/table")
@ComponentScan
@EnableAutoConfiguration
public class tableController {

    @GetMapping("/welcomeBook")
    public String tableView() {
        return "i view the table";
    }


    @PostMapping("/upload")
    public ResponseEntity<?> handleFileUpload(@ApiParam("CSV file") @RequestParam("file") MultipartFile fileNameS) throws IOException, CsvException {
        try (Reader reader = new InputStreamReader(fileNameS.getInputStream());
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            List<Map<String, String>> tableData = new ArrayList<>();
            for (CSVRecord csvRecord : csvParser) {
                Map<String, String> row = new HashMap<>();
                for (String header : csvParser.getHeaderNames()) {
                    row.put(header, csvRecord.get(header));
                }
                tableData.add(row);
            }
            return ResponseEntity.ok(tableData); // Return data on success
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Invalid CSV file"); // Return error message and status code 400
        }
    }


}

package com.gpt.spring_gpt_4o.controller;

import com.gpt.spring_gpt_4o.dto.CityDtoResponse;
import com.gpt.spring_gpt_4o.service.CityImportService;
import com.gpt.spring_gpt_4o.service.CityService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cities")
public class CityController {

    private ChatClient chatClient;

    private final CityService cityService;

    @Autowired
    private CityImportService cityImportService;

    @Autowired
    public CityController(ChatClient.Builder builder, CityService cityService) {
        this.cityService = cityService;
        this.chatClient = builder.build();

    }

    @GetMapping
    public ResponseEntity<List<CityDtoResponse>> getAllCities() {
        return new ResponseEntity<>(cityService.getAllCities(), HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<CityDtoResponse>> searchCitiesByName(@PathVariable(name = "name") String name) {
        return new ResponseEntity<>(cityService.searchCitiesByName(name), HttpStatus.OK);
    }

    @GetMapping("/state/{name}")
    public ResponseEntity<List<CityDtoResponse>> searchCitiesByState(@PathVariable(name = "name") String state) {
        return new ResponseEntity<>(cityService.searchCitiesByState(state), HttpStatus.OK);
    }

    @GetMapping("/createUSCities")
    public ResponseEntity createAllUSCities() throws IOException {
        String filePath = "C:\\Users\\savos\\Downloads\\uscities.csv";
        System.out.println("Starting import with file path: " + filePath);
        try {
            cityImportService.importCitiesFromCSV(filePath);
            return new ResponseEntity("Cities imported successfully!", HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity("Error during import: " + e.getMessage(), HttpStatus.PRECONDITION_FAILED);
        }
    }
}

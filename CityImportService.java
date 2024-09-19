package com.gpt.spring_gpt_4o.service;

import com.gpt.spring_gpt_4o.entity.City;
import com.gpt.spring_gpt_4o.repository.CityRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class CityImportService {

    @Autowired
    private final CityRepository cityRepository;

    public void importCitiesFromCSV(String filePath) throws IOException {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> records = reader.readAll();

            if (!records.isEmpty()) {
                records.remove(0);
            }

            for (String[] record : records) {
                try {
                    City city = new City();

                    city.setName(record[0]);
                    city.setState(record[3]);

                    if (!record[6].isEmpty() && !record[7].isEmpty()) {
                        city.setLatitude(Double.parseDouble(record[6]));
                        city.setLongitude(Double.parseDouble(record[7]));
                    }

                    if (!record[8].isEmpty()) {
                        city.setPopulation(Integer.parseInt(record[8]));
                    }
                    cityRepository.save(city);
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing numeric value for string: " + e.getMessage());
                } catch (Exception e) {
                    System.out.println("General error when processing a string: " + e.getMessage());
                }
            }
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
    }




}

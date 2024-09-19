package com.gpt.spring_gpt_4o.service;

import com.gpt.spring_gpt_4o.dto.CityDtoResponse;

import java.util.List;

public interface CityService {
    public List<CityDtoResponse> getAllCities();
    public List<CityDtoResponse> searchCitiesByName(String name);
    public List<CityDtoResponse> searchCitiesByState(String state);

}

package com.gpt.spring_gpt_4o.service.impl;


import com.gpt.spring_gpt_4o.dto.CityDtoResponse;
import com.gpt.spring_gpt_4o.entity.City;
import com.gpt.spring_gpt_4o.exception.ModelExistsException;
import com.gpt.spring_gpt_4o.repository.CityRepository;
import com.gpt.spring_gpt_4o.service.AiService;
import com.gpt.spring_gpt_4o.service.CityService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    protected final AiService aiService;

    @Override
    public List<CityDtoResponse> getAllCities() {
        return settingListCityDto(cityRepository.findAll());
    }

    @Override
    public List<CityDtoResponse> searchCitiesByName(String name) {
        if(cityRepository.findByName(name).isEmpty()){
            throw new ModelExistsException("City doesn't exists");
        }
        return settingListCityDto(cityRepository.findByNameContainingIgnoreCase(name));
    }

    @Override
    public List<CityDtoResponse> searchCitiesByState(String state) {
        return settingListCityDto(cityRepository.findByStateContainingIgnoreCase(state));
    }

    private CityDtoResponse settingCityDto(City city) {
        CityDtoResponse cityDto = new CityDtoResponse();
        cityDto.setName(city.getName());
        cityDto.setState(city.getState());
        cityDto.setLongitude(city.getLongitude());
        cityDto.setLatitude(city.getLatitude());
        cityDto.setPopulation(city.getPopulation());
        return cityDto;
    }

    private List<CityDtoResponse> settingListCityDto(List<City> cities){
        List<CityDtoResponse> responses = new ArrayList<>();
        for(City city : cities){
            responses.add(settingCityDto(city));
        }
        return responses;
    }
}

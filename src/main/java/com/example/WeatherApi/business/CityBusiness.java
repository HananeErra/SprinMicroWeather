package com.example.WeatherApi.business;

import com.example.WeatherApi.entity.CityEntity;
import com.example.WeatherApi.mapping.CityMapper;
import com.example.WeatherApi.repository.CityRepository;
import dto.weatherapi.City;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Named
@Service
@AllArgsConstructor
public class CityBusiness {
    @Inject
    private CityRepository cityRepository;
    private CityMapper cityMapper;

    public void CreateCity(CityEntity city){
        cityRepository.createCity(cityMapper.toDTO(city));
    }

    public CityEntity findByName(String name) {
        Optional<City> rs = cityRepository.findByName(name);
        City cityDto = rs.get();
        if(cityDto == null) return null;
        else return cityMapper.toEntity(cityDto);
    }

    public void deleteCityById(String id) {
        cityRepository.deleteCityById(id);
    }

    public Boolean updateCityByName(String name, CityEntity entity) {
        return cityRepository.updateCityByName(name, cityMapper.toDTO(entity));
    }

    public List<CityEntity> findAll() {
        List<City> rs = this.cityRepository.findAll();
        List<CityEntity> result = new ArrayList<>();
        rs.forEach(i -> result.add(cityMapper.toEntity(i)));
        return result;
    }
}

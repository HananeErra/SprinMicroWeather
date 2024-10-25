package com.example.WeatherApi.mapping;


import com.example.WeatherApi.entity.CityEntity;
import dto.weatherapi.City;
import dto.weatherapi.CityCoordinates;
import jakarta.inject.Inject;
import org.modelmapper.ModelMapper;


public class CityMapper implements Mapper<City, CityEntity> {

    @Override
    public City toDTO(CityEntity entity) {
        City city = new City();
        city.setId(entity.getId());
        city.setCountry(entity.getCountry());
        city.setName(entity.getName());
        city.setRegion(entity.getRegion());
        city.setZipCode(entity.getZipcode());
        city.setCoordinates(new CityCoordinates());
        city.getCoordinates().setLatitude(entity.getLat());
        city.getCoordinates().setLongitude(entity.getLon());
        return city;

    }

    @Override
    public CityEntity toEntity(City dto) {
        CityEntity cityEntity = new CityEntity();
        cityEntity.setCountry(dto.getCountry());
        cityEntity.setId(dto.getId());
        cityEntity.setName(dto.getName());
        cityEntity.setRegion(dto.getRegion());
        cityEntity.setZipcode(dto.getZipCode());
        cityEntity.setLon(dto.getCoordinates().getLongitude());
        cityEntity.setLat(dto.getCoordinates().getLatitude());
        return cityEntity;
    }
}

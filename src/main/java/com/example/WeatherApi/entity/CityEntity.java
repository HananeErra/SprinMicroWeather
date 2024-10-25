package com.example.WeatherApi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityEntity {
    private String id;
    private String name;
    private String country;
    private String region;
    private int zipcode;
    private int lat;
    private int lon;



}

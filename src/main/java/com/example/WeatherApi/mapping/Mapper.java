package com.example.WeatherApi.mapping;

public interface Mapper<A,B> {

    public A toDTO(B entity);
    public B toEntity(A dto);
}

package com.example.WeatherApi.configuration;

import com.example.WeatherApi.mapping.CityMapper;
import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;

@Configuration
@ApplicationPath("/api/v1") //url de base
public class JerseyConfig extends ResourceConfig {

    private final DataSource dataSource;

    public JerseyConfig(DataSource dataSource){
        this.dataSource = dataSource;
        packages("com.example.WeatherApi.controller");

    }

    @Bean
    public CityMapper CreatecityMapper(){
        return new CityMapper();
    }


    @Bean
    public JdbcTemplate dataJDBC( ){
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public NamedParameterJdbcTemplate paramTemplate() {
        return new NamedParameterJdbcTemplate(dataSource);
    }

}

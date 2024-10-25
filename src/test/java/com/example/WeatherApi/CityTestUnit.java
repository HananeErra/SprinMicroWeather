package com.example.WeatherApi;

import com.example.WeatherApi.repository.CityRepository;
import dto.weatherapi.City;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class CityTestUnit {
    @Mock
   private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private CityRepository cityRepository;

    @Test
    public void VerifyIfFindByNameGenerateTheCorrectSQL(){
        cityRepository.findByName("Paris");
        verify(jdbcTemplate).query(eq("SELECT * FROM city WHERE city.name = ?;"), ArgumentMatchers.<CityRepository.MapperCitySQL>any(), eq("Paris"));
    }
}

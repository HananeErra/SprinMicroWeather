package com.example.WeatherApi.repository;

import dto.weatherapi.City;
import dto.weatherapi.CityCoordinates;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Named
@Repository
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CityRepository {
    @Inject
    private JdbcTemplate jdbcTemplate;


    public void createCity(City city) {
        List<City> rs = this.findAll();
        AtomicBoolean exists = new AtomicBoolean(false);
        rs.forEach(i -> {
            if (i.getId().equals(city.getId())) exists.set(true);
        });

        if (!(exists.get()))
            jdbcTemplate.update("INSERT INTO city(ID,NAME,ZIPCODE,REGION,COUNTRY,LONGITUDE,LATITUDE) VALUES (?,?,?,?,?,?,?)", city.getId(), city.getName(), city.getZipCode(), city.getRegion(), city.getCountry(), city.getCoordinates().getLongitude(), city.getCoordinates().getLatitude());
    }

    public Optional<City> findByName(String name) {
        List<City> rs = jdbcTemplate.query("SELECT * FROM city WHERE city.name = ?;", new MapperCitySQL(), name);

        return rs.stream().findFirst();
    }

    public List<City> findAll() {
        List<City> rs = jdbcTemplate.query("SELECT * FROM city", new MapperCitySQL());
        return rs;
    }

    public void deleteCityById(String id) {
        jdbcTemplate.update("DELETE FROM CITY WHERE id = ?", id);

    }

    public Boolean updateCityByName(String name, City dto) {

        Optional<City> cityOp = this.findByName(name);
        if (cityOp.isEmpty()) return false;
        City city = this.findByName(name).get();
        jdbcTemplate.update("UPDATE city SET ID = ? , NAME = ?, ZIPCODE = ?, REGION = ?, COUNTRY = ?, LONGITUDE = ?, LATITUDE = ? WHERE NAME = ? ;",
                dto.getId(), dto.getName(), dto.getZipCode(), dto.getRegion(), dto.getCountry(), dto.getCoordinates().getLongitude(), dto.getCoordinates().getLatitude(), dto.getName());

        return true;
    }

    public static class MapperCitySQL implements RowMapper<City> {

        @Override
        public City mapRow(ResultSet rs, int rowNum) throws SQLException {
            City city = new City();
            city.setId(rs.getString("ID"));
            city.setName(rs.getString("NAME"));
            city.setRegion(rs.getString("REGION"));
            city.setZipCode(rs.getInt("ZIPCODE"));
            city.setCountry(rs.getString("COUNTRY"));

            CityCoordinates coor = new CityCoordinates();
            coor.setLongitude(rs.getInt("LONGITUDE"));
            coor.setLatitude(rs.getInt("LATITUDE"));
            city.setCoordinates(coor);
            return city;

        }
    }
}

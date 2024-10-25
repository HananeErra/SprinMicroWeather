package com.example.WeatherApi.controller;

import com.example.WeatherApi.business.CityBusiness;
import com.example.WeatherApi.entity.CityEntity;
import com.example.WeatherApi.mapping.CityMapper;
import dto.weatherapi.City;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

import static jakarta.ws.rs.core.Response.Status.BAD_REQUEST;

@Controller
@Path("/city")
@AllArgsConstructor
@NoArgsConstructor
public class CityController {
    @Inject
    private CityBusiness cityService;
    @Inject
    private CityMapper cityMapper;
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cityCreate(City city) {
        cityService.CreateCity(cityMapper.toEntity(city));
        return Response.ok("done").build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response cityFindAll(){
        List<CityEntity> rs = cityService.findAll();
        List<City> result = new ArrayList<>();
        rs.forEach(i -> result.add(cityMapper.toDTO(i)));
        return Response.ok(rs).build();

    }


    @GET
    @Path("/{name}")  // Spécifie que {name} est une partie variable de l'URL
    @Produces(MediaType.APPLICATION_JSON)
    public Response cityFind(@PathParam("name") String name) {
        CityEntity city = cityService.findByName(name);
        if(city == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        City cityDTO = cityMapper.toDTO(city);
        return Response.ok(cityDTO).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCityById(@PathParam("id") String id){
        cityService.deleteCityById(id);
        return Response.ok("done").build();

    }

    @PUT
    @Path("/{name}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response modifyCityByName(@PathParam("name") String name, City city) {
        if (!cityService.updateCityByName(name, cityMapper.toEntity(city))) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("City could not be updated")
                    .build();
        }
        return Response.ok("City updated successfully").build();
    }

}

package pl.polsl.controller;

import jersey.repackaged.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.model.Car;
import pl.polsl.model.Report;
import pl.polsl.repository.CarsRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Kuba on 3/19/2016.
 */
@Component
@Path("/cars")
@Produces(MediaType.APPLICATION_JSON)
public class CarsController {

    @Autowired
    private CarsRepository carsRepository;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Car> findAllCars() {
        return Lists.newArrayList(carsRepository.findAll());
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Car findOne(int id){
        return carsRepository.findOne(id);}
}

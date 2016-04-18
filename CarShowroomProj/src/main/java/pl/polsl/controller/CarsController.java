package pl.polsl.controller;

import jersey.repackaged.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.model.Car;
import pl.polsl.model.Report;
import pl.polsl.repository.CarsRepository;
import pl.polsl.repository.DictionaryRepository;
import pl.polsl.repository.ShowroomsRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Date;
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
    @Autowired
    private ShowroomsRepository showroomsRepository;
    @Autowired
    private DictionaryRepository dictionaryRepository;

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

    public void deleteCar(int id){
        carsRepository.delete(id);
    }

    public Car addCar(int name, Date dateProd, int showroom, int cost) {
            return carsRepository.save(new Car(dictionaryRepository.findOne(name),dateProd,
                    showroomsRepository.findOne(showroom),cost));
    }

    public Car editCar(int id, int idName, Date prodDate, int showroom, int cost) {
        Car car = carsRepository.findOne(id);

        car.setIdName(dictionaryRepository.findOne(idName));
        car.setProdDate(prodDate);
        car.setShowroom(showroomsRepository.findOne(showroom));
        car.setCost(cost);
  //      car.setContract();        //???????????????????????
        return carsRepository.save(car);
    }

}

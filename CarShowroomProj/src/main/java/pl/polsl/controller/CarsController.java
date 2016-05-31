package pl.polsl.controller;

import javafx.print.Collation;
import jersey.repackaged.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.Data;
import pl.polsl.model.Accessory;
import pl.polsl.model.Car;
import pl.polsl.model.Contract;
import pl.polsl.model.Report;
import pl.polsl.repository.CarsRepository;
import pl.polsl.repository.DictionaryRepository;
import pl.polsl.repository.ShowroomsRepository;
import pl.polsl.web.MainController;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    public void edit(int id){
        carsRepository.save(findOne(id));
    }

    public void deleteCar(int id){
        carsRepository.delete(id);
    }

    public Car addCar(int name, Date dateProd, int showroom, int cost, int order, Contract contract,Set<Accessory> accessoryList) {
        return carsRepository.save(new Car(dictionaryRepository.findOne(name),dateProd,
                showroomsRepository.findOne(showroom),cost, order, contract,accessoryList));
    }

    public Car editCar(int id, int idName, Date prodDate, int showroom, int cost, int order) {
        Car car = carsRepository.findOne(id);

        car.setCarName(dictionaryRepository.findOne(idName));
        car.setProdDate(prodDate);
        car.setShowroom(showroomsRepository.findOne(showroom));
        car.setCost(cost);
        car.setOrdered(order);
        return carsRepository.save(car);
    }

    /**
     * Created by: Aleksandra Chronowska
     * @param cars choosen cars
     * @return avaliable to choose cars
     */
    public Set<Car>findCarAvaliable(Set<Car>cars) {
        Set<Car>carList = findAllCars().stream().filter((Car car)->car.getContract()== null).collect(Collectors.toSet());
        List<Car>properList=new ArrayList<>();
            for(Car car : cars) {
                Optional<Car> result = carList.stream().filter(x -> x.getId() == car.getId()).findAny();
                if(result.isPresent())
                    properList.add(result.get());
            }

        carList.addAll(carList.stream().filter((Car car) -> car.getOrdered()==1).collect(Collectors.toSet()));
        carList.removeAll(properList);
        return carList;
    }

}

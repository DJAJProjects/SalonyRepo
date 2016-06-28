package pl.polsl.controller;

import jersey.repackaged.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.model.Accessory;
import pl.polsl.model.Car;
import pl.polsl.model.Contract;
import pl.polsl.repository.AccessoriesRepository;
import pl.polsl.repository.CarsRepository;
import pl.polsl.repository.DictionaryRepository;
import pl.polsl.repository.ShowroomsRepository;

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
 * Car controller class
 * @author Jakub Wieczorek
 * @version 1.0
 * Car controller class
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
    @Autowired
    private AccessoriesRepository accessoriesRepository;

    /**
     * Rest get method
     * @author Aleksadra Chronowska
     * @return list of all cars
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Car> findAllCars() {
        return Lists.newArrayList(carsRepository.findAll());
    }

    /**
     * Rest method find car by id
     * @author Aleksadra Chronowska
     * @param id given car id
     * @return car data by json
     */
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Car findOne(Integer id){
        return carsRepository.findOne(id);}


    /**
     * Method save object in repository.
     * Used to confirm contract in contract module.
     * @author Aleksadra Chronowska
     * @param id car id
     */
    public void edit(int id){
        carsRepository.save(findOne(id));
    }

    /**
     * Method delete car with given id
     * @param id car id
     */
    public void deleteCar(int id){
        Car car = findOne(id);
        Set<Accessory> set = car.getAccessories();
        for(Accessory a : set) {
            a.setCar(null);
        }
        carsRepository.delete(id);
    }

    /**
     * Method adding car to repository
     * @param name car name
     * @param dateProd car production date
     * @param showroom car showroom
     * @param cost car cost
     * @param order parameter if car is ordered (true) or not(false)
     * @param contract contract where car is solded
     * @param accessorySet car accessories list
     * @return added car object
     */
    public Car addCar(int name, Date dateProd, int showroom, int cost, int order, Contract contract, Set<Accessory> accessorySet) {
        Car car = carsRepository.save(new Car(dictionaryRepository.findOne(name),dateProd,
                showroomsRepository.findOne(showroom),cost, order, contract, accessorySet));
        for(Accessory a : car.getAccessories()) {
            a.setCar(car);
            accessoriesRepository.save(a);
        }
        return carsRepository.save(car);
    }

    /**
     * Method edit car in repository
     * @param idName car id name
     * @param prodDate car production date
     * @param showroom car showroom
     * @param cost car cost
     * @param order parameter if car is ordered (true) or not(false)
     * @param set car accessories list
     * @param previousSet
     * @return added car object
     */
    public Car editCar(int id, int idName, Date prodDate, int showroom, int cost, int order,Set<Accessory> set, Set<Accessory> previousSet) {
        Car car = carsRepository.findOne(id);
        car.setCarName(dictionaryRepository.findOne(idName));
        car.setProdDate(prodDate);
        car.setShowroom(showroomsRepository.findOne(showroom));
        car.setCost(cost);
        car.setOrdered(order);
        car.setAccessories(set);
        for(Accessory a : car.getAccessories()) {
            a.setCar(car);
            accessoriesRepository.save(a);
        }
        boolean flag;
        for(Accessory a : previousSet) {
            flag = false;
            for(Accessory a2 : car.getAccessories()) {
                if (a.getId() == a2.getId()) {
                    flag = true;
                }
            }
            if(flag || car.getAccessories().size() == 0) {
                a.setCar(null);
                accessoriesRepository.save(a);
            }
        }
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

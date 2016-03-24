package pl.polsl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.model.Car;
import pl.polsl.repository.CarRepository;

/**
 * Created by Kuba on 3/19/2016.
 */
@Component
public class CarController {
    @Autowired
    public CarRepository carRepository;

    public Car findCar(Long id){return carRepository.findOne(id);}
}

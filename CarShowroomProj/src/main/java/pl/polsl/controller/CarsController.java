package pl.polsl.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.repository.CarsRepository;

/**
 * Created by Kuba on 3/19/2016.
 */
@Component
public class CarsController {
    @Autowired
    public CarsRepository carsRepository;

}

package pl.polsl.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import pl.polsl.controller.CarController;

/**
 * Created by Kuba on 3/19/2016.
 */

@Controller
public class CarsController {
    @Autowired
    CarController carController;


}

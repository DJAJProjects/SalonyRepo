package pl.polsl.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.polsl.controller.CarsController;
import pl.polsl.controller.ReportsController;
import pl.polsl.model.Car;
import pl.polsl.model.Report;

import java.util.List;


// ###################TESTOWY#############################

@Controller
public class GreetingController {
    @Autowired
    private ReportsController reportsController;
    @Autowired
    private CarsController carsController;

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {


        Report r = reportsController.findReport(1);
        if(r != null){
            model.addAttribute("name", r.getContent());
            model.addAttribute("name2", r.getIdContractor().getName());
        }
        else{
            model.addAttribute("name", "dupa");
            model.addAttribute("name2", "dupa2");
        }
//        Car car = carsController.findOne(1);
        List<Car> listOfCars = carsController.findAllCars();
        if(listOfCars != null) {
            model.addAttribute("cars",listOfCars);
        }

        return "greeting";
    }

}
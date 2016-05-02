package pl.polsl.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.polsl.ViewMode;
import pl.polsl.controller.CarsController;
import pl.polsl.controller.DictionaryController;
import pl.polsl.controller.ShowroomsController;
import pl.polsl.model.Car;

import java.sql.Date;

/**
 * Created by Julia on 2016-04-13.
 */
@Controller
public class CarsWebController {

    @Autowired
    CarsController carsController;
    @Autowired
    ShowroomsController showroomsController;
    @Autowired
    DictionaryController dictionaryController;

    private ViewMode viewMode;

    @RequestMapping(value = "/cars")
    public String getCars(Model model) {
        model.addAttribute("cars",carsController.findAllCars());
        model.addAttribute("controlsPanelVisible", false);
        return "cars";
    }

    @RequestMapping(value ="/addNewCar")
    public String addNewCar(Model model){
        Car car = new Car();
        viewMode = ViewMode.INSERT;
        model.addAttribute("car",car);
        model.addAttribute("cars",carsController.findAllCars());
        model.addAttribute("showrooms", showroomsController.findAll());
        model.addAttribute("carNames", dictionaryController.findAllCarName());
        model.addAttribute("controlsPanelVisible", true);
        model.addAttribute("controlsDisabled", false);
        return "cars";
    }

    @RequestMapping(value = "/deleteCar/{id}")
    public String deleteCar(@PathVariable("id")int id) {
        carsController.deleteCar(id);
        return "redirect:/cars";
    }

    @RequestMapping(value = "/editCar/{id}")
    public String editCar(Model model, @PathVariable("id")int id) {
        viewMode = ViewMode.EDIT;
        model.addAttribute("cars",carsController.findAllCars());
        Car car = carsController.findOne(id);
        model.addAttribute("car",car);
        model.addAttribute("carNames", dictionaryController.findAllCarName());
        model.addAttribute("carNameId",car.getCarName().getId());
        model.addAttribute("showroomId",car.getShowroom().getId());
        model.addAttribute("prodDate",car.getProdDate());
        model.addAttribute("showrooms", showroomsController.findAll());
        model.addAttribute("controlsPanelVisible", true);
        model.addAttribute("controlsDisabled", false);
        return "cars";
    }

    @RequestMapping(value = "/viewCar/{id}")
    public String viewCar(Model model, @PathVariable("id")int id) {
        viewMode = ViewMode.VIEW_ALL;
        model.addAttribute("cars",carsController.findAllCars());
        Car car = carsController.findOne(id);
        model.addAttribute("car",car);
        model.addAttribute("carNames", dictionaryController.findAllCarName());
        model.addAttribute("carNameId",car.getCarName().getId());
        model.addAttribute("showroomId",car.getShowroom().getId());
        model.addAttribute("prodDate",car.getProdDate());
        model.addAttribute("showrooms", showroomsController.findAll());
        model.addAttribute("controlsPanelVisible", true);
        model.addAttribute("controlsDisabled", true);
        model.addAttribute("addForm",false);
        model.addAttribute("editForm",true);
        return "cars";
    }

    @RequestMapping(value = "/modifyCar", method = RequestMethod.POST)
    public String modifyCar(@RequestParam("id") int id, @RequestParam("name")int name, @RequestParam("prodDate")Date prodDate,
                            @RequestParam("showroom")int showroom, @RequestParam("cost")int cost) {
        if(viewMode == ViewMode.INSERT){
            Car car = carsController.addCar(name,prodDate,showroom,cost);
        } else if(viewMode == ViewMode.EDIT){
            Car car = carsController.editCar(id,name,prodDate,showroom,cost);
        }
        return "redirect:/cars";
    }

}

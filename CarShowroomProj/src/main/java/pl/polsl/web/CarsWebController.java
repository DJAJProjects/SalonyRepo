package pl.polsl.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

    private boolean visibleAddForm;
    private boolean visibleEditForm;
    private boolean visibleShowForm;

    @RequestMapping(value = "/cars", method = RequestMethod.GET)
    public String getCars(Model model) {
        visibleShowForm = true;
        model.addAttribute("cars",carsController.findAllCars());
        model.addAttribute("visibleShowForm", visibleShowForm);
        return "cars";
    }

    @RequestMapping(value ="/addNewCar", method = RequestMethod.GET)
    public String addNewCar(Model model){
        visibleAddForm = true;
        visibleShowForm = false;
        model.addAttribute("showrooms", showroomsController.findAll());
        model.addAttribute("carNames", dictionaryController.findAllCarName());
        model.addAttribute("visibleAddForm", visibleAddForm);
        model.addAttribute("visibleShowForm", visibleShowForm);
        visibleAddForm = false;
        return "cars";
    }

    @RequestMapping(value ="/addNewCar", method = RequestMethod.POST)
    public String addNewCar(@RequestParam("name") int name, @RequestParam(value = "dateProd") Date dateProd,
                         @RequestParam(value = "showroom")int showroom, @RequestParam(value = "cost")int cost){
        Car car = carsController.addCar(name,dateProd,showroom,cost);
        return "redirect:/cars";
    }

    @RequestMapping(value = "/deleteCar/{id}", method = RequestMethod.GET)
    public String deleteCar(@PathVariable("id")int id) {
        carsController.deleteCar(id);
        return "redirect:/cars";
    }

    @RequestMapping(value = "/editCar/{id}")
    public String editCar(Model model, @PathVariable("id")int id) {
        visibleEditForm = true;
        visibleShowForm = false;
        Car car = carsController.findOne(id);
        model.addAttribute("car",car);
        model.addAttribute("carNames", dictionaryController.findAllCarName());
        model.addAttribute("carNameId",car.getCarName().getId());
        model.addAttribute("showroomId",car.getShowroom().getId());
        model.addAttribute("showrooms", showroomsController.findAll());
        model.addAttribute("visibleEditForm", visibleAddForm);
        model.addAttribute("visibleShowForm", visibleShowForm);
        visibleEditForm = false;
        return "cars";
    }

    @RequestMapping(value = "/editCar", method = RequestMethod.POST)
    public String editCar(@PathVariable("id")int id, @RequestParam("name")int name, @RequestParam("prodDate")Date prodDate,
                          @RequestParam("showroom")int showroom, @RequestParam("cost")int cost) {
        Car car = carsController.editCar(id,name,prodDate,showroom,cost);
        return "redirect:/cars";
    }

}

package pl.polsl.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.polsl.ViewMode;
import pl.polsl.controller.CarsController;
import pl.polsl.controller.ContractsController;
import pl.polsl.controller.DictionaryController;
import pl.polsl.controller.ShowroomsController;
import pl.polsl.model.Car;
import pl.polsl.model.Contract;

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
    @Autowired
    ContractsController contractsController;

    private ViewMode viewMode;

    @RequestMapping(value = "/cars")
    public String getCars(Model model) {
        model.addAttribute("cars",carsController.findAllCars());
        model.addAttribute("controlsPanelVisible", false);
        return "cars";
    }

    @RequestMapping(value ="/addNewCar")
    public String addNewCar(Model model, @RequestParam(value="contract", required = false)Integer contract){
        Car car = new Car();
        viewMode = ViewMode.INSERT;
        model.addAttribute("car",car);
        model.addAttribute("cars",carsController.findAllCars());
        model.addAttribute("showrooms", showroomsController.findAll());
        model.addAttribute("carNames", dictionaryController.findAllCarName());
        model.addAttribute("controlsPanelVisible", true);
        model.addAttribute("controlsDisabled", false);
        model.addAttribute("disabledOrdered", 0);
        System.out.println("contract id: " + contract);
        if(contract != null){
            model.addAttribute("contract", contractsController.findOne(contract));
            model.addAttribute("ordered", 1);
            model.addAttribute("disabledOrdered", 1);
        }

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
        model.addAttribute("ordered",car.getOrdered());
        model.addAttribute("prodDate",car.getProdDate());
        model.addAttribute("showrooms", showroomsController.findAll());
        model.addAttribute("controlsPanelVisible", true);
        model.addAttribute("controlsDisabled", false);
        model.addAttribute("disabledOrdered", 0);

        return "cars";
    }

    @RequestMapping(value = "/viewCar/{id}")
    public String viewCar(Model model, @PathVariable("id")int id) {
        viewMode = ViewMode.VIEW_ALL;
        model.addAttribute("cars",carsController.findAllCars());
        Car car = carsController.findOne(id);
        model.addAttribute("car",car);
        System.out.println("order:" + car.getOrdered());
        model.addAttribute("carNames", dictionaryController.findAllCarName());
        model.addAttribute("carNameId",car.getCarName().getId());
        model.addAttribute("showroomId",car.getShowroom().getId());
        model.addAttribute("prodDate",car.getProdDate());
        model.addAttribute("ordered",car.getOrdered());
        model.addAttribute("showrooms", showroomsController.findAll());
        model.addAttribute("controlsPanelVisible", true);
        model.addAttribute("controlsDisabled", true);
        model.addAttribute("addForm",false);
        model.addAttribute("editForm",true);

        model.addAttribute("disabledOrdered", 0);

        return "cars";
    }

    @RequestMapping(value = "/modifyCar", method = RequestMethod.POST)
    public String modifyCar(RedirectAttributes redirectAttributes, @RequestParam("id") int id, @RequestParam("name")int name, @RequestParam("prodDate")Date prodDate,
                            @RequestParam("showroom")int showroom, @RequestParam("cost")int cost, @RequestParam(value="ordered", required = false)Integer order, @RequestParam(value="contract", required = false)Integer contract) {
        if(contract !=null) {
            Car car = carsController.addCar(name,prodDate,showroom,cost, 1, contractsController.findOne(contract));
            redirectAttributes.addAttribute("carId", car.getId());
            return "redirect:/contactAdditions";
        }
        if(viewMode == ViewMode.INSERT){
            Car car = carsController.addCar(name,prodDate,showroom,cost,order == null ? 0 : 1,null);
        } else if(viewMode == ViewMode.EDIT){
            Car car = carsController.editCar(id,name,prodDate,showroom,cost, order == null ? 0 : 1);
        }
        return "redirect:/cars";
    }

}

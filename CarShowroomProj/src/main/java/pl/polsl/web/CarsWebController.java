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
import pl.polsl.controller.*;
import pl.polsl.model.Accessory;
import pl.polsl.model.Car;

import java.io.Console;
import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Julia on 2016-04-13.
 */
@Controller
public class CarsWebController extends BaseWebController {

    @Autowired
    CarsController carsController;
    @Autowired
    ShowroomsController showroomsController;
    @Autowired
    DictionaryController dictionaryController;
    @Autowired
    ContractsController contractsController;
    @Autowired
    AccessoriesController accessoriesController;

    private ViewMode viewMode;
    private Set<Accessory> accessorySet = new HashSet<>();
    private Car car;
    private boolean flag;

    @RequestMapping(value = "/cars")
    public String getCars(Model model) {
        model.addAttribute("cars",carsController.findAllCars());
        model.addAttribute("controlsPanelVisible", false);
        refreshMenuPrivileges(model);
        flag = false;
        accessorySet.clear();
        return "cars";
    }

    @RequestMapping(value = "/carsDetails", method = RequestMethod.GET)
    public String carsDetails(Model model) {

        System.out.println("carDetails");

        model.addAttribute("controlsPanelVisible", true);
        model.addAttribute("cars",carsController.findAllCars());
        model.addAttribute("carNames", dictionaryController.findAllCarName());
        model.addAttribute("showrooms", showroomsController.findAll());
        model.addAttribute("accessory", accessoriesController.findAll());
        model.addAttribute("chosenAccessories",accessorySet);

        //GDY TRUE TO WYWALA NIE MOŻNA POBRAĆ COST
        //PAMIĘTAJ TO JAKOŚ OBEJŚĆ ŻEBY USER NIE MÓGŁ EDYTOWAĆ A WARTOŚĆ BYŁA POBIERANA
        model.addAttribute("editCostDisabled",false);

        if(viewMode == ViewMode.INSERT) {
            model.addAttribute("controlsDisabled", false);
            model.addAttribute("disabledOrdered", 0);
            if(!flag) {
                flag = true;
                model.addAttribute("controlsCostDisabled",true);
                model.addAttribute("car",car);
                return "cars";
            }
        } else if(viewMode == ViewMode.EDIT) {
            model.addAttribute("controlsDisabled", false);
            model.addAttribute("controlsCostDisabled",false);
            model.addAttribute("disabledOrdered", 0);
        } else if(viewMode == ViewMode.VIEW_ALL) {
            model.addAttribute("controlsDisabled", true);
            model.addAttribute("controlsCostDisabled",true);
            model.addAttribute("disabledOrdered", 0);
        }

        model.addAttribute("car",car);
        System.out.println("order:" + car.getOrdered());
        model.addAttribute("carNameId",car.getCarName().getId());
        model.addAttribute("showroomId",car.getShowroom().getId());
        model.addAttribute("prodDate",car.getProdDate());
        model.addAttribute("ordered",car.getOrdered());
        model.addAttribute("totalCost", car.getCost());

        return "cars";
    }

    @RequestMapping(value ="/addNewCar", method = RequestMethod.GET)
    public String addNewCar(Model model, @RequestParam(value="contract", required = false)Integer contract){
        viewMode = ViewMode.INSERT;
        car = new Car();
        accessorySet.clear();
        flag = false;
        model.addAttribute("controlsCostDisabled",true);

        System.out.println("contract id: " + contract);
        if(contract != null){
            model.addAttribute("contract",ContractWebController.contract.getId());
            model.addAttribute("ordered", 1);
            model.addAttribute("disabledOrdered", 1);
        }

        return "redirect:/carsDetails";
    }

    @RequestMapping(value = "/deleteCar/{id}")
    public String deleteCar(@PathVariable("id")int id) {
        carsController.deleteCar(id);
        flag = false;
        return "redirect:/cars";
    }

    @RequestMapping(value = "/editCar/{id}")
    public String editCar(Model model, @PathVariable("id")int id) {
        viewMode = ViewMode.EDIT;
        this.car = carsController.findOne(id);
        flag = false;
        accessorySet = car.getAccessories();
        model.addAttribute("controlsCostDisabled",false);

        return "redirect:/carsDetails";
    }

    @RequestMapping(value = "/viewCar/{id}")
    public String viewCar(Model model, @PathVariable("id")int id) {
        viewMode = ViewMode.VIEW_ALL;
        this.car = carsController.findOne(id);
        flag = false;
        accessorySet = car.getAccessories();
        model.addAttribute("controlsCostDisabled",false);

        return "redirect:/carsDetails";
    }

    @RequestMapping(value = "/modifyCar1", method = RequestMethod.POST)
    public String modifyCar1(Model model, RedirectAttributes redirectAttributes, @RequestParam("name")int name,
                             @RequestParam("prodDate")Date prodDate,@RequestParam("showroom")int showroom) {
        car.setCarName(dictionaryController.findOne(name));
        car.setProdDate(prodDate);
        car.setShowroom(showroomsController.findOne(showroom));
        car.setCost(Integer.parseInt(dictionaryController.findOne(name).getValue2()));
        flag = true;
        model.addAttribute("controlsCostDisabled",false);

        return "redirect:/carsDetails";
    }

    @RequestMapping(value = "/modifyCar", method = RequestMethod.POST)
    public String modifyCar(RedirectAttributes redirectAttributes, @RequestParam("id") int id,
                            @RequestParam("cost")int cost, @RequestParam(value="ordered", required = false)Integer order,
                            @RequestParam(value="contract", required = false)Integer contract) {
        if(contract !=null) {
            Car addCar = carsController.addCar(car.getCarName().getId(),car.getProdDate(),car.getShowroom().getId(),cost, 1, null,accessorySet);
            ContractWebController.contract.getCarList().add(addCar);
            redirectAttributes.addAttribute("carId", contract);
            return "redirect:/contactAdditions";
        }
        if(viewMode == ViewMode.INSERT){
            Car addCar = carsController.addCar(car.getCarName().getId(),car.getProdDate(),car.getShowroom().getId(),cost,order == null ? 0 : 1,null,accessorySet);
        } else if(viewMode == ViewMode.EDIT){
            Car editCar = carsController.editCar(id,car.getCarName().getId(),car.getProdDate(),car.getShowroom().getId(),cost, order == null ? 0 : 1);
        }
        return "redirect:/cars";
    }

    @RequestMapping(value ="/addAccessoryToCar", method = RequestMethod.POST)
    public String newAccessory(RedirectAttributes redirectAttributes, @RequestParam("accessoryId")Integer accessoryId) {
        Accessory part = accessoriesController.findOne(accessoryId);
        accessorySet.add(part);

        Integer tmpCost = car.getCost() + part.getCost();
        car.setCost(tmpCost);

        return "redirect:/carsDetails";
    }

    @RequestMapping(value ="/deleteChosenAccessories/{id}")
    public String deleteAccessory(@PathVariable("id")int id){
        accessorySet.remove(accessorySet.stream().filter(x-> (x.getId() == id)).findAny().get());
        return "redirect:/carsDetails";
    }

}

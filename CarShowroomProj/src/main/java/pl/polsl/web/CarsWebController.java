package pl.polsl.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.polsl.Data;
import pl.polsl.ViewMode;
import pl.polsl.controller.*;
import pl.polsl.model.Accessory;
import pl.polsl.model.Car;

import java.sql.Date;
import java.util.HashSet;
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
    private Set<Accessory> previousAccessorySet = new HashSet<>();
    private Car car;
    private boolean flag;
    private boolean orderedCar;
    private boolean moduleOnlyCar;

    @RequestMapping(value = "/cars", method = RequestMethod.GET)
    public String getCars(Model model) {
        if (Data.user == null) {
            model.asMap().clear();
            model.addAttribute("userNotLoggedIn", true);
            return "sign_in";
        } else if (!privilegesController.getReadPriv(Data.carsModuleValue, Data.user)) {
            model.asMap().clear();
            model.addAttribute("forbiddenAccess", true);
        } else {
            model.addAttribute("cars",carsController.findAllCars());
        }
        model.addAttribute("controlsPanelVisible", false);
        refreshMenuPrivileges(model);
        flag = false;
        orderedCar = false;
        accessorySet.clear();
        moduleOnlyCar = true;
        analisePrivileges(Data.carsModuleValue);
        model.addAttribute("insertEnabled", insertEnabled);
        model.addAttribute("updateEnabled", updateEnabled);
        model.addAttribute("deleteEnabled", deleteEnabled);
        return "cars";
    }

    @RequestMapping(value = "/carsDetails", method = RequestMethod.GET)
    public String carsDetails(Model model, @RequestParam(value="contract", required = false)Integer contract) {
        refreshMenuPrivileges(model);
        if(orderedCar){
            model.addAttribute("ordered", 1);
            model.addAttribute("disabledOrdered", 1);
        }

        model.addAttribute("controlsPanelVisible", true);
        model.addAttribute("cars",carsController.findAllCars());
        model.addAttribute("carNames", dictionaryController.findAllCarName());
        model.addAttribute("showrooms", showroomsController.findAll());
        model.addAttribute("accessory", accessoriesController.findFreeAccessories());
        model.addAttribute("chosenAccessories",accessorySet);
        model.addAttribute("editCostDisabled",true);

        if(viewMode == ViewMode.INSERT) {
            model.addAttribute("controlsDisabled", false);
            if(!orderedCar)
                model.addAttribute("disabledOrdered", 0);
            if(!flag) {
                flag = true;
                model.addAttribute("controlsCostDisabled",true);
                model.addAttribute("car",car);
                return "cars";
            }
        } else if(viewMode == ViewMode.EDIT) {
            model.addAttribute("controlsDisabled", false);
            model.addAttribute("disabledOrdered", 0);
        } else if(viewMode == ViewMode.VIEW_ALL) {
            model.addAttribute("controlsDisabled", true);
            model.addAttribute("controlsCostDisabled",true);
            model.addAttribute("disabledOrdered", 0);
        }

        model.addAttribute("car",car);
        if(viewMode == ViewMode.INSERT || (viewMode == ViewMode.EDIT && flag)) {
            model.addAttribute("controlsDisabledPart1", true);
        } else if(viewMode == ViewMode.EDIT &&  !flag) {
            model.addAttribute("controlsCostDisabled",true);
        } else if(viewMode == ViewMode.VIEW_ALL) {
            model.addAttribute("controlsDisabledPart1", true);
        }
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

        if(contract != null){
            orderedCar = true;
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
        accessorySet.clear();
        previousAccessorySet.clear();
        accessorySet.addAll(car.getAccessories());
        previousAccessorySet.addAll(accessorySet);
        model.addAttribute("controlsCostDisabled",false);

        return "redirect:/carsDetails";
    }

    @RequestMapping(value = "/viewCar/{id}")
    public String viewCar(Model model, @PathVariable("id")int id) {
        viewMode = ViewMode.VIEW_ALL;
        this.car = carsController.findOne(id);
        flag = false;accessorySet.clear();
        accessorySet = car.getAccessories();
        model.addAttribute("controlsCostDisabled",false);

        return "redirect:/carsDetails";
    }

    @RequestMapping(value = "/modifyCar1", method = RequestMethod.POST)
    public String modifyCar1(RedirectAttributes redirectAttributes, @RequestParam("name")int name,
                             @RequestParam("prodDate")Date prodDate,@RequestParam("showroom")int showroom) {
        car.setCarName(dictionaryController.findOne(name));
        car.setProdDate(prodDate);
        car.setShowroom(showroomsController.findOne(showroom));
        if(viewMode == ViewMode.INSERT) {
            car.setCost(Integer.parseInt(dictionaryController.findOne(name).getValue2()));
        }
        flag = true;
        if(orderedCar) {
            System.out.println("ordered car" + orderedCar);
            car.setOrdered(1);
        }

        return "redirect:/carsDetails";
    }

    @RequestMapping(value = "/modifyCar", method = RequestMethod.POST)
    public String modifyCar(RedirectAttributes redirectAttributes, @RequestParam("id") Integer id,
                            @RequestParam(value="ordered", required = false)Integer order,
                            @RequestParam(value="contract", required = false)Integer contract) {
        if(moduleOnlyCar) {
            if(viewMode == ViewMode.INSERT){
                Car addCar = carsController.addCar(car.getCarName().getId(),car.getProdDate(),car.getShowroom().getId(),car.getCost(),order == null ? 0 : 1,null,accessorySet);
            } else if(viewMode == ViewMode.EDIT){
                Car editCar = carsController.editCar(id,car.getCarName().getId(),car.getProdDate(),car.getShowroom().getId(),car.getCost(), order == null ? 0 : 1,accessorySet,previousAccessorySet);
            }
            return "redirect:/cars";
        }
        System.out.println("car order: " + car.getOrdered());
       if(car.getOrdered()!=null) {
           if (car.getOrdered() == 1) {
               Car addCar = carsController.addCar(car.getCarName().getId(), car.getProdDate(), car.getShowroom().getId(), car.getCost(), 1, null, accessorySet);
               ContractWebController.contract.getCarList().add(addCar);
               redirectAttributes.addAttribute("carId", contract);
               return "redirect:/contactAdditions";
           }
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
        Integer tmpCost = car.getCost();
        for(Accessory a : accessorySet) {
            if(a.getId() == id) {
                car.setCost(tmpCost - a.getCost());
                accessorySet.remove(a);
                break;
            }
        }
        return "redirect:/carsDetails";
    }

}

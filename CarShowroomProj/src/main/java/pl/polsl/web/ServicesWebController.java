package pl.polsl.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.polsl.ViewMode;
import pl.polsl.controller.*;
import pl.polsl.model.Service;

import java.sql.Date;

/**
 * Created by Julia on 2016-04-28.
 */
@Controller
public class ServicesWebController extends BaseWebController {

    @Autowired
    ServicesController servicesController;
    @Autowired
    DictionaryController dictionaryController;
    @Autowired
    WorkersController workersController;
    @Autowired
    CarsController carsController;
    @Autowired
    AccessoriesController accessoriesController;

    private ViewMode viewMode;

    @RequestMapping(value = "/services")
    public String getServices(Model model) {
        model.addAttribute("services",servicesController.findAll());
        model.addAttribute("controlsPanelVisible", false);
        refreshMenuPrivileges(model);
        return "services";
    }

    @RequestMapping(value ="/addNewService")
    public String addNewService(Model model){
        Service service = new Service();
        viewMode = ViewMode.INSERT;
        model.addAttribute("service",service);
        model.addAttribute("services",servicesController.findAll());
        model.addAttribute("types",dictionaryController.findAllService());
        model.addAttribute("servicemans",workersController.findAllServicemans());
        model.addAttribute("cars",carsController.findAllCars());
        model.addAttribute("accessories",accessoriesController.findAll());
        model.addAttribute("controlsPanelVisible", true);
        model.addAttribute("controlsDisabled", false);
        return "services";
    }

    @RequestMapping(value = "/deleteService/{id}")
    public String deleteService(@PathVariable("id")int id) {
        servicesController.deleteService(id);
        return "redirect:/services";
    }

    @RequestMapping(value = "/editService/{id}")
    public String editService(Model model, @PathVariable("id")int id) {
        viewMode = ViewMode.EDIT;
        model.addAttribute("services",servicesController.findAll());
        Service service = servicesController.findOne(id);
        model.addAttribute("service",service);
        model.addAttribute("serviceType",service.getServiceType());
        model.addAttribute("serviceServiceman",service.getServiceman());
        model.addAttribute("serviceCar",service.getCar());
        model.addAttribute("serviceAccessory",service.getAccessory());
        model.addAttribute("serviceCost",service.getCost());
        model.addAttribute("dateConducted",service.getDateConducted());
        model.addAttribute("types",dictionaryController.findAllService());
        model.addAttribute("servicemans",workersController.findAllServicemans());
        model.addAttribute("cars",carsController.findAllCars());
        model.addAttribute("accessories",accessoriesController.findAll());
        model.addAttribute("controlsPanelVisible", true);
        model.addAttribute("controlsDisabled", false);
        return "services";
    }

    @RequestMapping(value = "/viewService/{id}")
    public String viewService(Model model, @PathVariable("id")int id) {
        viewMode = ViewMode.VIEW_ALL;
        model.addAttribute("services",servicesController.findAll());
        Service service = servicesController.findOne(id);
        model.addAttribute("service",service);
        model.addAttribute("serviceType",service.getServiceType());
        model.addAttribute("serviceServiceman",service.getServiceman());
        model.addAttribute("serviceCar",service.getCar());
        model.addAttribute("serviceAccessory",service.getAccessory());
        model.addAttribute("serviceCost",service.getCost());
        model.addAttribute("dateConducted",service.getDateConducted());
        model.addAttribute("types",dictionaryController.findAllService());
        model.addAttribute("servicemans",workersController.findAllServicemans());
        model.addAttribute("cars",carsController.findAllCars());
        model.addAttribute("accessories",accessoriesController.findAll());
        model.addAttribute("controlsPanelVisible", true);
        model.addAttribute("controlsDisabled", true);
        return "services";
    }

    @RequestMapping(value = "/modifyService", method = RequestMethod.POST)
    public String modifyService(@RequestParam("id")int id, @RequestParam("type") int type, @RequestParam("idServiceman")int idServiceman,
                                @RequestParam("idCar")int idCar, @RequestParam("idAccessory")int idAccessory,
                                @RequestParam(value = "cost")int cost, @RequestParam("dateConducted")Date dateConducted) {
        if(viewMode == ViewMode.INSERT){
            servicesController.addService(type,idServiceman,idCar,idAccessory,cost,dateConducted);
        } else if(viewMode == ViewMode.EDIT) {
            servicesController.editService(id, type, idServiceman, idCar, idAccessory, cost, dateConducted);
        }
        return "redirect:/services";
    }

}

package pl.polsl.web;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.View;
import pl.polsl.ViewMode;
import pl.polsl.controller.*;
import pl.polsl.model.Accessory;
import pl.polsl.model.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private Service service;
    private boolean flag;
    private boolean serviceCost;
    private boolean accessoryCost;
    private boolean editPart1;
    private boolean editPart2;
    private boolean changeServiceType;
    private Accessory previousAccessory;
    int previousServiceTypeId;

    @RequestMapping(value = "/services")
    public String getServices(Model model) {
        model.addAttribute("services",servicesController.findAll());
        model.addAttribute("controlsPanelVisible", false);
        refreshMenuPrivileges(model);
        analisePrivileges("Serwisy");
        model.addAttribute("insertEnabled", insertEnabled);
        model.addAttribute("updateEnabled", updateEnabled);
        model.addAttribute("deleteEnabled", deleteEnabled);
        flag = false;
        return "services";
    }

    @RequestMapping(value = "/servicesDetails", method = RequestMethod.GET)
    public String servicesDetails(Model model) {
        refreshMenuPrivileges(model);
        model.addAttribute("controlsPanelVisible", true);
        model.addAttribute("services",servicesController.findAll());
        model.addAttribute("types",dictionaryController.findAllService());
        model.addAttribute("servicemans",workersController.findAllServicemans());
        model.addAttribute("cars",carsController.findAllCars());
        model.addAttribute("serviceObjects",dictionaryController.findAllSubservice());
        model.addAttribute("controlsDisabledCost",true);
        if(viewMode == ViewMode.EDIT || viewMode == ViewMode.INSERT) {
            if(editPart1 && !editPart2) {
                model.addAttribute("controlsDisabledPart1",true);
                model.addAttribute("controlsDisabledPart2",false);
                model.addAttribute("controlsDisabledPart3",true);
            } else if(editPart2) {
                model.addAttribute("controlsDisabledPart1",true);
                model.addAttribute("controlsDisabledPart2",true);
                model.addAttribute("controlsDisabledPart3",false);
            } else {
                model.addAttribute("controlsDisabledPart1",false);
                model.addAttribute("controlsDisabledPart2",true);
                model.addAttribute("controlsDisabledPart3",true);
            }
        }
        if(viewMode == ViewMode.INSERT) {
            model.addAttribute("controlsDisabled", false);
            model.addAttribute("accessories",accessoriesController.findFreeAccessories());
            if(!flag) {
                flag = true;
                model.addAttribute("service",service);
                return "services";
            }
        } else if(viewMode == ViewMode.EDIT) {
            Set<Accessory> set = new HashSet<>();
            set = accessoriesController.findFreeAccessories();
            if(!changeServiceType && service.getServiceType().getId() == 47) {
                set.add(service.getAccessory());
            }
            model.addAttribute("accessories",set);
        } else if(viewMode == ViewMode.VIEW_ALL) {
            model.addAttribute("accessories",accessoriesController.findAll());
            model.addAttribute("controlsDisabledPart1",true);
            model.addAttribute("controlsDisabledPart2",true);
            model.addAttribute("controlsDisabledPart3",true);
        }
        model.addAttribute("service",service);
        model.addAttribute("serviceTypeId",service.getServiceType().getId());
        model.addAttribute("serviceCarId",service.getCar().getId());
        if(viewMode != ViewMode.INSERT) {
            model.addAttribute("serviceServicemanId", service.getServiceman().getId());
        }
        if(viewMode == ViewMode.INSERT && service.getServiceType().getId() == 47) {
            model.addAttribute("part", true);
            model.addAttribute("serv", false);
            if(editPart2) {
                model.addAttribute("serviceAccessoryId", service.getAccessory().getId());
            }
        } else if(viewMode == ViewMode.INSERT) {
            model.addAttribute("part", false);
            model.addAttribute("serv", true);
            if(editPart2) {
                model.addAttribute("serviceObjectId", service.getSubserviceType().getId());
            }
        } else if(viewMode != ViewMode.INSERT) {
            if(!changeServiceType) {
                if(service.getServiceType().getId() == 47) {
                    model.addAttribute("part", true);
                    model.addAttribute("serv", false);
                    model.addAttribute("serviceAccessoryId", service.getAccessory().getId());
                } else {
                    model.addAttribute("part", false);
                    model.addAttribute("serv", true);
                    model.addAttribute("serviceObjectId", service.getSubserviceType().getId());
                }
            } else {
                if (previousServiceTypeId == 47) {
                    model.addAttribute("part", false);
                    model.addAttribute("serv", true);
                    if(editPart2) {
                        model.addAttribute("serviceObjectId", service.getSubserviceType().getId());
                    }
                } else if(previousServiceTypeId != 0){
                    model.addAttribute("part", true);
                    model.addAttribute("serv", false);
                    if(editPart2) {
                        model.addAttribute("serviceAccessoryId", service.getAccessory().getId());
                    }
                }
            }
        }
        model.addAttribute("cost",service.getCost());
        model.addAttribute("dateConducted",service.getDateConducted());

        return "services";
    }

    @RequestMapping(value ="/addNewService")
    public String addNewService(Model model){
        viewMode = ViewMode.INSERT;
        flag = false;
        service = new Service();
        editPart1 = editPart2 = false;
        changeServiceType = false;
        previousServiceTypeId = 0;
        return "redirect:/servicesDetails";
    }

    @RequestMapping(value = "/deleteService/{id}")
    public String deleteService(@PathVariable("id")int id) {
        servicesController.deleteService(id);
        flag = false;
        previousServiceTypeId = 0;
        return "redirect:/servicesDetails";
    }

    @RequestMapping(value = "/editService/{id}")
    public String editService(Model model, @PathVariable("id")int id) {
        viewMode = ViewMode.EDIT;
        service = servicesController.findOne(id);
        previousAccessory = service.getAccessory();
        flag = false;
        editPart1 = editPart2 = false;
        changeServiceType = false;
        previousServiceTypeId = 0;
        return "redirect:/servicesDetails";
    }

    @RequestMapping(value = "/viewService/{id}")
    public String viewService(Model model, @PathVariable("id")int id) {
        viewMode = ViewMode.VIEW_ALL;
        service = servicesController.findOne(id);
        flag = false;
        editPart1 = editPart2 = false;
        changeServiceType = false;
        previousServiceTypeId = 0;
        return "redirect:/servicesDetails";
    }

    @RequestMapping(value = "/modifyServiceType", method = RequestMethod.POST)
    public String modifyService1(Model model, @RequestParam("type") int type, @RequestParam("idCar")int idCar ){
        if(viewMode == ViewMode.EDIT) {
            previousServiceTypeId = service.getServiceType().getId();
            if (previousServiceTypeId != type) {
                if(type == 47 || previousServiceTypeId == 47) {
                    changeServiceType = true;
                }
            }
        }
        service.setServiceType(dictionaryController.findOne(type));
        service.setCar(carsController.findOne(idCar));
        if(type == 31) {    //gw
            serviceCost = accessoryCost = false;
        } else if(type == 32) {       //pgw
            serviceCost = true;
            accessoryCost = false;
        } else {        //mc
            serviceCost = false;
            accessoryCost = true;
        }
        editPart1 = true;
        return "redirect:/servicesDetails";
    }

    @RequestMapping(value = "/modifyServiceObject", method = RequestMethod.POST)
    public String modifyService2(@RequestParam("idServiceObject")int idServiceObject) {
        if(accessoryCost) {
            if(viewMode == ViewMode.EDIT) {
                previousAccessory = service.getAccessory();
            }
            service.setAccessory(accessoriesController.findOne(idServiceObject));
            service.setCost(accessoriesController.findOne(idServiceObject).getCost() + accessoriesController.findOne(idServiceObject).getAssemblyCost());
        } else {
            service.setSubserviceType(dictionaryController.findOne(idServiceObject));
            if(serviceCost) {
                service.setCost(Integer.parseInt(dictionaryController.findOne(idServiceObject).getValue2()));
            } else {
                service.setCost(0);
            }
        }
        editPart2 = true;
        return "redirect:/servicesDetails";
    }

    @RequestMapping(value = "/modifyService", method = RequestMethod.POST)
    public String modifyService(@RequestParam("id") int id, @RequestParam("idServiceman")int idServiceman,
                                @RequestParam("dateConducted")Date dateConducted) {
        if(viewMode == ViewMode.INSERT){
            if(accessoryCost)
                servicesController.addService(service.getServiceType().getId(),idServiceman,service.getCar().getId(),service.getAccessory().getId(),0,service.getCost(),dateConducted);
            else
                servicesController.addService(service.getServiceType().getId(),idServiceman,service.getCar().getId(),0,service.getSubserviceType().getId(),service.getCost(),dateConducted);
        } else if(viewMode == ViewMode.EDIT) {
            if(accessoryCost) {
                servicesController.editService(id, service.getServiceType().getId(), idServiceman, service.getCar().getId(), service.getAccessory().getId(), 0, service.getCost(), dateConducted, previousAccessory.getId());
            } else {
                servicesController.editService(id, service.getServiceType().getId(), idServiceman, service.getCar().getId(), 0, service.getSubserviceType().getId(), service.getCost(), dateConducted, 0);
            }
        }
        return "redirect:/services";
    }

}

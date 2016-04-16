package pl.polsl.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.polsl.controller.CarsController;
import pl.polsl.controller.ContractorsController;
import pl.polsl.controller.ContractsController;
import pl.polsl.controller.PromotionsController;
import pl.polsl.model.Car;
import pl.polsl.model.Contract;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aleksandra on 2016-04-07.
 */
@Controller
public class ContractWebController {
    @Autowired
    private ContractsController contractsController;
    @Autowired
    private CarsController carsController;
    @Autowired
    private ContractorsController contractorsController;
    private List<Car>choosenCarList = new ArrayList<Car>();
    private int currentContractId = 0;

    @RequestMapping(value ="/contracts", method = RequestMethod.GET)
    public String all(Model model){
        model.addAttribute("contracts", contractsController.findAllContracts());
        return "contracts";
    }

    @RequestMapping(value ="/addContract", method = RequestMethod.GET)
    public String add(Model model){
        model.addAttribute("contracts", contractsController.findAllContracts());
        model.addAttribute("activeAddView", 1);
        model.addAttribute("contractors",contractorsController.findAllContractors());

        return "contracts";
    }

    @RequestMapping(value ="/deleteContract/{id}")
    public String deleteContract(@PathVariable("id")int id){
        contractsController.delete(id);
        return "redirect:/contracts/";
    }

    @RequestMapping(value ="/car", method = RequestMethod.GET)
    public String addCar(Model model){
        model.addAttribute("contracts", contractsController.findAllContracts());
//        model.addAttribute("activeAddView", 1);
        model.addAttribute("contractors",contractorsController.findAllContractors());
        model.addAttribute("carView",1);
        model.addAttribute("cars",carsController.findAllCars());
        model.addAttribute("choosenCar", choosenCarList);
        return "contracts";
    }

    @RequestMapping(value ="/addCar", method = RequestMethod.POST)
    public String newCar(RedirectAttributes redirectAttributes, @RequestParam("car") int car) {
        choosenCarList.add(carsController.findOne(car));
        System.out.println("VALUE: " + carsController.findOne(car).getCarName().getValue());
        Contract con = contractsController.findOne(currentContractId);
        con.getCarList().add(carsController.findOne(car));
        contractsController.edit(con);
        carsController.edit(car);
        return "redirect:/car";
    }
    @RequestMapping(value ="/addNew", method = RequestMethod.POST)
    public String newContract(RedirectAttributes redirectAttributes, @RequestParam("cost") int cost, @RequestParam("client") int clientId) {
        Contract con  = contractsController.addNew(cost,contractorsController.findOne(clientId));
        currentContractId = con.getId();
        redirectAttributes.addAttribute("activeAddView", 0);
        return "redirect:/addContract/";
    }
}

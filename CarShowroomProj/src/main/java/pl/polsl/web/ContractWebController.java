package pl.polsl.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.polsl.controller.*;
import pl.polsl.model.Accessory;
import pl.polsl.model.Car;
import pl.polsl.model.Contract;
import pl.polsl.model.Promotion;
import sun.swing.BakedArrayList;

import java.util.ArrayList;
import java.util.Date;
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
    @Autowired
    private AccessoriesController accessoriesController;
    @Autowired
    private PromotionsController promotionsController;

    private List<Car>choosenCarList = new ArrayList<Car>();
    private List<Accessory>choosenAccessories = new ArrayList<Accessory>();
    private List<Accessory>accessoryList = new ArrayList<Accessory>();
    private List<Promotion>choosenPromotionList = new ArrayList<Promotion>();

    private int currentContractId = 0;
    private Date currentDate;

    @RequestMapping(value ="/contracts", method = RequestMethod.GET)
    public String all(Model model){
        model.addAttribute("contracts", contractsController.findAllContracts());
        choosenCarList.clear();
        choosenAccessories.clear();
        model.addAttribute("promotionView",0);
        System.out.println("ACTUAL: " + promotionsController.findActual(new Date()).get(0).getName());
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

    @RequestMapping(value = "/contactAdditions", method = RequestMethod.GET)
    public String addCar(Model model){
        model.addAttribute("contracts", contractsController.findAllContracts());
        model.addAttribute("contractors",contractorsController.findAllContractors());
        model.addAttribute("carView",1);
        model.addAttribute("cars",carsController.findAllCars());
        model.addAttribute("choosenCar", choosenCarList);
        model.addAttribute("choosenAccessories", choosenAccessories);
        model.addAttribute("choosenPromotion", choosenPromotionList);
        model.addAttribute("accessories", accessoriesController.findAll());
        if(promotionsController.findActual(currentDate).size() != 0) {
            model.addAttribute("promotionView",1);
        }
        model.addAttribute("promotions", promotionsController.findActual(currentDate));

        return "contracts";
    }

    @RequestMapping(value ="/addCar", method = RequestMethod.POST)
     public String newCar(RedirectAttributes redirectAttributes, @RequestParam("car") int car) {
        Car c = carsController.findOne(car);
        choosenCarList.add(c);
        Contract con = contractsController.findOne(currentContractId);
        System.out.println(c.getCarName().getValue());
        con.getCarList().add(c);
        carsController.edit(car);
        con.setTotalCost(con.getTotalCost() + c.getCost());
        contractsController.edit(con);
        return "redirect:/contactAdditions";
    }

    @RequestMapping(value ="/addPromotion", method = RequestMethod.POST)
    public String newPromotion(RedirectAttributes redirectAttributes, @RequestParam("promotion") int promotion) {
        Promotion prom = promotionsController.findOne(promotion);
        choosenPromotionList.add(prom);
        Contract con = contractsController.findOne(currentContractId);
        con.getPromotions().add(prom);
        promotionsController.edit(prom);
        con.setTotalCost(con.getTotalCost() - (int)con.getTotalCost() * prom.getPercValue()/100);
        System.out.println("KWOTA: " + con.getTotalCost());
        contractsController.edit(con);

        return "redirect:/contactAdditions";
    }

    @RequestMapping(value ="/addAccessory", method = RequestMethod.POST)
    public String newAccesory(RedirectAttributes redirectAttributes, @RequestParam("accessory") int accessory) {
        Accessory part = accessoriesController.findOne(accessory);
        choosenAccessories.add(part);
        Contract con = contractsController.findOne(currentContractId);
        con.getAccessoryList().add(part);
        System.out.println(part.getAccessory().getValue());
        accessoriesController.edit(accessory);
        con.setTotalCost(con.getTotalCost() + part.getCost());
        System.out.println("KWOTA: " + con.getTotalCost());
        contractsController.edit(con);

        return "redirect:/contactAdditions";
    }

    @RequestMapping(value ="/addNew", method = RequestMethod.POST)
    public String newContract(RedirectAttributes redirectAttributes, @RequestParam("client") int clientId) {
        Contract con  = contractsController.addNew(contractorsController.findOne(clientId));
        currentContractId = con.getId();
        redirectAttributes.addAttribute("activeAddView", 0);
        currentDate = new Date();
        return "redirect:/contactAdditions/";
    }
}

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
import pl.polsl.model.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    @Autowired
    private InvoiceController invoiceController;
    @Autowired
    private DictionaryController dictionaryController;

    private int currentContractId = 0;
    private Date currentDate;

    @RequestMapping(value ="/invoiceGenerate", method = RequestMethod.GET)
    public String generateInvoice(Model model){
        model.addAttribute("dateCreated", currentDate);
        Contract con = contractsController.findOne(currentContractId);
        model.addAttribute("showroom", con.getWorker().getShowroom());
        model.addAttribute("contract", con);
        model.addAttribute("paymentforms", dictionaryController.findAllPaymentForm());
        model.addAttribute("invoiceTypes", dictionaryController.findAllInvoiceType());
        return "invoice_generate";
    }

    @RequestMapping(value ="/generateNew", method = RequestMethod.POST)
    public String newInvoice(RedirectAttributes redirectAttributes, @RequestParam("paymentForm") int paymentFormId, @RequestParam("invoiceType") int invoiceTypeId, @RequestParam(value="date", required = false)String date) {

        Invoice invoice = invoiceController.addNew(contractsController.findOne(currentContractId),paymentFormId, invoiceTypeId);
        contractsController.findOne(currentContractId).setInvoice(invoice);
        if(date != null) {
           System.out.println(date);
           SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yyyy");
           try {
               Date d = sdf.parse(date);
               invoice.setPaymentDeadline(d);
           } catch (ParseException e) {
               e.printStackTrace();
           }
       }
        contractsController.edit(contractsController.findOne(currentContractId));
        return "redirect:/contracts";
    }

    @RequestMapping(value ="/confirmContract", method = RequestMethod.POST)
    public String addInvoice(RedirectAttributes redirectAttributes, @RequestParam("client") int clientId) {
        currentDate = new Date();
        contractsController.findOne(currentContractId).setContractor(contractorsController.findOne(clientId));
        contractsController.edit(contractsController.findOne(currentContractId));
        return "redirect:/invoiceGenerate";
    }

    @RequestMapping(value ="/contracts", method = RequestMethod.GET)
    public String all(Model model){
        model.addAttribute("contracts", contractsController.findAllContracts());
        model.addAttribute("promotionView",0);
        model.addAttribute("carView",0);
        currentContractId = 0;
        return "contracts";
    }

    @RequestMapping(value = "/contactAdditions", method = RequestMethod.GET)
    public String addCar(Model model){
        Contract con;
        if(currentContractId == 0) {
            con = contractsController.addNew();
            currentDate = new Date();
            currentContractId = con.getId();
        }
        else
            con = contractsController.findOne(currentContractId);

        model.addAttribute("contracts", contractsController.findAllContracts());
        model.addAttribute("contractors",contractorsController.findAllContractors());
        model.addAttribute("carView",1);

        if(carsController.findCarAvaliable() !=null)
            model.addAttribute("cars",carsController.findCarAvaliable());
        model.addAttribute("choosenCar", con.getCarList());
        model.addAttribute("choosenAccessories", con.getAccessoryList());
        model.addAttribute("choosenPromotion", con.getPromotions());
        if(accessoriesController.findAccessoriesAvaliable() != null)
            model.addAttribute("accessories", accessoriesController.findAccessoriesAvaliable());
        if(promotionsController.findActual(currentDate).size() != 0) {
            model.addAttribute("promotionView",1);
        }
        model.addAttribute("promotions", promotionsController.findActual(currentDate));

        return "contracts";
    }

    @RequestMapping(value ="/deleteContract/{id}")
    public String deleteContract(@PathVariable("id")int id){
        contractsController.delete(id);
        return "redirect:/contracts/";
    }

    @RequestMapping(value ="/deleteChoosenCar/{id}")
    public String deleteCarFromList(@PathVariable("id")int id){
        contractsController.findOne(currentContractId).getCarList().remove(carsController.findOne(id));
        contractsController.findOne(currentContractId).setTotalCost(contractsController.findOne(currentContractId).getTotalCost() - carsController.findOne(id).getCost());
        carsController.findOne(id).setContract(null);
        carsController.edit(id);
        contractsController.edit(contractsController.findOne(currentContractId));
        return "redirect:/contactAdditions/";
    }

    @RequestMapping(value ="/deleteChoosenAccessories/{id}")
    public String deleteAccessory(@PathVariable("id")int id){
        contractsController.findOne(currentContractId).getAccessoryList().remove(accessoriesController.findOne(id));
        contractsController.findOne(currentContractId).setTotalCost(contractsController.findOne(currentContractId).getTotalCost() - accessoriesController.findOne(id).getCost());
        accessoriesController.findOne(id).setContract(null);
        accessoriesController.edit(id);
        contractsController.edit(contractsController.findOne(currentContractId));
        return "redirect:/contactAdditions/";
    }

    @RequestMapping(value ="/deleteChoosenPromotion/{id}")
    public String deletePromotion(@PathVariable("id")int id){
        contractsController.findOne(currentContractId).getPromotions().remove(promotionsController.findOne(id));
        contractsController.findOne(currentContractId).setTotalCost(contractsController.findOne(currentContractId).getTotalCost() +  (int)contractsController.findOne(currentContractId).getTotalCost() * promotionsController.findOne(id).getPercValue()/100);
        promotionsController.findOne(id).getContracts().remove(contractsController.findOne(currentContractId));
        promotionsController.edit(promotionsController.findOne(id));
        contractsController.edit(contractsController.findOne(currentContractId));
        return "redirect:/contactAdditions/";
    }

    @RequestMapping(value ="/addCar", method = RequestMethod.POST)
     public String newCar(RedirectAttributes redirectAttributes, @RequestParam("car") int car) {
        Car c = carsController.findOne(car);
        Contract con = contractsController.findOne(currentContractId);
        System.out.println(c.getCarName().getValue());
        con.getCarList().add(c);
        c.setContract(con);
        carsController.edit(car);
        con.setTotalCost(con.getTotalCost() + c.getCost());
        contractsController.edit(con);
        return "redirect:/contactAdditions";
    }

    @RequestMapping(value ="/addPromotion", method = RequestMethod.POST)
    public String newPromotion(RedirectAttributes redirectAttributes, @RequestParam("promotion") int promotion) {
        Promotion prom = promotionsController.findOne(promotion);
        Contract con = contractsController.findOne(currentContractId);
        con.getPromotions().add(prom);
        promotionsController.edit(prom);
        con.setTotalCost(con.getTotalCost() - (int)con.getTotalCost() * prom.getPercValue()/100);
        contractsController.edit(con);

        return "redirect:/contactAdditions";
    }

    @RequestMapping(value ="/addAccessory", method = RequestMethod.POST)
    public String newAccesory(RedirectAttributes redirectAttributes, @RequestParam("accessory") int accessory) {
        Accessory part = accessoriesController.findOne(accessory);
        Contract con = contractsController.findOne(currentContractId);
        con.getAccessoryList().add(part);
        System.out.println(part.getAccessory().getValue());
        part.setContract(con);
        accessoriesController.edit(accessory);
        con.setTotalCost(con.getTotalCost() + part.getCost());
        contractsController.edit(con);

        return "redirect:/contactAdditions";
    }

}

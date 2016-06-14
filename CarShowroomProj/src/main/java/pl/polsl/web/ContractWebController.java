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
import pl.polsl.model.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Aleksandra on 2016-04-07.
 */
@Controller
public class ContractWebController extends BaseWebController{
    @Autowired
    private ContractsController contractsController;
    @Autowired
    private CarsController carsController;
    @Autowired
    private AccessoriesController accessoriesController;
    @Autowired
    private PromotionsController promotionsController;
    @Autowired
    private InvoiceController invoiceController;
    @Autowired
    private DictionaryController dictionaryController;
    @Autowired
    private ContractorsController contractorsController;
    private Set<Car> carList = new LinkedHashSet<Car>();
    private Set<Accessory> accessoryList = new LinkedHashSet<Accessory>();
    private Set<Promotion>promotionList = new LinkedHashSet<Promotion>();
    private ViewMode viewMode;

    public static Contract contract;


    /**
     * INVOICE MAIN VIEW
     *
     */
    @RequestMapping(value ="/invoiceGenerate", method = RequestMethod.GET)
    public String generateInvoice(Model model, @RequestParam("contract") int id, @RequestParam(value="invoice", required = false)Invoice invoice){
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        model.addAttribute("dateCreated",df.format(Calendar.getInstance().getTime()));
        Contract con = contractsController.findOne(id);
        model.addAttribute("showroom", con.getWorker().getShowroom());
        model.addAttribute("contract", con);
        model.addAttribute("paymentforms", dictionaryController.findAllPaymentForm());
        model.addAttribute("invoiceTypes", dictionaryController.findAllInvoiceType());
        if(invoice != null) {
            model.addAttribute("paymentForm", invoice.getPaymentForm());
            model.addAttribute("invoiceType", invoice.getInvoiceType());
            if(invoice.getPaymentDeadline()!=null){
                model.addAttribute("paymentDeadline", invoice.getPaymentDeadline());
            }
            if(invoice.getDateSold() !=null) {
                model.addAttribute("dataSold", invoice.getDateSold());
            }
            model.addAttribute("disabledButtons", 1);
        }
        return "invoice_generate";
    }

    /**
     * NEW INVOICE(FACTURE)
     *
     */
    @RequestMapping(value ="/generateNew", method = RequestMethod.POST)
    public String newInvoice(RedirectAttributes redirectAttributes, @RequestParam("contract_id") int contractId, @RequestParam("paymentForm") int paymentFormId, @RequestParam("invoiceType") int invoiceTypeId, @RequestParam(value="date", required = false)String date,  @RequestParam(value="dateSold", required = false)String dateSold) {
        Invoice invoice = invoiceController.addNew(contractsController.findOne(contractId),paymentFormId, invoiceTypeId);
        contractsController.findOne(contractId).setInvoice(invoice);

        if(date != "") {
                java.sql.Date sqlDate=  java.sql.Date.valueOf(date);
                invoice.setPaymentDeadline(sqlDate);
        }
        if(dateSold != null) {
            java.sql.Date sqlDateSold = java.sql.Date.valueOf(dateSold);
            invoice.setDateSold(sqlDateSold);

        }
        contractsController.edit(contractsController.findOne(contractId));
        return "redirect:/contracts";
    }

    /**
     * SUBMIT NEW CONTRACT, ADD TO DATABASE
     *
     */
    @RequestMapping(value ="/confirmContract", method = RequestMethod.POST)
    public String addInvoice(RedirectAttributes redirectAttributes, @RequestParam("client") int clientId) {
        Contract con;
        if(viewMode == ViewMode.EDIT) {

            con = contractsController.updateContract(contract, carList, accessoryList, promotionList, clientId);

        }
        else
            con = contractsController.addNew(carList, accessoryList, promotionList, clientId);
        viewMode = ViewMode.VIEW_AFTER_INSERT;
        redirectAttributes.addAttribute("contract",con);
        return "redirect:/contactAdditions";
    }

    @RequestMapping(value ="/chooseGenerateOption", method = RequestMethod.POST)
    public String chooseOption(RedirectAttributes redirectAttributes, @RequestParam("contract_id") int contract){

        redirectAttributes.addAttribute("contract",contractsController.findOne(contract));
        return "redirect:/invoiceGenerate/";
    }

    /**
     * MAIN CONTRACT VIEW
     *
     */
    @RequestMapping(value ="/contracts", method = RequestMethod.GET)
    public String all(Model model){
        viewMode = ViewMode.DEFAULT;
        contract = null;
        model.addAttribute("carView",0);

        if (Data.user == null) {
            model.asMap().clear();
            model.addAttribute("userNotLoggedIn", true);
            return "sign_in";
        } else if (!privilegesController.getReadPriv(Data.contractModuleValue, Data.user)) {
            model.asMap().clear();
            model.addAttribute("forbiddenAccess", true);
        } else {
            model.addAttribute("contracts", contractsController.findContracts());
        }

        analisePrivileges(Data.contractModuleValue);
        model.addAttribute("insertEnabled", insertEnabled);
        model.addAttribute("updateEnabled", updateEnabled);
        model.addAttribute("deleteEnabled", deleteEnabled);
        refreshMenuPrivileges(model);
        return "contracts";
    }

    /**
     * MAIN ADDING CONTRACT VIEW
     *
     */
    @RequestMapping(value = "/contactAdditions", method = RequestMethod.GET)
    public String addAdditions(Model model, @RequestParam(value="contract", required = false)Contract contractId,  @RequestParam(value="carId", required = false)Integer orderCarContract, @RequestParam(value="cannotAddPromotion", required = false)Integer cannotAddPromotion){

        model.addAttribute("invisibleCarList", 1);
        model.addAttribute("invisibleAccessoryList", 1);
        model.addAttribute("invisiblePromotionList", 1);

        if(cannotAddPromotion != null)
            model.addAttribute("cannotAddPromotion",1);
        else
            model.addAttribute("cannotAddPromotion",0);

        if(contractId!=null)
            model.addAttribute("contract", contractId);

        //ZAMOWIENIA, powrot do sprzedazy z id nowego samochodu
        if(orderCarContract != null) {
            carList.addAll(contract.getCarList());
            accessoryList.addAll(contract.getAccessoryList());
            promotionList.addAll(contract.getPromotions());
            viewMode = ViewMode.INSERT;
        }

        model.addAttribute("contracts", contractsController.findContracts());
        model.addAttribute("contractors",contractorsController.findAllContractors());

        if(viewMode == ViewMode.EDIT || viewMode == ViewMode.VIEW_ALL){
            carList = contract.getCarList();
            accessoryList = contract.getAccessoryList();
            promotionList = contract.getPromotions();
            model.addAttribute("contractor",contract.getContractor());
        }

        if(viewMode == ViewMode.VIEW_ALL || viewMode == ViewMode.VIEW_AFTER_INSERT){
            model.addAttribute("disabledButtons", 1);
            if(viewMode == ViewMode.VIEW_ALL)
                model.addAttribute("disabledConfirmButton", 1);
        }
         else {
            model.addAttribute("disabledButtons", 0);
            model.addAttribute("disabledConfirmButton", 0);
        }

        model.addAttribute("carView",1);

        Set<Car>carsAvaliable = carsController.findCarAvaliable(carList);
        System.out.println(carsAvaliable);

        if(carsAvaliable.size() != 0)
            model.addAttribute("cars",carsAvaliable);
        else
            model.addAttribute("invisibleCarList", 0);
        if(orderCarContract != null)
            model.addAttribute("invisibleCarList", 1);

        model.addAttribute("choosenCar", carList);
        model.addAttribute("choosenAccessories", accessoryList);
        model.addAttribute("choosenPromotion", promotionList);

        Set<Accessory>accessoriesAvaliable = accessoriesController.findAccessoriesAvaliable(accessoryList);
        if(accessoriesAvaliable.size() != 0)
            model.addAttribute("accessories", accessoriesAvaliable);
        else
            model.addAttribute("invisibleAccessoryList", 0);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String stringDate= dateFormat.format(new Date());
        java.sql.Date sqlDate=  java.sql.Date.valueOf(stringDate);

        List<Promotion>promotionAvaliable = promotionsController.findActual(sqlDate, promotionList);
        if(promotionAvaliable.size() != 0)
            model.addAttribute("promotions",promotionAvaliable);
        else
            model.addAttribute("invisiblePromotionList", 0);

        return "contracts";
    }

    /**
     * CONTRACT OPERATION
     *
     */
    @RequestMapping(value ="/deleteContract/{id}")
    public String deleteContract(@PathVariable("id")int id){
        contractsController.delete(id);
        return "redirect:/contracts/";
    }

    @RequestMapping(value ="/viewContract/{id}")
    public String displayContract(RedirectAttributes redirectAttributes, @PathVariable("id")int id){
        viewMode = ViewMode.VIEW_ALL;
        this.contract = contractsController.findOne(id);
        return "redirect:/contactAdditions/";
    }

    @RequestMapping(value ="/editContract/{id}")
    public String editContract(RedirectAttributes redirectAttributes, @PathVariable("id")int id){
        viewMode = ViewMode.EDIT;
        this.contract = contractsController.findOne(id);
        return "redirect:/contactAdditions/";
    }

    @RequestMapping(value ="/addContract")
    public String addContract(){
        viewMode = ViewMode.INSERT;
        carList.clear();
        accessoryList.clear();
        promotionList.clear();
        contract = null;
        return "redirect:/contactAdditions/";
    }

    /**
     * REMOVE CHOOSEN ADDITIONS
     *
     */
    @RequestMapping(value ="/deleteChoosenCar/{id}")
    public String deleteCarFromList(@PathVariable("id")int id){
        carList.remove(carList.stream().filter(x-> (x.getId() == id)).findAny().get());
        return "redirect:/contactAdditions/";
    }

    @RequestMapping(value ="/deleteChoosenAccessories/{id}")
    public String deleteAccessory(@PathVariable("id")int id){
        accessoryList.remove(accessoryList.stream().filter(x-> (x.getId() == id)).findAny().get());
        return "redirect:/contactAdditions/";
    }

    @RequestMapping(value ="/deleteChoosenPromotion/{id}")
    public String deletePromotion(@PathVariable("id")int id){
        promotionList.remove(promotionList.stream().filter(x-> (x.getId() == id)).findAny().get());
        return "redirect:/contactAdditions/";
    }

    /**
     * CHOOSE ADDITIONS
     *
     */
    @RequestMapping(value ="/addCar", method = RequestMethod.POST)
    public String newCar(RedirectAttributes redirectAttributes, @RequestParam("car") int car) {
        carList.add(carsController.findOne(car));
        return "redirect:/contactAdditions";
    }
    @RequestMapping(value ="/orderCar", method = RequestMethod.POST)
    public String orderCar(RedirectAttributes redirectAttributes) {
        this.contract = contractsController.makeTemporaryContract(carList, promotionList, accessoryList);
        redirectAttributes.addAttribute("contract",1);
        return "redirect:/addNewCar";
    }

    @RequestMapping(value ="/addPromotion", method = RequestMethod.POST)
    public String newPromotion(RedirectAttributes redirectAttributes, @RequestParam("promotion") int promotion) {
        Promotion prom = promotionsController.findOne(promotion);
        if(accessoryList.size() == 0 && carList.size() == 0)
            redirectAttributes.addAttribute("cannotAddPromotion", 1);
        else{
            promotionList.add(prom);
        }
        return "redirect:/contactAdditions";
    }

    @RequestMapping(value ="/addAccessory", method = RequestMethod.POST)
    public String newAccesory(RedirectAttributes redirectAttributes, @RequestParam("accessory") int accessory) {
        Accessory part = accessoriesController.findOne(accessory);
        accessoryList.add(part);
        return "redirect:/contactAdditions";
    }

}
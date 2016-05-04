package pl.polsl.web;

import com.sun.deploy.util.OrderedHashSet;
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
import pl.polsl.model.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    private Contract contract;
    private ViewMode viewMode;

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
                model.addAttribute("paymentDeadline", invoice.getPaymentDeadline().toString());
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
    public String newInvoice(RedirectAttributes redirectAttributes, @RequestParam("contract_id") int contractId, @RequestParam("paymentForm") int paymentFormId, @RequestParam("invoiceType") int invoiceTypeId, @RequestParam(value="date", required = false)String date) {
        Invoice invoice = invoiceController.addNew(contractsController.findOne(contractId),paymentFormId, invoiceTypeId);
        contractsController.findOne(contractId).setInvoice(invoice);
        if(date != "") {
           System.out.println(date);
           SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yyyy");
           try {
               Date d = sdf.parse(date);
               invoice.setPaymentDeadline(d);
           } catch (ParseException e) {
               e.printStackTrace();
           }
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
//        carList.clear();
//        accessoryList.clear();
//        promotionList.clear();
        model.addAttribute("contracts", contractsController.findAllContracts());
        model.addAttribute("carView",0);
//        model.addAttribute("disabledButtons", 0);
//        model.addAttribute("endingOperation",0);
        return "contracts";
    }

    /**
     * MAIN ADDING CONTRACT VIEW
     *
     */
    @RequestMapping(value = "/contactAdditions", method = RequestMethod.GET)
    public String addAdditions(Model model, @RequestParam(value="contract", required = false)Contract contractId){

        if(contractId!=null)
            model.addAttribute("contract", contractId);


        model.addAttribute("contracts", contractsController.findAllContracts());
        model.addAttribute("contractors",contractorsController.findAllContractors());

        if(viewMode == ViewMode.EDIT || viewMode == ViewMode.VIEW_ALL){
            carList = contract.getCarList();
            accessoryList = contract.getAccessoryList();
            promotionList = contract.getPromotions();
            model.addAttribute("contractor",contract.getContractor());

            if(viewMode == ViewMode.VIEW_ALL)
                model.addAttribute("disabledButtons", 1);
        }
        else
            model.addAttribute("disabledButtons", 0);

        model.addAttribute("carView",1);

        Set<Car>carsAvaliable = carsController.findCarAvaliable(carList);
        if( carsAvaliable!=null)
            model.addAttribute("cars",carsAvaliable);

        model.addAttribute("choosenCar", carList);
        model.addAttribute("choosenAccessories", accessoryList);
        model.addAttribute("choosenPromotion", promotionList);

        Set<Accessory>accessoriesAvaliable = accessoriesController.findAccessoriesAvaliable(accessoryList);
        if(accessoriesAvaliable != null)
            model.addAttribute("accessories", accessoriesAvaliable);

        List<Promotion>promotionAvaliable = promotionsController.findActual(new Date(), promotionList);
        if(promotionAvaliable.size() != 0) {
            model.addAttribute("promotions",promotionAvaliable);

        }
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
        System.out.println("Wchodzi do widoku");
//        redirectAttributes.addAttribute("contract",contractsController.findOne(id));
        return "redirect:/contactAdditions/";
    }

    @RequestMapping(value ="/editContract/{id}")
    public String editContract(RedirectAttributes redirectAttributes, @PathVariable("id")int id){
        viewMode = ViewMode.EDIT;
        this.contract = contractsController.findOne(id);
//        redirectAttributes.addAttribute("contract",contractsController.findOne(id));
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

    @RequestMapping(value ="/addPromotion", method = RequestMethod.POST)
    public String newPromotion(RedirectAttributes redirectAttributes, @RequestParam("promotion") int promotion) {
        Promotion prom = promotionsController.findOne(promotion);
        promotionList.add(prom);
        return "redirect:/contactAdditions";
    }

    @RequestMapping(value ="/addAccessory", method = RequestMethod.POST)
    public String newAccesory(RedirectAttributes redirectAttributes, @RequestParam("accessory") int accessory) {
        Accessory part = accessoriesController.findOne(accessory);
        accessoryList.add(part);
        return "redirect:/contactAdditions";
    }

}

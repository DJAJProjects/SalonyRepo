package pl.polsl.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.View;
import pl.polsl.Data;
import pl.polsl.ViewMode;
import pl.polsl.controller.ContractorsController;
import pl.polsl.controller.DictionaryController;
import pl.polsl.controller.PrivilegesController;
import pl.polsl.model.Contractor;

import java.util.List;

import java.util.List;

/**
 * Contractors web controller class
 * @author Jakub Wieczorek
 * @version 1.0
 */
@Controller
public class ContractorsWebController extends BaseWebController {


    @Autowired
    private ContractorsController contractorsController;
    @Autowired
    private DictionaryController dictionaryController;

    /**
     * GET method for return contractors view
     * @param model actual web model
     * @return contractors view
     */
    @RequestMapping(value ="/contractors")
    public String getContractors(Model model){

        analisePrivileges("Klienci");

        if(viewMode == ViewMode.DEFAULT){
            model.addAttribute("controlsPanelVisible", false);
        }
        else{
            if(model.containsAttribute("contractor")) {
                model.addAttribute("controlsPanelVisible", true);
            }
            else{
                viewMode = ViewMode.DEFAULT;
                model.addAttribute("controlsPanelVisible", false);
            }
        }

        model.addAttribute("contractors", contractorsController.findContractorsRelatedToWorker(Data.user));
        model.addAttribute("insertEnabled", insertEnabled);
        model.addAttribute("updateEnabled", updateEnabled);
        model.addAttribute("deleteEnabled", deleteEnabled);
        refreshMenuPrivileges(model);
        model.addAttribute("classActiveContractors","active");
        return "contractors";
    }

    /**
     * Method to display view about contractors details
     * @param redirectAttributes
     * @param id id contractors to display
     * @return redirect to getContractors method
     */
    @RequestMapping(value ="/viewContractor/{id}")
    public String viewContractor(RedirectAttributes redirectAttributes, @PathVariable("id")int id) {

        viewMode = ViewMode.VIEW_ALL;

        Contractor contractor = contractorsController.findOne(id);
        redirectAttributes.addFlashAttribute("cityId", contractor.getCity().getId());
        redirectAttributes.addFlashAttribute("countryId", contractor.getCountry().getId());

        redirectAttributes.addFlashAttribute("contractor", contractor);
        redirectAttributes.addFlashAttribute("controlsDisabled", true);
        redirectAttributes.addFlashAttribute("cities", dictionaryController.findAllCities());
        redirectAttributes.addFlashAttribute("countries", dictionaryController.findAllCountries());

        return "redirect:/contractors/";
    }

    /**
     * Method to display add contractors view
     * @param redirectAttributes
     * @return redirect to getContractors method
     */
    @RequestMapping(value ="/addContractor")
    public String addContractor(RedirectAttributes redirectAttributes) {

        viewMode = ViewMode.INSERT;

        Contractor contractor = new Contractor();

        redirectAttributes.addFlashAttribute("contractor", contractor);
        redirectAttributes.addFlashAttribute("controlsDisabled", false);
        redirectAttributes.addFlashAttribute("cities", dictionaryController.findAllCities());
        redirectAttributes.addFlashAttribute("countries", dictionaryController.findAllCountries());

        return "redirect:/contractors/";
    }

    /**
     * Method to display edit contractors view
     * @param redirectAttributes
     * @param id contractors id to edit
     * @return redirect to getContractors method
     */
    @RequestMapping(value ="/editContractor/{id}")
    public String editContractor(RedirectAttributes redirectAttributes, @PathVariable("id")int id) {

        viewMode = ViewMode.EDIT;

        Contractor contractor = contractorsController.findOne(id);
        redirectAttributes.addFlashAttribute("cityId", contractor.getCity().getId());
        redirectAttributes.addFlashAttribute("countryId", contractor.getCountry().getId());

        redirectAttributes.addFlashAttribute("contractor", contractor);
        redirectAttributes.addFlashAttribute("controlsDisabled", false);
        redirectAttributes.addFlashAttribute("cities", dictionaryController.findAllCities());
        redirectAttributes.addFlashAttribute("countries", dictionaryController.findAllCountries());

        return "redirect:/contractors/";
    }

    /**
     * Method to save contractor to database
     * @param redirectAttributes contractor redirectAttributes
     * @param id contractor id
     * @param name contractor name
     * @param surname contractor surname
     * @param pesel contractor pesel
     * @param nip contractor nip
     * @param regon contractor regon
     * @param city contractor city
     * @param country contractor country
     * @param street contractor street
     * @return
     */
    @RequestMapping(value ="/acceptModifyContractor", method = RequestMethod.POST)
    public String editContractor(   RedirectAttributes redirectAttributes,
                                    @RequestParam("id") int id,
                                    @RequestParam("name") String name,
                                    @RequestParam(value = "surname") String surname,
                                    @RequestParam(value = "pesel") String pesel,
                                    @RequestParam(value = "nip") String nip,
                                    @RequestParam(value = "regon") String regon,
                                    @RequestParam(value = "city") int city,
                                    @RequestParam(value = "country") int country,
                                    @RequestParam(value = "street") String street){

        if(viewMode == ViewMode.INSERT){
            if (surname == null || surname.equals("")) {
                redirectAttributes.addFlashAttribute("error","Pole z nazwiskiem nie może być puste");
                return "redirect:/addContractor/";
            }
            Contractor contractor = contractorsController.addContractor(name,surname,pesel,nip,
                                                                        regon,city,country, street);
        }
        else if(viewMode == ViewMode.EDIT){
            Contractor contractor = contractorsController.updateContractor( id,name,surname,pesel,nip,
                                                                        regon,city,country,street);
        }
        viewMode = ViewMode.DEFAULT;
        return "redirect:/contractors/";
    }

    /**
     * Method to delete contractor
     * @param id contractor id to remove
     * @return redirect to getContractor method
     */
    @RequestMapping(value = "/deleteContractor/{id}")
    public String deleteContractor(@PathVariable("id")int id) {
        contractorsController.deleteContractor(id);
        return "redirect:/contractors";
    }

    /**
     * Method to cancel contractor edit or add
     * @return redirect to getContractor method
     */
    @RequestMapping(value="/resetContractorsChange")
    public  String resetChange(){
        viewMode = ViewMode.DEFAULT;
        return "redirect:/contractors";
    }




}

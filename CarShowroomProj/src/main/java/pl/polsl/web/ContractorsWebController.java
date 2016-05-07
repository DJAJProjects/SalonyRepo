package pl.polsl.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.polsl.ViewMode;
import pl.polsl.controller.ContractorsController;
import pl.polsl.controller.DictionaryController;
import pl.polsl.model.Contractor;

/**
 * Created by Kuba on 08.04.2016.
 */
@Controller
public class ContractorsWebController {

    private ViewMode viewMode;

    @Autowired
    private ContractorsController contractorsController;
    @Autowired
    private DictionaryController dictionaryController;

    @RequestMapping(value ="/contractors")
    public String getContractors(Model model){

        viewMode = ViewMode.DEFAULT;

        model.addAttribute("contractors", contractorsController.findAllContractors());
        model.addAttribute("controlsPanelVisible", false);
        return "contractors";
    }

    @RequestMapping(value ="/viewContractor/{id}")
    public String viewContractor(Model model, @PathVariable("id")int id) {

        viewMode = ViewMode.VIEW_ALL;

        Contractor contractor = contractorsController.findOne(id);
        model.addAttribute("cityId", contractor.getCity().getId());
        model.addAttribute("countryId", contractor.getCountry().getId());

        model.addAttribute("contractor", contractor);
        model.addAttribute("controlsPanelVisible", true);
        model.addAttribute("controlsDisabled", true);
        model.addAttribute("cities", dictionaryController.findAllCities());
        model.addAttribute("countries", dictionaryController.findAllCountries());
        model.addAttribute("contractors", contractorsController.findAllContractors());

        return "contractors";
    }

    @RequestMapping(value ="/addContractor")
    public String addContractor(Model model) {

        viewMode = ViewMode.INSERT;

        Contractor contractor = new Contractor();

        model.addAttribute("contractor", contractor);
        model.addAttribute("controlsPanelVisible", true);
        model.addAttribute("controlsDisabled", false);
        model.addAttribute("cities", dictionaryController.findAllCities());
        model.addAttribute("countries", dictionaryController.findAllCountries());
        model.addAttribute("contractors", contractorsController.findAllContractors());

        return "contractors";
    }

    @RequestMapping(value ="/editContractor/{id}")
    public String editContractor(Model model, @PathVariable("id")int id) {

        viewMode = ViewMode.EDIT;

        Contractor contractor = contractorsController.findOne(id);
        model.addAttribute("cityId", contractor.getCity().getId());
        model.addAttribute("countryId", contractor.getCountry().getId());

        model.addAttribute("contractor", contractor);
        model.addAttribute("controlsPanelVisible", true);
        model.addAttribute("controlsDisabled", false);
        model.addAttribute("cities", dictionaryController.findAllCities());
        model.addAttribute("countries", dictionaryController.findAllCountries());
        model.addAttribute("contractors", contractorsController.findAllContractors());

        return "contractors";
    }

    @RequestMapping(value ="/acceptModifyContractor", method = RequestMethod.POST)
    public String editContractor(   @RequestParam("id") int id,
                                    @RequestParam("name") String name,
                                    @RequestParam(value = "surname") String surname,
                                    @RequestParam(value = "pesel") String pesel,
                                    @RequestParam(value = "nip") String nip,
                                    @RequestParam(value = "regon") String regon,
                                    @RequestParam(value = "city") int city,
                                    @RequestParam(value = "country") int country,
                                    @RequestParam(value = "street") String street){

        if(viewMode == ViewMode.INSERT){
            Contractor contractor = contractorsController.addContractor(name,surname,pesel,nip,
                                                                        regon,city,country, street);
        }
        else if(viewMode == ViewMode.EDIT){
            Contractor contractor = contractorsController.updateContractor( id,name,surname,pesel,nip,
                                                                        regon,city,country,street);
        }
        return "redirect:/contractors/";
    }

    @RequestMapping(value = "/deleteContractor/{id}")
    public String deleteContractor(@PathVariable("id")int id) {
        contractorsController.deleteContractor(id);
        return "redirect:/contractors";
    }
}

package pl.polsl.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.polsl.controller.ContractorsController;
import pl.polsl.controller.DictionaryController;
import pl.polsl.controller.ShowroomsController;
import pl.polsl.controller.WorkersController;
import pl.polsl.model.Contractor;
import pl.polsl.model.Showroom;

/**
 * Created by Kuba on 08.04.2016.
 */
@Controller
public class ContractorsWebController {

    private boolean addVisible = false;

    @Autowired
    private ContractorsController contractorsController;
    @Autowired
    private DictionaryController dictionaryController;

    @RequestMapping(value ="/contractors")
    public String getContractors(Model model){
        model.addAttribute("contractors", contractorsController.findAllContractors());
        return "contractors";
    }

    @RequestMapping(value ="/addContractor")
    public String addContractor(Model model){
        addVisible = true;
        model.addAttribute("addVisible", addVisible);
        model.addAttribute("cities", dictionaryController.findAllCities());
        model.addAttribute("countries", dictionaryController.findAllCountries());
        model.addAttribute("contractors", contractorsController.findAllContractors());
        return "contractors";
    }

    @RequestMapping(value ="/addContractor", method = RequestMethod.POST)
    public String addContractor(@RequestParam("name") String name,
                                @RequestParam(value = "surname") String surname,
                                @RequestParam(value = "pesel") String pesel,
                                @RequestParam(value = "nip") String nip,
                                @RequestParam(value = "regon") String regon,
                                @RequestParam(value = "city") int city,
                                @RequestParam(value = "country") int country,
                                @RequestParam(value = "street") String street){
        Contractor contractor = contractorsController.addContractor(name, surname, pesel, nip, regon, city, country, street);
        addVisible = false;
        return "redirect:/contractors/";
    }

}

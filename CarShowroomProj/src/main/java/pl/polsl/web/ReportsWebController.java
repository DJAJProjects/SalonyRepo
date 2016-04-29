package pl.polsl.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.polsl.controller.*;
import pl.polsl.model.Contractor;

/**
 * Created by Kuba on 20.04.2016.
 */
@Controller
public class ReportsWebController {

    private boolean addVisible = false;

    @Autowired
    private ReportsController reportsController;

    @Autowired
    private DictionaryController dictionaryController;

    @Autowired
    private ShowroomsController showroomController;

    @Autowired
    private WorkersController workersController;

    @Autowired
    private ContractorsController contractorsController;

    @RequestMapping(value ="/reports")
    public String getReports(Model model){
        model.addAttribute("reports", reportsController.findAllRaports());
        return "reports";
    }

    @RequestMapping(value ="/addReport")
    public String addReports(Model model){
        addVisible = true;
        model.addAttribute("addVisible", addVisible);
        model.addAttribute("cities", dictionaryController.findAllCities());
        model.addAttribute("countries", dictionaryController.findAllCountries());
        model.addAttribute("contractors", contractorsController.findAllContractors());
        return "reports";
    }

    @RequestMapping(value ="/addReport", method = RequestMethod.POST)
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

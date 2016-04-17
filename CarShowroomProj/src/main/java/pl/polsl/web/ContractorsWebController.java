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
import pl.polsl.model.Showroom;

/**
 * Created by Kuba on 08.04.2016.
 */
@Controller
public class ContractorsWebController {


    @Autowired
    private ContractorsController contractorsController;
    @Autowired
    private DictionaryController dictionaryController;


    @RequestMapping(value ="/contractors")
    public String getShowrooms(Model model){
        model.addAttribute("contractors", contractorsController.findAllContractors());
        return "contractors";
    }


}

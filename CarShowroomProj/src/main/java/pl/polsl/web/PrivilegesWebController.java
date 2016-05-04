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
import pl.polsl.controller.PrivilegesController;
import pl.polsl.model.Contractor;
import pl.polsl.model.Privileges;

/**
 * Created by Kuba on 08.04.2016.
 */
@Controller
public class PrivilegesWebController {

    private ViewMode viewMode;

    @Autowired
    private PrivilegesController privilegesController;

    @RequestMapping(value ="/privileges")
    public String getPrivileges(Model model){

        viewMode = ViewMode.DEFAULT;

        model.addAttribute("privilegesGroups", privilegesController.findAll());
        model.addAttribute("controlsPanelVisible", false);
        return "privileges";
    }

    @RequestMapping(value ="/viewPrivileges/{id}")
    public String viewPrivileges(Model model, @PathVariable("id")int id) {

        viewMode = ViewMode.VIEW_ALL;

        Privileges privileges = privilegesController.findOne(id);

        model.addAttribute("privilegesGroups", privilegesController.findAll());
        model.addAttribute("privilegesGroup", privileges);
        model.addAttribute("controlsPanelVisible", true);
        model.addAttribute("controlsDisabled", true);
        return "privileges";
    }

    @RequestMapping(value ="/addPrivileges")
    public String addPrivileges(Model model) {

        viewMode = ViewMode.INSERT;

        Privileges privileges =  new Privileges();

        model.addAttribute("privilegesGroups", privilegesController.findAll());
        model.addAttribute("privileges", privileges);
        model.addAttribute("controlsPanelVisible", true);
        model.addAttribute("controlsDisabled", false);

        return "privileges";
    }

    @RequestMapping(value ="/editPrivileges/{id}")
    public String editPrivileges(Model model, @PathVariable("id")int id) {

        viewMode = ViewMode.EDIT;

        Privileges privileges = privilegesController.findOne(id);

        model.addAttribute("privilegesGroups", privilegesController.findAll());
        model.addAttribute("privilegesGroup", privileges);
        model.addAttribute("controlsPanelVisible", true);
        model.addAttribute("controlsDisabled", false);
        return "privileges";
    }

    @RequestMapping(value ="/acceptModifyPrivileges", method = RequestMethod.POST)
    public String editContractor(   @RequestParam("id") int id,
                                    @RequestParam("name") String name,
                                    @RequestParam(value = "surname") String surname,
                                    @RequestParam(value = "pesel") String pesel,
                                    @RequestParam(value = "nip") String nip,
                                    @RequestParam(value = "regon") String regon,
                                    @RequestParam(value = "city") int city,
                                    @RequestParam(value = "country") int country,
                                    @RequestParam(value = "street") String street){

       /* if(viewMode == ViewMode.INSERT){
            Contractor contractor = contractorsController.addContractor(name,surname,pesel,nip,
                                                                        regon,city,country, street);
        }
        else if(viewMode == ViewMode.EDIT){
            Contractor contractor = contractorsController.updateShowroom( id,name,surname,pesel,nip,
                                                                        regon,city,country,street);
        }*/
        return "redirect:/privileges/";
    }
}

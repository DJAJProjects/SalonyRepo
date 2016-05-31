package pl.polsl.web;

import org.apache.log4j.ConsoleAppender;
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

import java.io.Console;

/**
 * Created by Kuba on 08.04.2016.
 */
@Controller
public class PrivilegesWebController extends BaseWebController{

    private ViewMode viewMode;

    @Autowired
    private PrivilegesController privilegesController;
    @Autowired
    private DictionaryController dictionaryController;

    @RequestMapping(value ="/privileges")
    public String getPrivileges(Model model){

        viewMode = ViewMode.DEFAULT;

        model.addAttribute("privilegesGroups", privilegesController.findAll());
        model.addAttribute("controlsPanelVisible", false);
        refreshMenuPrivileges(model);
        return "privileges";
    }

    @RequestMapping(value ="/viewPrivileges/{id}")
    public String viewPrivileges(Model model, @PathVariable("id")int id) {

        viewMode = ViewMode.VIEW_ALL;

        Privileges privileges = privilegesController.findOne(id);

        model.addAttribute("privilegesGroups", privilegesController.findAll());
        model.addAttribute("privilegesGroup", privileges);
        model.addAttribute("modules", dictionaryController.findAllModules());
        model.addAttribute("controlsPanelVisible", true);
        model.addAttribute("controlsDisabled", true);
        return "privileges";
    }

    @RequestMapping(value ="/addPrivileges")
    public String addPrivileges(Model model) {

        viewMode = ViewMode.INSERT;

        Privileges privileges =  new Privileges();

        model.addAttribute("privilegesGroups", privilegesController.findAll());
        model.addAttribute("modules", dictionaryController.findAllModules());
        model.addAttribute("privilegesGroup", privileges);
        model.addAttribute("controlsPanelVisible", true);
        model.addAttribute("controlsDisabled", false);

        return "privileges";
    }

    @RequestMapping(value ="/editPrivileges/{id}")
    public String editPrivileges(Model model, @PathVariable("id")int id) {

        viewMode = ViewMode.EDIT;

        Privileges privileges = privilegesController.findOne(id);

        model.addAttribute("privilegesGroups", privilegesController.findAll());
        model.addAttribute("modules", dictionaryController.findAllModules());
        model.addAttribute("privilegesGroup", privileges);
        model.addAttribute("controlsPanelVisible", true);
        model.addAttribute("controlsDisabled", false);
        return "privileges";
    }

    @RequestMapping(value ="/acceptModifyPrivileges", method = RequestMethod.POST)
    public String acceptModifyContractor(@RequestParam("id") int id,
                                         @RequestParam(value = "name") String name,
                                         @RequestParam(value = "module") int module,
                                         @RequestParam(value = "read") boolean read,
                                         @RequestParam(value = "insert") boolean insert,
                                         @RequestParam(value = "update") boolean update,
                                         @RequestParam(value = "delete") boolean delete){

        //String name  = privileges.getName();

        if(viewMode == ViewMode.INSERT){
            Privileges privileges = privilegesController.addPrivilege(name,module,read,
                    insert,update,delete);
        }
        else if(viewMode == ViewMode.EDIT){
            Privileges privileges = privilegesController.updatePrivilege( id,name,module,read,
                    insert,update,delete);
        }
        return "redirect:/privileges/";
    }
}

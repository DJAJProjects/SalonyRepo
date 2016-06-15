package pl.polsl.web;

import org.apache.log4j.ConsoleAppender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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

    @Autowired
    private PrivilegesController privilegesController;
    @Autowired
    private DictionaryController dictionaryController;

    @RequestMapping(value ="/privileges")
    public String getPrivileges(Model model){


        if(viewMode == ViewMode.DEFAULT){
            model.addAttribute("controlsPanelVisible", false);
        }
        else{
            if(model.containsAttribute("privilegesGroup")) {
                model.addAttribute("controlsPanelVisible", true);
            }
            else{
                viewMode = ViewMode.DEFAULT;
                model.addAttribute("controlsPanelVisible", false);
            }
        }

        model.addAttribute("privilegesGroups", privilegesController.findAll());

        refreshMenuPrivileges(model);
        return "privileges";
    }

    @RequestMapping(value ="/viewPrivileges/{id}")
    public String viewPrivileges(RedirectAttributes redirectAttributes, @PathVariable("id")int id) {

        viewMode = ViewMode.VIEW_ALL;

        Privileges privileges = privilegesController.findOne(id);

        redirectAttributes.addFlashAttribute("privilegesGroup", privileges);
        redirectAttributes.addFlashAttribute("modules", dictionaryController.findAllModules());
        redirectAttributes.addFlashAttribute("controlsDisabled", true);
        return "redirect:/privileges/";
    }

    @RequestMapping(value ="/addPrivileges")
    public String addPrivileges(RedirectAttributes redirectAttributes) {

        viewMode = ViewMode.INSERT;

        Privileges privileges  = new Privileges();

        redirectAttributes.addFlashAttribute("privilegesGroup", privileges);
        redirectAttributes.addFlashAttribute("modules", dictionaryController.findAllModules());
        redirectAttributes.addFlashAttribute("controlsDisabled", false);

        return "redirect:/privileges/";
    }

    @RequestMapping(value ="/editPrivileges/{id}")
    public String editPrivileges(RedirectAttributes redirectAttributes, @PathVariable("id")int id) {

        viewMode = ViewMode.EDIT;

        Privileges privileges = privilegesController.findOne(id);

        redirectAttributes.addFlashAttribute("privilegesGroup", privileges);

        redirectAttributes.addFlashAttribute("modules", dictionaryController.findAllModules());
        redirectAttributes.addFlashAttribute("controlsDisabled", false);
        return "redirect:/privileges/";
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

        viewMode = ViewMode.DEFAULT;

        return "redirect:/privileges/";
    }
}

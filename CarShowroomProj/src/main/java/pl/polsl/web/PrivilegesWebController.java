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
 * Privileges web controller class
 * @author Jakub Wieczorek
 * @version 1.0
 */
@Controller
public class PrivilegesWebController extends BaseWebController{

    @Autowired
    private PrivilegesController privilegesController;
    @Autowired
    private DictionaryController dictionaryController;

    /**
     * GET method for return privileges view
     * @param model actual web model
     * @return privileges view
     */
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
        model.addAttribute("classActivePrivileges","active");
        refreshMenuPrivileges(model);
        return "privileges";
    }


    /**
     * Method to display view about privileges details
     * @param redirectAttributes
     * @param id id privileges to display
     * @return redirect to getPrivileges method
     */
    @RequestMapping(value ="/viewPrivileges/{id}")
    public String viewPrivileges(RedirectAttributes redirectAttributes, @PathVariable("id")int id) {

        viewMode = ViewMode.VIEW_ALL;

        Privileges privileges = privilegesController.findOne(id);

        redirectAttributes.addFlashAttribute("privilegesGroup", privileges);
        redirectAttributes.addFlashAttribute("modules", dictionaryController.findAllModules());
        redirectAttributes.addFlashAttribute("controlsDisabled", true);
        return "redirect:/privileges/";
    }

    /**
     * Method to display add privileges view
     * @param redirectAttributes
     * @return redirect to getPrivileges method
     */
    @RequestMapping(value ="/addPrivileges")
    public String addPrivileges(RedirectAttributes redirectAttributes) {

        viewMode = ViewMode.INSERT;

        Privileges privileges  = new Privileges();

        redirectAttributes.addFlashAttribute("privilegesGroup", privileges);
        redirectAttributes.addFlashAttribute("modules", dictionaryController.findAllModules());
        redirectAttributes.addFlashAttribute("controlsDisabled", false);

        return "redirect:/privileges/";
    }

    /**
     * Method to display edit privileges view
     * @param redirectAttributes
     * @param id privileges id to edit
     * @return redirect to getPrivileges method
     */
    @RequestMapping(value ="/editPrivileges/{id}")
    public String editPrivileges(RedirectAttributes redirectAttributes, @PathVariable("id")int id) {

        viewMode = ViewMode.EDIT;

        Privileges privileges = privilegesController.findOne(id);

        redirectAttributes.addFlashAttribute("privilegesGroup", privileges);

        redirectAttributes.addFlashAttribute("modules", dictionaryController.findAllModules());
        redirectAttributes.addFlashAttribute("controlsDisabled", false);
        return "redirect:/privileges/";
    }

    /**
     * Method to delete privilege
     * @param id privilege id to remove
     * @return redirect to getPrivileges method
     */
    @RequestMapping(value = "/deletePrivileges/{id}")
    public String deleteReport(@PathVariable("id")int id) {
        privilegesController.deletePrivilege(id);
        return "redirect:/privileges/";
    }


    /**
     * Method to save privilege to database
     * @param id privilege id
     * @param name privilege name
     * @param module privilege module
     * @param read privilege read
     * @param insert privilege insert
     * @param update privilege update
     * @param delete privilege delete
     * @return
     */
    @RequestMapping(value ="/acceptModifyPrivileges", method = RequestMethod.POST)
    public String acceptModifyContractor(@RequestParam("id") int id,
                                         @RequestParam(value = "name") String name,
                                         @RequestParam(value = "module") int module,
                                         @RequestParam(value = "readPriv", required = false) boolean read,
                                         @RequestParam(value = "insertPriv", required = false) boolean insert,
                                         @RequestParam(value = "updatePriv",required = false) boolean update,
                                         @RequestParam(value = "deletePriv", required = false) boolean delete){

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

    /**
     * Method to cancel privilege edit or add
     * @return redirect to getPrivileges method
     */
    @RequestMapping(value="/resetPrivilegesChange")
    public  String resetChange(){
        viewMode = ViewMode.DEFAULT;
        return "redirect:/privileges";
    }
}

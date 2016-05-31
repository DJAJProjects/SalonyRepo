package pl.polsl.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.polsl.ViewMode;
import pl.polsl.controller.AccessoriesController;
import pl.polsl.controller.DictionaryController;
import pl.polsl.model.Accessory;

/**
 * Created by Julia on 2016-04-28.
 */
@Controller
public class AccessoriesWebController extends  BaseWebController {

    @Autowired
    AccessoriesController accessoriesController;
    @Autowired
    DictionaryController dictionaryController;

    private ViewMode viewMode;

    @RequestMapping(value = "/accessories")
    public String getAccessories(Model model) {
        viewMode = ViewMode.DEFAULT;
        model.addAttribute("accessories",accessoriesController.findAll());
        model.addAttribute("controlsPanelVisible", false);
        refreshMenuPrivileges(model);
        return "accessories";
    }

    @RequestMapping(value ="/addNewAccessory")
    public String addNewAccessory(Model model){
        Accessory accessory = new Accessory();
        viewMode = ViewMode.INSERT;
        model.addAttribute("accessory",accessory);
        model.addAttribute("accessories",accessoriesController.findAll());
        model.addAttribute("accessoryNames", dictionaryController.findAllAccessories());
        model.addAttribute("controlsPanelVisible", true);
        model.addAttribute("controlsDisabled", false);
        return "accessories";
    }

    @RequestMapping(value = "/deleteAccessory/{id}")
    public String deleteAccessory(@PathVariable("id")int id) {
        accessoriesController.deleteAccessory(id);
        return "redirect:/accessories";
    }

    @RequestMapping(value = "/editAccessory/{id}")
    public String editAccessory(Model model, @PathVariable("id")int id) {
        viewMode = ViewMode.EDIT;
        model.addAttribute("accessories",accessoriesController.findAll());
        Accessory accessory = accessoriesController.findOne(id);
        model.addAttribute("accessory",accessory);
        model.addAttribute("accessoryNames", dictionaryController.findAllAccessories());
        model.addAttribute("accessoryNameId",accessory.getAccessory().getId());
        model.addAttribute("contractId", accessory.getContract().getId());
        model.addAttribute("cost", accessory.getCost());
        model.addAttribute("controlsPanelVisible", true);
        model.addAttribute("controlsDisabled", false);
        return "accessories";
    }

    @RequestMapping(value = "/viewAccessory/{id}")
    public String viewAccessory(Model model, @PathVariable("id")int id) {
        viewMode = ViewMode.VIEW_ALL;
        model.addAttribute("accessories",accessoriesController.findAll());
        Accessory accessory = accessoriesController.findOne(id);
        model.addAttribute("accessory",accessory);
        model.addAttribute("accessoryNames", dictionaryController.findAllAccessories());
        model.addAttribute("accessoryNameId",accessory.getAccessory().getId());
        model.addAttribute("contractId", accessory.getContract().getId());
        model.addAttribute("cost", accessory.getCost());
        model.addAttribute("controlsPanelVisible", true);
        model.addAttribute("controlsDisabled", true);
        return "accessories";
    }

    @RequestMapping(value = "/modifyAccessory", method = RequestMethod.POST)
    public String modifyAccessory(@RequestParam("id")int id,@RequestParam("name")int name,@RequestParam("cost")int cost){

        if(viewMode == ViewMode.INSERT){
            Accessory accessory = accessoriesController.addAccessory(name,cost);
        } else if(viewMode == ViewMode.EDIT){
            Accessory accessory = accessoriesController.editAccessory(id,name,cost);
        }
        return "redirect:/accessories";
    }

}

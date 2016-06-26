package pl.polsl.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.polsl.Data;
import pl.polsl.ViewMode;
import pl.polsl.controller.AccessoriesController;
import pl.polsl.controller.DictionaryController;
import pl.polsl.model.Accessory;
import pl.polsl.model.Dictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Julia Kubieniec
 * @version 1.0
 */
@Controller
public class AccessoriesWebController extends  BaseWebController {

    @Autowired
    AccessoriesController accessoriesController;
    @Autowired
    DictionaryController dictionaryController;

    private ViewMode viewMode;
    private Accessory accessory;
    private boolean flag;
    private boolean editPart1;
    private Set<Accessory> freeAccessory;

    /**
     * GET method for return accessories view
     * show only free accessories (available in magazine)
     * @param model actual web model
     * @return accessories view
     */
    @RequestMapping(value = "/accessories")
    public String getAccessories(Model model) {
        if (Data.user == null) {
            model.asMap().clear();
            model.addAttribute("userNotLoggedIn", true);
            return "sign_in";
        } else if (!privilegesController.getReadPriv(Data.accessoriesModuleValue, Data.user)) {
            model.asMap().clear();
            model.addAttribute("forbiddenAccess", true);
        } else {
            //   model.addAttribute("accessories",accessoriesController.findAll());
            freeAccessory = accessoriesController.findFreeAccessories();
        }
        viewMode = ViewMode.DEFAULT;
        model.addAttribute("accessories",freeAccessory);
        model.addAttribute("controlsPanelVisible", false);
        refreshMenuPrivileges(model);
        analisePrivileges(Data.accessoriesModuleValue);
        model.addAttribute("insertEnabled", insertEnabled);
        model.addAttribute("updateEnabled", updateEnabled);
        model.addAttribute("deleteEnabled", deleteEnabled);
        model.addAttribute("classActiveAccessories","active");
        return "accessories";
    }

    /**
     * GET method that allows add new accessory
     * @param model actual web model
     * @return redirect to accessoryDetails method
     */
    @RequestMapping(value ="/addNewAccessory")
    public String addNewAccessory(Model model){
        accessory = new Accessory();
        viewMode = ViewMode.INSERT;
        flag = false;
        editPart1 = false;
        return "redirect:/accessoryDetails";
    }

    /**
     * GET method that allows delete accessory
     * @param id id accessory with will be delete
     * @return redirect to accessories view
     */
    @RequestMapping(value = "/deleteAccessory/{id}")
    public String deleteAccessory(@PathVariable("id")int id) {
        accessoriesController.deleteAccessory(id);
        return "redirect:/accessories";
    }

    /**
     * GET method that allows edit accessory
     * @param model actual web model
     * @param id id accessory with will be edit
     * @return redirect to accessoryDetails method
     */
    @RequestMapping(value = "/editAccessory/{id}")
    public String editAccessory(Model model, @PathVariable("id")int id) {
        viewMode = ViewMode.EDIT;
        accessory = accessoriesController.findOne(id);
        editPart1 = false;
        return "redirect:/accessoryDetails";
    }

    /**
     * GET method that allows view all details about accessory
     * @param model actual web model
     * @param id id accessory with will be view
     * @return redirect to accessoryDetails method
     */
    @RequestMapping(value = "/viewAccessory/{id}")
    public String viewAccessory(Model model, @PathVariable("id")int id) {
        viewMode = ViewMode.VIEW_ALL;
        accessory = accessoriesController.findOne(id);
        editPart1 = true;
        return "redirect:/accessoryDetails";
    }

    /**
     * GET method that allows transfer all necessary information about accessories
     * @param model actual web model
     * @return accessories view
     */
    @RequestMapping(value = "/accessoryDetails", method = RequestMethod.GET)
    public String accessoryDetails(Model model){
        refreshMenuPrivileges(model);
        model.addAttribute("accessory",accessory);
     //   model.addAttribute("accessories",accessoriesController.findAll());
        model.addAttribute("accessories",freeAccessory);
        model.addAttribute("accessoryNames", dictionaryController.findAllAccessories());
        if(viewMode == ViewMode.INSERT) {
            model.addAttribute("controlsPanelVisible", true);
            model.addAttribute("controlsDisabledPart1", false);
            model.addAttribute("controlsDisabled",false);
            if(!flag) {
                flag = true;
                model.addAttribute("controlsDisabledPart2", true);
                return "accessories";
            }
        } else if(viewMode == ViewMode.EDIT) {
            model.addAttribute("controlsDisabled",false);
            model.addAttribute("controlsPanelVisible", true);
            model.addAttribute("controlsDisabledPart1", false);
            model.addAttribute("controlsDisabledPart2", true);
        } else if(viewMode == ViewMode.VIEW_ALL) {
            model.addAttribute("controlsDisabled",true);
            model.addAttribute("controlsPanelVisible", true);
            model.addAttribute("controlsDisabledPart1", true);
            model.addAttribute("controlsDisabledPart2", true);
        }
        model.addAttribute("accessoryNameId",accessory.getAccessory().getId());
        List<Dictionary> accesoryList = dictionaryController.findAllAccessories();
        List<Integer> costList = new ArrayList<>();
        List<Integer> assemblyCostList = new ArrayList<>();
        for(int i = 0; i < accesoryList.size(); i++) {
            if(accesoryList.get(i).getValue().equals(accessory.getAccessory().getValue())) {
                costList.add(Integer.parseInt(accesoryList.get(i).getValue2()));
                assemblyCostList.add(Integer.parseInt(accesoryList.get(i).getValue3()));
            }
        }
        model.addAttribute("costList",costList);
        model.addAttribute("assemblyCostList",assemblyCostList);
        if(editPart1 && !(viewMode == ViewMode.VIEW_ALL)) {
            model.addAttribute("controlsDisabledPart1", true);
            model.addAttribute("controlsDisabledPart2", false);
        }
        if(viewMode == ViewMode.VIEW_ALL || viewMode == ViewMode.EDIT) {
            model.addAttribute("cost", accessory.getCost());
            model.addAttribute("assemblyCost",accessory.getAssemblyCost());
        }
        return "accessories";
    }

    /**
     * GET method for allows modify accessory
     * @param name name of edit/add accessory
     * @return redirect to accessoryDetails method
     */
    @RequestMapping(value = "/modifyAccessory", method = RequestMethod.POST)
    public String modifyAccessory(@RequestParam("name")int name){
        accessory.setAccessory(dictionaryController.findOne(name));
        editPart1 = true;
        return "redirect:/accessoryDetails";
    }

    /**
     * POST method that allows save accessory to database
     * @param id id of edit/add accessory
     * @param cost cost of edit/add accessory
     * @param assemblyCost assemblyCost of edit/add accessory
     * @return redirect to accessories method
     */
    @RequestMapping(value = "/modifyAccessoryCost", method = RequestMethod.POST)
    public String modifyAccessoryCost(@RequestParam("id")int id,@RequestParam("cost")int cost,
                                      @RequestParam("assemblyCost")int assemblyCost){
        if(viewMode == ViewMode.INSERT){
            accessoriesController.addAccessory(accessory.getAccessory().getId(),cost,assemblyCost);
        } else if(viewMode == ViewMode.EDIT){
            accessoriesController.editAccessory(id,accessory.getAccessory().getId(),cost,assemblyCost);
        }
        return "redirect:/accessories";
    }

    /**
     * GET method that allows discard all changes in select/add accessory
     * @return redirect to accessories method
     */
    @RequestMapping(value="/resetAccessoriesChange")
    public  String resetChange(){
        viewMode = ViewMode.DEFAULT;
        return "redirect:/accessories";
    }

}

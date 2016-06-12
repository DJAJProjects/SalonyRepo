package pl.polsl.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.polsl.Data;
import pl.polsl.ViewMode;
import pl.polsl.controller.DictionaryController;
import pl.polsl.model.Dictionary;

/**
 * Created by Dominika Błasiak on 02.05.2016.
 */
@Controller
public class DictionaryWebController extends BaseWebController{
    private ViewMode viewMode;
    @Autowired
    private DictionaryController dictionaryController;

    @RequestMapping(value ="/dictionary")
    public String getDictionaries(Model model) {
        model.addAttribute("dictionaries", dictionaryController.findAll());
        refreshMenuPrivileges(model);
        if (Data.user == null) {
            model.asMap().clear();
            model.addAttribute("userNotLoggedIn", true);
            return "sign_in";
        } else if (!privilegesController.getReadPriv(Data.dictionaryModuleValue, Data.user)) {
            model.asMap().clear();
            model.addAttribute("forbiddenAccess", true);
        } else {
            model.addAttribute("dictionaries", dictionaryController.findAll());
        }
        analisePrivileges(Data.dictionaryModuleValue);
        model.addAttribute("insertEnabled", insertEnabled);
        model.addAttribute("updateEnabled", updateEnabled);
        model.addAttribute("deleteEnabled", deleteEnabled);
        refreshMenuPrivileges(model);
        return "dictionary";
    }

    @RequestMapping(value ="/editDictionary/{id}")
    public String editDictionary(RedirectAttributes redirectAttributes, @PathVariable("id")int id){
        viewMode = ViewMode.EDIT;
        redirectAttributes.addFlashAttribute("controlsPanelVisible", true);
        redirectAttributes.addFlashAttribute("controlsDisabled", false);
        redirectAttributes.addFlashAttribute("dictionary", dictionaryController.findOne(id));
        redirectAttributes.addFlashAttribute("types", dictionaryController.findAllTypes());
        return "redirect:/dictionary/";
    }

    @RequestMapping(value ="/viewDictionary/{id}")
    public String viewDictionary(RedirectAttributes redirectAttributes, @PathVariable("id")int id) {
        viewMode = ViewMode.VIEW_ALL;
        redirectAttributes.addFlashAttribute("controlsPanelVisible", true);
        redirectAttributes.addFlashAttribute("controlsDisabled", true);
        redirectAttributes.addFlashAttribute("dictionary", dictionaryController.findOne(id));
        redirectAttributes.addFlashAttribute("types", dictionaryController.findAllTypes());
        return "redirect:/dictionary/";
    }

    @RequestMapping(value ="/acceptModifyDictionary", method = RequestMethod.POST)
    public String editDictionary(RedirectAttributes redirectAttributes, @RequestParam("id") int id,
                               @RequestParam("type") String type,
                               @RequestParam(value = "value") String value,
                               @RequestParam(value = "value2")String value2,
                                 @RequestParam(value = "value3")String value3){
        if(viewMode == ViewMode.EDIT) {
           if(dictionaryController.updateDictionaryValue(id, type, value, value2, value3) == null){
               redirectAttributes.addFlashAttribute("databaseError");
           }
        }
        else if(viewMode==ViewMode.INSERT) {
            if(dictionaryController.addDictionaryValue(id, type, value, value2, value3)==null){
                redirectAttributes.addFlashAttribute("databaseError");
            }
        }
        viewMode = ViewMode.DEFAULT;
        return "redirect:/dictionary/";
    }

    @RequestMapping(value ="/addDictionary")
    public String addDictionary(RedirectAttributes redirectAttributes){
        viewMode = ViewMode.INSERT;
        redirectAttributes.addFlashAttribute("controlsPanelVisible", true);
        redirectAttributes.addFlashAttribute("controlsDisabled", false);
        redirectAttributes.addFlashAttribute("dictionary", new Dictionary());
        redirectAttributes.addFlashAttribute("types", dictionaryController.findAllTypes());
        return "redirect:/dictionary/";
    }

    @RequestMapping(value ="/deleteDictionary/{id}")
    public String deleteDictionary(RedirectAttributes redirectAttributes, @PathVariable("id")int id) {
        Dictionary dictionary = dictionaryController.findOne(id);
        if (dictionary.getPosition() != null
                || dictionary.getAccessory() != null
                || dictionary.getCarName() != null
                || dictionary.getCity() != null
                || dictionary.getCountry() != null
                || dictionary.getInvoiceType() != null
                || dictionary.getPaymentForm() != null
                || dictionary.getServices() != null)
            redirectAttributes.addFlashAttribute("deleteDictionaryError", true);
        else
            dictionaryController.delete(id);
        return "redirect:/dictionary/";
    }

    @RequestMapping(value="/ResetDictionaryChange")
    public  String resetChange(){
        viewMode = ViewMode.DEFAULT;
        return "redirect:/dictionary";
    }
}

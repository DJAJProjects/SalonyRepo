package pl.polsl.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
    public String getDictionaries(Model model){
        model.addAttribute("dictionaries", dictionaryController.findAll());
        refreshMenuPrivileges(model);
        return "dictionary";
    }

    @RequestMapping(value ="/editDictionary/{id}")
    public String editDictionary(RedirectAttributes redirectAttributes, @PathVariable("id")int id){
        viewMode = ViewMode.EDIT;
        redirectAttributes.addAttribute("controlsPanelVisible", true);
        redirectAttributes.addAttribute("controlsDisabled", false);
        redirectAttributes.addAttribute("dictionary", dictionaryController.findOne(id));
        redirectAttributes.addAttribute("dictionaries", dictionaryController.findAll());
        redirectAttributes.addAttribute("types", dictionaryController.findAllTypes());
        return "redirect:/dictionary/";
    }

    @RequestMapping(value ="/viewDictionary/{id}")
    public String viewDictionary(RedirectAttributes redirectAttributes, @PathVariable("id")int id) {
        viewMode = ViewMode.VIEW_ALL;
        redirectAttributes.addAttribute("controlsPanelVisible", true);
        redirectAttributes.addAttribute("controlsDisabled", true);
        redirectAttributes.addAttribute("dictionary", dictionaryController.findOne(id));
        redirectAttributes.addAttribute("dictionaries", dictionaryController.findAll());
        redirectAttributes.addAttribute("types", dictionaryController.findAllTypes());
        return "redirect:/dictionary/";
    }

    @RequestMapping(value ="/acceptModifyDictionary", method = RequestMethod.POST)
    public String editDictionary(@RequestParam("id") int id,
                               @RequestParam("type") String type,
                               @RequestParam(value = "value") String value,
                               @RequestParam(value = "value2")String value2){
        if(viewMode == ViewMode.EDIT) {
            Dictionary dictionary = dictionaryController.updateDictionaryValue(id, type, value, value2);
        }
        else if(viewMode==ViewMode.INSERT) {
            Dictionary dictionary = dictionaryController.addDictionaryValue(id, type, value, value2);
        }
        return "redirect:/dictionary/";
    }

    @RequestMapping(value ="/addDictionary")
    public String addDictionary(RedirectAttributes redirectAttributes){
        viewMode = ViewMode.INSERT;
        redirectAttributes.addAttribute("controlsPanelVisible", true);
        redirectAttributes.addAttribute("controlsDisabled", false);
        redirectAttributes.addAttribute("dictionary", new Dictionary());
        redirectAttributes.addAttribute("dictionaries", dictionaryController.findAll());
        redirectAttributes.addAttribute("types", dictionaryController.findAllTypes());
        return "redirect:/dictionary/";
    }

    @RequestMapping(value ="/deleteDictionary/{id}")
    public String deleteDictionary(@PathVariable("id")int id){
        dictionaryController.delete(id);
        return "redirect:/dictionary/";
    }
}

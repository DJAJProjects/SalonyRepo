package pl.polsl.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
        model.addAttribute("controlsPanelVisible", false);
        refreshMenuPrivileges(model);
        return "dictionary";
    }

    @RequestMapping(value ="/editDictionary/{id}")
    public String editDictionary(Model model, @PathVariable("id")int id){
        viewMode = ViewMode.EDIT;
        model.addAttribute("controlsPanelVisible", true);
        model.addAttribute("controlsDisabled", false);
        model.addAttribute("dictionary", dictionaryController.findOne(id));
        model.addAttribute("dictionaries", dictionaryController.findAll());
        model.addAttribute("types", dictionaryController.findAllTypes());
        return "dictionary";
    }

    @RequestMapping(value ="/viewDictionary/{id}")
    public String viewDictionary(Model model, @PathVariable("id")int id) {
        viewMode = ViewMode.VIEW_ALL;
        model.addAttribute("controlsPanelVisible", true);
        model.addAttribute("controlsDisabled", true);
        model.addAttribute("dictionary", dictionaryController.findOne(id));
        model.addAttribute("dictionaries", dictionaryController.findAll());
        model.addAttribute("types", dictionaryController.findAllTypes());
        return "dictionary";
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
    public String addDictionary(Model model){
        viewMode = ViewMode.INSERT;
        model.addAttribute("controlsPanelVisible", true);
        model.addAttribute("controlsDisabled", false);
        model.addAttribute("dictionary", new Dictionary());
        model.addAttribute("dictionaries", dictionaryController.findAll());
        model.addAttribute("types", dictionaryController.findAllTypes());
        return "dictionary";
    }

//    @RequestMapping(value ="/addShowroom", method = RequestMethod.POST)
//    public String addShowroom(@RequestParam("name") String name, @RequestParam(value = "street") String street, @RequestParam(value = "city")int city, @RequestParam(value = "country")int country, @RequestParam(value = "director")int director){
//        Showroom showroom = showroomsController.addShowroom(name,street,city, country, director);
//        return "redirect:/showroom/";
//    }

    @RequestMapping(value ="/deleteDictionary/{id}")
    public String deleteDictionary(@PathVariable("id")int id){
        dictionaryController.delete(id);
        return "redirect:/dictionary/";
    }
}

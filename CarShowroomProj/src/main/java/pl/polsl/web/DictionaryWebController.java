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
 * Dictionary web controller class
 * @author Dominika Błasiak
 * @version 1.0
 */
@Controller
public class DictionaryWebController extends BaseWebController{
    @Autowired
    private DictionaryController dictionaryController;

    /**
     * GET method for return dictionary view
     * @param model actual web model
     * @return dictionary view
     */
    @RequestMapping(value ="/dictionary")
    public String getDictionaries(Model model) {
        model.addAttribute("dictionaries", dictionaryController.findAll());
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

    /**
     * Method to display edit dictionary view
     * @param redirectAttributes
     * @return redirect to getDictionary method
     */
    @RequestMapping(value ="/editDictionary/{id}")
    public String editDictionary(RedirectAttributes redirectAttributes, @PathVariable("id")int id){
        viewMode = ViewMode.EDIT;
        redirectAttributes.addFlashAttribute("controlsPanelVisible", true);
        redirectAttributes.addFlashAttribute("controlsDisabled", false);
        redirectAttributes.addFlashAttribute("dictionary", dictionaryController.findOne(id));
        redirectAttributes.addFlashAttribute("types", dictionaryController.findAllTypes());
        return "redirect:/dictionary/";
    }

    /**
     * Method to display dictionary details view
     * @param redirectAttributes
     * @return redirect to getDictionary method
     */
    @RequestMapping(value ="/viewDictionary/{id}")
    public String viewDictionary(RedirectAttributes redirectAttributes, @PathVariable("id")int id) {
        viewMode = ViewMode.VIEW_ALL;
        redirectAttributes.addFlashAttribute("controlsPanelVisible", true);
        redirectAttributes.addFlashAttribute("controlsDisabled", true);
        redirectAttributes.addFlashAttribute("dictionary", dictionaryController.findOne(id));
        redirectAttributes.addFlashAttribute("types", dictionaryController.findAllTypes());
        return "redirect:/dictionary/";
    }

    /**
     * Method to save dictionary to database
     * @param redirectAttributes
     * @param id dictionary id
     * @param type dictionary type
     * @param value dictionary value
     * @param value2 dictionary alternative value
     * @param value3 dictionary alternative value
     * @return redirect to getDictionary method
     */
    @RequestMapping(value ="/acceptModifyDictionary", method = RequestMethod.POST)
    public String addOrEditDictionary(RedirectAttributes redirectAttributes, @RequestParam("id") int id,
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

    /**
     * Method to display add dictionary view
     * @param redirectAttributes
     * @return redirect to getDictionary method
     */
    @RequestMapping(value ="/addDictionary")
    public String addDictionary(RedirectAttributes redirectAttributes){
        viewMode = ViewMode.INSERT;
        redirectAttributes.addFlashAttribute("controlsPanelVisible", true);
        redirectAttributes.addFlashAttribute("controlsDisabled", false);
        redirectAttributes.addFlashAttribute("dictionary", new Dictionary());
        redirectAttributes.addFlashAttribute("types", dictionaryController.findAllTypes());
        return "redirect:/dictionary/";
    }

    /**
     * Method to delete dictionary
     * @param redirectAttributes redirect attributes
     * @param id dictionary id to remove
     * @return redirect to getDictionary method
     */
    @RequestMapping(value ="/deleteDictionary/{id}")
    public String deleteDictionary(RedirectAttributes redirectAttributes, @PathVariable("id")int id) {
        Dictionary dictionary = dictionaryController.findOne(id);
        if (dictionary.getPosition() != null && dictionary.getPosition().size() != 0
                || (dictionary.getAccessory() != null && dictionary.getAccessory().size() != 0)
                || (dictionary.getCarName() != null && dictionary.getCarName().size() != 0)
                || (dictionary.getCity() != null && dictionary.getCity().size() != 0)
                || (dictionary.getCountry() != null && dictionary.getCountry().size() != 0)
                || (dictionary.getInvoiceType() != null && dictionary.getInvoiceType().size() != 0)
                || (dictionary.getPaymentForm() != null && dictionary.getPaymentForm().size() != 0)
                || (dictionary.getServices() != null && dictionary.getServices().size() != 0))
            redirectAttributes.addFlashAttribute("deleteDictionaryError", true);
        else
            dictionaryController.delete(id);
        return "redirect:/dictionary/";
    }

    /**
     * Method to cancel dictionary edit or add
     * @return redirect to getDictionary method
     */
    @RequestMapping(value="/resetDictionaryChange")
    public  String resetChange(){
        viewMode = ViewMode.DEFAULT;
        return "redirect:/dictionary";
    }
}

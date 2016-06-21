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
import pl.polsl.controller.DictionaryController;
import pl.polsl.controller.PromotionsController;
import pl.polsl.model.Promotion;

import java.sql.Date;

/**
 * Created by Julia on 2016-04-28.
 */
@Controller
public class PromotionWebController extends BaseWebController {

    @Autowired
    PromotionsController promotionsController;

    private ViewMode viewMode;

    @RequestMapping(value = "/promotions")
    public String getAccessories(Model model) {
        if (Data.user == null) {
            model.asMap().clear();
            model.addAttribute("userNotLoggedIn", true);
            return "sign_in";
        } else if (!privilegesController.getReadPriv(Data.promotionModuleValue, Data.user)) {
            model.asMap().clear();
            model.addAttribute("forbiddenAccess", true);
        } else {
            model.addAttribute("promotions",promotionsController.findAll());
        }
        model.addAttribute("controlsPanelVisible", false);
        refreshMenuPrivileges(model);
        analisePrivileges(Data.promotionModuleValue);
        model.addAttribute("insertEnabled", insertEnabled);
        model.addAttribute("updateEnabled", updateEnabled);
        model.addAttribute("deleteEnabled", deleteEnabled);
        return "promotions";
    }

    @RequestMapping(value ="/addNewPromotion")
    public String addNewPromotion(Model model){
        refreshMenuPrivileges(model);
        Promotion promotion = new Promotion();
        viewMode = ViewMode.INSERT;
        model.addAttribute("promotion",promotion);
        model.addAttribute("promotions",promotionsController.findAll());
        model.addAttribute("controlsPanelVisible", true);
        model.addAttribute("controlsDisabled", false);
        return "promotions";
    }

    @RequestMapping(value = "/deletePromotion/{id}")
    public String deleteAccessory(@PathVariable("id")int id) {
        promotionsController.deletePromotion(id);
        return "redirect:/promotions";
    }

    @RequestMapping(value = "/editPromotion/{id}")
    public String editPromotion(Model model, @PathVariable("id")int id) {
        refreshMenuPrivileges(model);
        viewMode = ViewMode.EDIT;
        model.addAttribute("promotions",promotionsController.findAll());
        Promotion promotion = promotionsController.findOne(id);
        model.addAttribute("promotion",promotion);
        model.addAttribute("promotionPercValue",promotion.getPercValue());
        model.addAttribute("promotionName",promotion.getName());
        model.addAttribute("dateStart",promotion.getDateStart());
        model.addAttribute("dateEnd",promotion.getDateEnd());
        model.addAttribute("controlsPanelVisible", true);
        model.addAttribute("controlsDisabled", false);
        return "promotions";
    }

    @RequestMapping(value = "/viewPromotion/{id}")
    public String viewPromotion(Model model, @PathVariable("id")int id) {
        refreshMenuPrivileges(model);
        viewMode = ViewMode.VIEW_ALL;
        model.addAttribute("promotions",promotionsController.findAll());
        Promotion promotion = promotionsController.findOne(id);
        model.addAttribute("promotion",promotion);
        model.addAttribute("promotionPercValue",promotion.getPercValue());
        model.addAttribute("promotionName",promotion.getName());
        model.addAttribute("dateStart",promotion.getDateStart());
        model.addAttribute("dateEnd",promotion.getDateEnd());
        model.addAttribute("controlsPanelVisible", true);
        model.addAttribute("controlsDisabled", true);
        return "promotions";
    }

    @RequestMapping(value = "/modifyPromotion", method = RequestMethod.POST)
    public String editPromotion(@RequestParam("id")int id, @RequestParam("percValue")int percValue, @RequestParam("name")String name,
                                @RequestParam("dateStart")Date dateStart, @RequestParam("dateEnd") Date dateEnd) {
        if(viewMode == ViewMode.INSERT){
            Promotion promotion = promotionsController.addPromotion(percValue,name,dateStart,dateEnd);
        } else if(viewMode == ViewMode.EDIT) {
            Promotion promotion = promotionsController.editPromotion(id, percValue, name, dateStart, dateEnd);
        }
        return "redirect:/promotions";
    }

    @RequestMapping(value="/resetPromotionChange")
    public  String resetChange(){
        viewMode = ViewMode.DEFAULT;
        return "redirect:/promotions";
    }

}

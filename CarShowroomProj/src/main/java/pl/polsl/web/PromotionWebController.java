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

    /**
     * GET method for return promotion view
     * @param model actual web model
     * @return promotion view
     */
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
        model.addAttribute("classActivePromotions","active");
        return "promotions";
    }

    /**
     * GET method for allows add new promotion
     * @param model actual web model
     * @return redirect to promotions method
     */
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

    /**
     * GET method for allows delete selected promotion
     * @param id if of selected promotion
     * @return redirect to promotions method
     */
    @RequestMapping(value = "/deletePromotion/{id}")
    public String deleteAccessory(@PathVariable("id")int id) {
        promotionsController.deletePromotion(id);
        return "redirect:/promotions";
    }


    /**
     * GET method for allows edit selected promotion
     * @param id if of selected promotion
     * @return redirect to promotions method
     */
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

    /**
     * GET method for allows view all details about selected promotion
     * @param id if of selected promotion
     * @return redirect to promotions method
     */
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

    /**
     * POST method that allows save promotion to database
     * @param id id of edit/add promotion
     * @param percValue percent of cost reduction
     * @param name promotion name
     * @param dateStart start date of promotion
     * @param dateEnd end date of promotion
     * @return redirect to promotions method
     */
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

    /**
     * GET method that allows discard all changes in select/add promotion
     * @return redirect to promotions method
     */
    @RequestMapping(value="/resetPromotionChange")
    public  String resetChange(){
        viewMode = ViewMode.DEFAULT;
        return "redirect:/promotions";
    }

}

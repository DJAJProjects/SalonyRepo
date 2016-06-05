package pl.polsl.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.polsl.Data;
import pl.polsl.controller.DictionaryController;
import pl.polsl.controller.PrivilegesController;
import pl.polsl.controller.WorkersController;


/**
 * Created by Dominika Błasiak on 2016-04-09.
 */
@Controller
public class MainController extends BaseWebController {

    @Autowired
    DictionaryController dictionaryController;

    @Autowired
    PrivilegesController privilegesController;

    @Autowired
    private WorkersController workersController;

    @RequestMapping(value = "/")
    public String home(Model model){
        model.addAttribute("error",false);
        return "sign_in";

    }

    @RequestMapping(value = "signIn", method= RequestMethod.POST)
    public String signIn(Model model, @RequestParam(value = "login")String login, @RequestParam(value = "password")String password){
        Data.user = workersController.findOne(login,password);
        if(Data.user!=null){
            refreshMenuPrivileges(model);
            Data.adminId = dictionaryController.findAdmin();
            Data.directorId = dictionaryController.findDirector();
            return "menu";
        }
        else{
            model.addAttribute("error",true);
        }
        return "sign_in";
    }
}

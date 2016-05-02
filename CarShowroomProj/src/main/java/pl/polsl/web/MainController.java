package pl.polsl.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.polsl.Data;
import pl.polsl.controller.DictionaryController;
import pl.polsl.controller.WorkersController;

import java.util.List;


/**
 * Created by Dominika Błasiak on 2016-04-09.
 */
@Controller
public class MainController {

    @Autowired
    DictionaryController dictionaryController;

    @RequestMapping(value = "/")
    public String home(Model model){
        model.addAttribute("error",false);
        List<String> typ = dictionaryController.findAllTypes();
        System.out.println(typ.size());
        return "sign_in";

    }

    @Autowired
    private WorkersController workersController;


    @RequestMapping(value = "signIn", method= RequestMethod.POST)
    public String signIn(Model model, @RequestParam(value = "login")String login, @RequestParam(value = "password")String password){
        Data.user = workersController.findOne(login,password);
        if(Data.user!=null){
            System.out.println(Data.user.getId());
            return "index";
        }
        else{
            model.addAttribute("error",true);
        }
        return "sign_in";

    }
}

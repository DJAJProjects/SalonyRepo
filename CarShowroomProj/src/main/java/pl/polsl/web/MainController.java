package pl.polsl.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.polsl.controller.WorkersController;
import pl.polsl.model.Worker;


/**
 * Created by Dominika Błasiak on 2016-04-09.
 */
@Controller
public class MainController {
    @RequestMapping(value = "/")
    public String home(Model model){
        model.addAttribute("error",false);
        return "sign_in";
    }

    @Autowired
    private WorkersController workersController;

    public static Worker worker;
    @RequestMapping(value = "signIn", method= RequestMethod.POST)
    public String signIn(Model model, @RequestParam(value = "login")String login, @RequestParam(value = "password")String password){
        worker = workersController.findOne(login,password);
        if(worker!=null){
            System.out.println(worker.getId());
            return "index";
        }
        else{
            model.addAttribute("error",true);
        }
        return "sign_in";

    }
}

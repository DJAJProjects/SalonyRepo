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
public class MainController {

    @Autowired
    DictionaryController dictionaryController;

    @Autowired
    PrivilegesController privilegesController;

    @RequestMapping(value = "/")
    public String home(Model model){
        model.addAttribute("error",false);
        return "sign_in";

    }

    @Autowired
    private WorkersController workersController;


    @RequestMapping(value = "signIn", method= RequestMethod.POST)
    public String signIn(Model model, @RequestParam(value = "login")String login, @RequestParam(value = "password")String password){
        Data.user = workersController.findOne(login,password);
        if(Data.user!=null){
            model.addAttribute("workersVisible", privilegesController.getReadPriv("Pracownicy", Data.user));
            model.addAttribute("showroomsVisible", privilegesController.getReadPriv("Salony", Data.user));
            model.addAttribute("contractsVisible", privilegesController.getReadPriv("Sprzedaże", Data.user));
            model.addAttribute("contractorsVisible", privilegesController.getReadPriv("Klienci", Data.user));
            model.addAttribute("invoicesVisible", privilegesController.getReadPriv("Faktury", Data.user));
            model.addAttribute("reportsVisible", privilegesController.getReadPriv("Raporty", Data.user));
            model.addAttribute("accessoriesVisible", privilegesController.getReadPriv("Akcesoria", Data.user));
            model.addAttribute("promotionsVisible", privilegesController.getReadPriv("Promocje", Data.user));
            model.addAttribute("servicesVisible", privilegesController.getReadPriv("Serwisy", Data.user));
            model.addAttribute("dictionaryVisible", privilegesController.getReadPriv("Słownik", Data.user));
            model.addAttribute("privilegesVisible", privilegesController.getReadPriv("Uprawnienia", Data.user));
            model.addAttribute("carsVisible", privilegesController.getReadPriv("Samochody", Data.user));

            return "menu";
        }
        else{
            model.addAttribute("error",true);
        }
        return "sign_in";

    }
}

package pl.polsl.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import pl.polsl.Data;
import pl.polsl.controller.PrivilegesController;

/**
 * Created by Kuba on 27.05.2016.
 */
public class BaseWebController {

    @Autowired
    protected PrivilegesController privilegesController;

    protected boolean insertEnabled;
    protected boolean updateEnabled;
    protected boolean deleteEnabled;

    protected void analisePrivileges(String modulename){
        insertEnabled = privilegesController.getInsertPriv(modulename, Data.user);
        updateEnabled = privilegesController.getDeletePriv(modulename, Data.user);
        deleteEnabled = privilegesController.getUpdatePriv(modulename, Data.user);
    }

    protected void refreshMenuPrivileges(Model model){
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
    }

}



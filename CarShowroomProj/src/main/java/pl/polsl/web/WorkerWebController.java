package pl.polsl.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.polsl.controller.WorkersController;

/**
 * Created by Dominika Błasiak on 09.04.2016.
 */
@Controller
public class WorkerWebController {
    @Autowired
    private WorkersController workersController;

    @RequestMapping(value ="/worker")
    public String allGroups(Model model){
        model.addAttribute("workers", workersController.findAll());
        return "worker";
    }
}

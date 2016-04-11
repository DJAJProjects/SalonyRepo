package pl.polsl.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.polsl.controller.ShowroomsController;

/**
 * Created by Dominika Błasiak on 08.04.2016.
 */
@Controller
public class ShowroomWebController {

    @Autowired
    private ShowroomsController showroomsController;

    @RequestMapping(value ="/showroom")
    public String getShowrooms(Model model){
        model.addAttribute("showrooms", showroomsController.findAll());
        return "showroom";
    }

    @RequestMapping(value ="/editShowroom")
    public void editShowroom(){

    }

    @RequestMapping(value ="/deleteShowroom/{id}", method = RequestMethod.GET)
    public String deleteShowroom(@PathVariable("id")int id){
        showroomsController.delete(id);
        return "redirect:/showroom/";
    }
}

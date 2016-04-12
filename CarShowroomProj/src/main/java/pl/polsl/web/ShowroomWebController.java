package pl.polsl.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.polsl.controller.DictionaryController;
import pl.polsl.controller.ShowroomsController;
import pl.polsl.controller.WorkersController;
import pl.polsl.model.Showroom;

/**
 * Created by Dominika Błasiak on 08.04.2016.
 */
@Controller
public class ShowroomWebController {

    private boolean visibleAddForm = false;

    @Autowired
    private ShowroomsController showroomsController;
    @Autowired
    private DictionaryController dictionaryController;
    @Autowired
    private WorkersController workersController;


    @RequestMapping(value ="/showroom")
    public String getShowrooms(Model model){
        model.addAttribute("showrooms", showroomsController.findAll());
        return "showroom";
    }

    @RequestMapping(value ="/editShowroom")
    public void editShowroom(){

    }

    @RequestMapping(value ="/addShowroom")
    public String addShowroom(Model model){
        visibleAddForm = true;
        model.addAttribute("cities", dictionaryController.findAllCities());
        model.addAttribute("countries", dictionaryController.findAllCountries());
        model.addAttribute("directors", workersController.findAllFreeDirectors());
        model.addAttribute("showrooms", showroomsController.findAll());
        model.addAttribute("visible", visibleAddForm);
        return "showroom";
    }

    @RequestMapping(value ="/addShowroom", method = RequestMethod.POST)
    public String addShowroom(@RequestParam("name") String name, @RequestParam(value = "street") String street, @RequestParam(value = "city")int city, @RequestParam(value = "country")int country, @RequestParam(value = "director")int director){
        Showroom showroom = showroomsController.addShowroom(name,street,city, country, director);
        visibleAddForm = true;
        return "redirect:/showroom/";
    }

    @RequestMapping(value ="/deleteShowroom/{id}", method = RequestMethod.GET)
    public String deleteShowroom(@PathVariable("id")int id){
        showroomsController.delete(id);
        return "redirect:/showroom/";
    }
}

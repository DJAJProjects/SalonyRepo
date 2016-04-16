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

    private boolean addVisible = false;
    private boolean editVisible = false;
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

    @RequestMapping(value ="/editShowroom/{id}")
    public String editShowroom(Model model, @PathVariable("id")int id){
        editVisible=true;
        Showroom showroom = showroomsController.findOne(id);
        model.addAttribute("directorId", showroom.getDirector().getId());
        model.addAttribute("cityId", showroom.getCity().getId());
        model.addAttribute("countryId", showroom.getCountry().getId());
        model.addAttribute("directorSurname",showroom.getDirector().getSurname());
        model.addAttribute("showroom", showroom);
        model.addAttribute("directors", workersController.findAllFreeDirectors());
        model.addAttribute("countries", dictionaryController.findAllCountries());
        model.addAttribute("cities", dictionaryController.findAllCities());
        model.addAttribute("showrooms", showroomsController.findAll());
        model.addAttribute("editVisible", editVisible);
        return "showroom";

    }

    @RequestMapping(value ="/editShowroom", method = RequestMethod.POST)
    public String editShowroom(@RequestParam("id") int id,@RequestParam("name") String name, @RequestParam(value = "street") String street, @RequestParam(value = "city")int city, @RequestParam(value = "country")int country, @RequestParam(value = "director")int director){
        Showroom showroom = showroomsController.updateShowroom(id, name,street,city, country, director);
        editVisible = false;
        return "redirect:/showroom/";
    }

    @RequestMapping(value ="/addShowroom")
    public String addShowroom(Model model){
        addVisible = true;
        model.addAttribute("cities", dictionaryController.findAllCities());
        model.addAttribute("countries", dictionaryController.findAllCountries());
        model.addAttribute("directors", workersController.findAllFreeDirectors());
        model.addAttribute("showrooms", showroomsController.findAll());
        model.addAttribute("addVisible", addVisible);
        return "showroom";
    }

    @RequestMapping(value ="/addShowroom", method = RequestMethod.POST)
    public String addShowroom( @RequestParam(value = "name") String name, @RequestParam(value = "street", required = false) String street, @RequestParam(value = "city", required = false)Integer city, @RequestParam(value = "country", required = false)Integer country, @RequestParam(value = "director", required = false)Integer director){

        Showroom showroom = showroomsController.addShowroom(name,street,city, country, director);
        addVisible = false;
        return "redirect:/showroom/";
    }

    @RequestMapping(value ="/deleteShowroom/{id}")
    public String deleteShowroom(@PathVariable("id")int id){
        showroomsController.delete(id);
        return "redirect:/showroom/";
    }
}

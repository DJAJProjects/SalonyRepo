package pl.polsl.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.polsl.ViewMode;
import pl.polsl.controller.DictionaryController;
import pl.polsl.controller.ShowroomsController;
import pl.polsl.controller.WorkersController;
import pl.polsl.model.Showroom;
import pl.polsl.model.Worker;

import java.util.List;

/**
 * Created by Dominika Błasiak on 08.04.2016.
 */
@Controller
public class ShowroomWebController extends  BaseWebController {
    private ViewMode viewMode;

    @Autowired
    private ShowroomsController showroomsController;
    @Autowired
    private DictionaryController dictionaryController;
    @Autowired
    private WorkersController workersController;


    @RequestMapping(value ="/showroom")
    public String getShowrooms(Model model){
        model.addAttribute("showrooms", showroomsController.findAll());
        refreshMenuPrivileges(model);
        return "showroom";
    }

    @RequestMapping(value ="/editShowroom/{id}")
    public String editShowroom(RedirectAttributes redirectAttributes, @PathVariable("id")int id){
        viewMode = ViewMode.EDIT;
        Showroom showroom = showroomsController.findOne(id);
        redirectAttributes.addFlashAttribute("controlsPanelVisible", true);
        redirectAttributes.addFlashAttribute("controlsDisabled", false);
        redirectAttributes.addFlashAttribute("directorId", showroom.getDirector().getId());
        redirectAttributes.addFlashAttribute("cityId", showroom.getCity().getId());
        redirectAttributes.addFlashAttribute("countryId", showroom.getCountry().getId());
        redirectAttributes.addFlashAttribute("directorSurname",showroom.getDirector().getSurname());
        redirectAttributes.addFlashAttribute("showroom", showroom);
        List<Worker> directors = workersController.findAllFreeDirectors();
        directors.add((workersController.findOne(showroom.getDirector().getId())));
        redirectAttributes.addFlashAttribute("directors",directors);
        redirectAttributes.addFlashAttribute("countries", dictionaryController.findAllCountries());
        redirectAttributes.addFlashAttribute("cities", dictionaryController.findAllCities());
        redirectAttributes.addFlashAttribute("showrooms", showroomsController.findAll());
        return "redirect:/showroom/";
    }


    @RequestMapping(value ="/viewShowroom/{id}")
    public String viewShowroom(RedirectAttributes redirectAttributes, @PathVariable("id")int id) {

        viewMode = ViewMode.VIEW_ALL;

        Showroom showroom = showroomsController.findOne(id);
        redirectAttributes.addFlashAttribute("controlsPanelVisible", true);
        redirectAttributes.addFlashAttribute("controlsDisabled", true);
        redirectAttributes.addFlashAttribute("directorId", showroom.getDirector().getId());
        redirectAttributes.addFlashAttribute("cityId", showroom.getCity().getId());
        redirectAttributes.addFlashAttribute("countryId", showroom.getCountry().getId());
        redirectAttributes.addFlashAttribute("directorSurname",showroom.getDirector().getSurname());
        redirectAttributes.addFlashAttribute("showroom", showroom);
        redirectAttributes.addFlashAttribute("directors", workersController.findAll());
        redirectAttributes.addFlashAttribute("countries", dictionaryController.findAllCountries());
        redirectAttributes.addFlashAttribute("cities", dictionaryController.findAllCities());
        redirectAttributes.addFlashAttribute("showrooms", showroomsController.findAll());

        return "redirect:/showroom/";
    }

    @RequestMapping(value ="/acceptModifyShowroom", method = RequestMethod.POST)
    public String editShowroom(@RequestParam("id") int id,
                               @RequestParam("name") String name,
                               @RequestParam(value = "street") String street,
                               @RequestParam(value = "city")int city,
                               @RequestParam(value = "country")int country,
                               @RequestParam(value = "director")int director){
        if(viewMode == ViewMode.EDIT) {
            Showroom showroom = showroomsController.updateShowroom(id, name, street, city, country, director);
        }
        else if(viewMode==ViewMode.INSERT) {
            Showroom showroom = showroomsController.addShowroom(name, street, city, country, director);
        }
        return "redirect:/showroom/";
    }

    @RequestMapping(value ="/addShowroom")
    public String addShowroom(RedirectAttributes redirectAttributes){
        viewMode = ViewMode.INSERT;
        Showroom showroom = new Showroom();
        redirectAttributes.addFlashAttribute("showroom", showroom);
        redirectAttributes.addFlashAttribute("controlsPanelVisible", true);
        redirectAttributes.addFlashAttribute("controlsDisabled", false);
        redirectAttributes.addFlashAttribute("cities", dictionaryController.findAllCities());
        redirectAttributes.addFlashAttribute("countries", dictionaryController.findAllCountries());
        redirectAttributes.addFlashAttribute("directors", workersController.findAllFreeDirectors());
        redirectAttributes.addFlashAttribute("showrooms", showroomsController.findAll());
        return "redirect:/showroom/";
    }

    @RequestMapping(value ="/deleteShowroom/{id}")
    public String deleteShowroom(@PathVariable("id")int id){
        showroomsController.delete(id);
        return "redirect:/showroom/";
    }
}

package pl.polsl.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.polsl.Data;
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

    @Autowired
    private ShowroomsController showroomsController;
    @Autowired
    private DictionaryController dictionaryController;
    @Autowired
    private WorkersController workersController;

    @RequestMapping(value ="/showroom")
    public String getShowrooms(Model model){
        if (Data.user == null) {
            model.asMap().clear();
            model.addAttribute("userNotLoggedIn", true);
            return "sign_in";
        } else if (!privilegesController.getReadPriv(Data.showroomModuleValue, Data.user)) {
            model.asMap().clear();
            model.addAttribute("forbiddenAccess", true);
        } else {
            model.addAttribute("showrooms", showroomsController.findShowroomsRelatedToWorker(Data.user));
        }
        analisePrivileges(Data.showroomModuleValue);
        model.addAttribute("insertEnabled", insertEnabled);
        model.addAttribute("updateEnabled", updateEnabled);
        model.addAttribute("deleteEnabled", deleteEnabled);
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
        directors.add(workersController.findOne(showroom.getDirector().getId()));
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
    public String editShowroom(RedirectAttributes redirectAttributes,
                               @RequestParam("id") int id,
                               @RequestParam("name") String name,
                               @RequestParam(value = "street") String street,
                               @RequestParam(value = "city")int city,
                               @RequestParam(value = "country")int country,
                               @RequestParam(value = "director")int director){
        if(viewMode == ViewMode.EDIT){
            Showroom oldShowroom = showroomsController.findOne(id);
            if(oldShowroom.getDirector().getId()!=director) {
                workersController.updateWorker(oldShowroom.getDirector().getId(), -1);
                workersController.updateWorker(director, id);
            }
            if(showroomsController.updateShowroom(id, name, street, city, country, director)==null) {
                redirectAttributes.addFlashAttribute("databaseError", true);
            }
        }
        else if(viewMode==ViewMode.INSERT) {
            if(showroomsController.addShowroom(name, street, city, country, director)==null)
                redirectAttributes.addFlashAttribute("databaseError", true);
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
        return "redirect:/showroom/";
    }

    @RequestMapping(value ="/deleteShowroom/{id}")
    public String deleteShowroom(RedirectAttributes redirectAttributes, @PathVariable("id")int id){
        Showroom showroom = showroomsController.findOne(id);
        if(showroom.getWorkers()!=null || showroom.getCars() !=null || showroom.getReports()!=null){
            redirectAttributes.addFlashAttribute("deleteShowroomError", true);
        }else
            showroomsController.delete(id);
        return "redirect:/showroom/";
    }
    @RequestMapping(value="/resetShowroomChange")
    public  String resetChange(){
        viewMode = ViewMode.DEFAULT;
        return "redirect:/showroom";
    }
}

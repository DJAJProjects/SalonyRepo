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
import pl.polsl.controller.PrivilegesController;
import pl.polsl.controller.ShowroomsController;
import pl.polsl.controller.WorkersController;
import pl.polsl.model.Dictionary;
import pl.polsl.model.Worker;

import java.sql.Date;
import java.util.List;

/**
 * Created by Dominika Błasiak on 09.04.2016.
 */
@Controller
public class WorkerWebController extends  BaseWebController {

    private ViewMode viewMode;

    @Autowired
    private ShowroomsController showroomsController;
    @Autowired
    private DictionaryController dictionaryController;
    @Autowired
    private WorkersController workersController;
    @Autowired
    private PrivilegesController privilegesController;

    @RequestMapping(value ="/worker")
    public String getWorkers(Model model){
        if(Data.user == null){
            model.asMap().clear();
            model.addAttribute("userNotLoggedIn", true);
        }else if(!privilegesController.getReadPriv("Pracownicy", Data.user)){
            model.asMap().clear();
            model.addAttribute("forbiddenAccess", true);
        }
        else{
            model.addAttribute("workers", workersController.findAll());
        }
        refreshMenuPrivileges(model);
        return "worker";
    }

    @RequestMapping(value ="/deleteWorker/{id}")
    public String deleteWorker(@PathVariable("id")int id){
        workersController.deleteOne(id);
        return "redirect:/worker/";
    }

    @RequestMapping(value ="/addWorker")
    public String addWorker(RedirectAttributes redirectAttributes){
        viewMode = ViewMode.INSERT;
        Worker worker = new Worker();
        redirectAttributes.addFlashAttribute("controlsPanelVisible", true);
        redirectAttributes.addFlashAttribute("controlsDisabled", false);
        redirectAttributes.addFlashAttribute("worker", worker);
        List<Dictionary> positions = dictionaryController.findAllPositions();
        positions.remove(0);
        redirectAttributes.addFlashAttribute("positions", positions);
        redirectAttributes.addFlashAttribute("showrooms", showroomsController.findAll());
        redirectAttributes.addFlashAttribute("controlsLoginVisible", true);
        redirectAttributes.addFlashAttribute("controlsPasswordVisible", true);
        return "redirect:/worker";
    }

    @RequestMapping(value ="/viewWorker/{id}")
    public String displayWorker(RedirectAttributes redirectAttributes, @PathVariable("id")int id) {

        viewMode = ViewMode.VIEW_ALL;

        Worker worker = workersController.findOne(id);
        redirectAttributes.addFlashAttribute("worker", worker);
        redirectAttributes.addFlashAttribute("controlsPanelVisible", true);
        redirectAttributes.addFlashAttribute("controlsDisabled", true);
        redirectAttributes.addFlashAttribute("positionId", worker.getPosition().getId());
        redirectAttributes.addFlashAttribute("showroomId", worker.getShowroom().getId());
        redirectAttributes.addFlashAttribute("positions", dictionaryController.findAllPositions());
        redirectAttributes.addFlashAttribute("showrooms", showroomsController.findAll());
        redirectAttributes.addFlashAttribute("controlsLoginVisible", true);
        redirectAttributes.addFlashAttribute("controlsPasswordVisible", false);
        return "redirect:/worker";
    }

    @RequestMapping(value ="/editWorker/{id}")
    public String editWorker(RedirectAttributes redirectAttributes, @PathVariable("id")int id){
        viewMode = ViewMode.EDIT;
        Worker worker = workersController.findOne(id);
        redirectAttributes.addFlashAttribute("controlsPanelVisible", true);
        redirectAttributes.addFlashAttribute("controlsDisabled", false);
        redirectAttributes.addFlashAttribute("positionId", worker.getPosition().getId());
        if(worker.getShowroom()!=null)
            redirectAttributes.addFlashAttribute("showroomId", worker.getShowroom().getId());
        redirectAttributes.addFlashAttribute("worker", worker);
        List<Dictionary> positions = dictionaryController.findAllPositions();
        positions.remove(0);
        redirectAttributes.addFlashAttribute("positions", positions);
        redirectAttributes.addFlashAttribute("showrooms", showroomsController.findAll());
        redirectAttributes.addFlashAttribute("controlsLoginVisible", false);
        redirectAttributes.addFlashAttribute("controlsPasswordVisible", false);
        return "redirect:/worker";

    }

    @RequestMapping(value ="/acceptModifyWorker", method = RequestMethod.POST)
    public String editWorker(@RequestParam("id") int id,
                               @RequestParam("name") String name,
                               @RequestParam(value = "surname") String surname,
                               @RequestParam(value = "payment") int payment,
                               @RequestParam(value="dateHired") Date dateHired,
                               @RequestParam(value = "position")int position,
                               @RequestParam(value = "showroom")int showroom,
                               @RequestParam(value = "login")String login,
                               @RequestParam(value = "password")String password){
        if(viewMode == ViewMode.EDIT) {
            Worker worker = workersController.updateWorker(id, name,surname,payment, dateHired, position,showroom);
        }
        else if(viewMode==ViewMode.INSERT) {
            Worker worker = workersController.addWorker(name,surname,payment, dateHired, position, showroom, login,password);
        }
        return "redirect:/worker/";
    }
}

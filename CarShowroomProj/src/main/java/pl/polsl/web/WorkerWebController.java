package pl.polsl.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.polsl.ViewMode;
import pl.polsl.controller.DictionaryController;
import pl.polsl.controller.ShowroomsController;
import pl.polsl.controller.WorkersController;
import pl.polsl.model.Dictionary;
import pl.polsl.model.Worker;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dominika Błasiak on 09.04.2016.
 */
@Controller
public class WorkerWebController extends  BaseWebController {

    private ViewMode viewMode;
    private boolean showLogin = false;
    private boolean showPassword = false;

    @Autowired
    private ShowroomsController showroomsController;
    @Autowired
    private DictionaryController dictionaryController;
    @Autowired
    private WorkersController workersController;

    @RequestMapping(value ="/worker")
    public String getWorkers(Model model){
        model.addAttribute("workers", workersController.findAll());
        refreshMenuPrivileges(model);
        return "worker";
    }

    @RequestMapping(value ="/deleteWorker/{id}")
    public String deleteWorker(@PathVariable("id")int id){
        workersController.deleteOne(id);
        return "redirect:/worker/";
    }

    @RequestMapping(value ="/addWorker")
    public String addWorker(Model model){
        viewMode = ViewMode.INSERT;
        Worker worker = new Worker();
        model.addAttribute("controlsPanelVisible", true);
        model.addAttribute("controlsDisabled", false);
        model.addAttribute("worker", worker);
        model.addAttribute("workers", workersController.findAll());
        List<Dictionary> positions = dictionaryController.findAllPositions();
        positions.remove(0);
        model.addAttribute("positions", positions);
        model.addAttribute("showrooms", showroomsController.findAll());
        model.addAttribute("controlsLoginVisible", true);
        model.addAttribute("controlsPasswordVisible", true);
        return "worker";
    }

    @RequestMapping(value ="/viewWorker/{id}")
    public String displayWorker(Model model, @PathVariable("id")int id) {

        viewMode = ViewMode.VIEW_ALL;

        Worker worker = workersController.findOne(id);
        model.addAttribute("worker", worker);
        model.addAttribute("controlsPanelVisible", true);
        model.addAttribute("controlsDisabled", true);
        model.addAttribute("positionId", worker.getPosition().getId());
        model.addAttribute("showroomId", worker.getShowroom().getId());
        model.addAttribute("workers", workersController.findAll());
        model.addAttribute("positions", dictionaryController.findAllPositions());
        model.addAttribute("showrooms", showroomsController.findAll());
        model.addAttribute("controlsLoginVisible", true);
        model.addAttribute("controlsPasswordVisible", false);
        return "worker";
    }

    @RequestMapping(value ="/editWorker/{id}")
    public String editWorker(Model model, @PathVariable("id")int id){
        viewMode = ViewMode.EDIT;
        Worker worker = workersController.findOne(id);
        model.addAttribute("controlsPanelVisible", true);
        model.addAttribute("controlsDisabled", false);
        model.addAttribute("positionId", worker.getPosition().getId());
        if(worker.getShowroom()!=null)
            model.addAttribute("showroomId", worker.getShowroom().getId());
//        else
//            model.addAttribute("showroomId", worker.getShowroom().getId());

        model.addAttribute("worker", worker);
        model.addAttribute("workers", workersController.findAll());
        List<Dictionary> positions = dictionaryController.findAllPositions();
        positions.remove(0);
        model.addAttribute("positions", positions);
        model.addAttribute("showrooms", showroomsController.findAll());
        model.addAttribute("controlsLoginVisible", false);
        model.addAttribute("controlsPasswordVisible", false);
        return "worker";

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

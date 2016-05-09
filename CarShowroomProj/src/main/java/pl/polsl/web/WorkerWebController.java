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
import pl.polsl.model.Worker;

/**
 * Created by Dominika Błasiak on 09.04.2016.
 */
@Controller
public class WorkerWebController {

    private ViewMode viewMode;

    @Autowired
    private ShowroomsController showroomsController;
    @Autowired
    private DictionaryController dictionaryController;
    @Autowired
    private WorkersController workersController;

    @RequestMapping(value ="/worker")
    public String getWorkers(Model model){
        model.addAttribute("workers", workersController.findAll());
        return "worker";
    }

    @RequestMapping(value ="/deleteWorker/{id}")
    public String deleteWorker(@PathVariable("id")int id){
        workersController.delete(id);
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
        model.addAttribute("positions", dictionaryController.findAllPositions());
        model.addAttribute("showrooms", showroomsController.findAll());
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

        return "worker";
    }
//    @RequestMapping(value ="/addWorker", method = RequestMethod.POST)
//    public String addWorker(@RequestParam("name") String name, @RequestParam(value = "surname") String surname, @RequestParam(value = "position")int position, @RequestParam(value = "showroom")int showroom,@RequestParam(value = "login")String login, @RequestParam(value = "password")String password){
//        Worker worker = workersController.addShowroom(name,surname,position, showroom, login,password);
//        addVisible = false;
//        return "redirect:/worker/";
//    }

    @RequestMapping(value ="/editWorker/{id}")
    public String editWorker(Model model, @PathVariable("id")int id){
        viewMode = ViewMode.EDIT;
        Worker worker = workersController.findOne(id);

        model.addAttribute("controlsPanelVisible", true);
        model.addAttribute("controlsDisabled", false);
        model.addAttribute("positionId", worker.getPosition().getId());
        model.addAttribute("showroomId", worker.getShowroom().getId());
        model.addAttribute("worker", worker);
        model.addAttribute("workers", workersController.findAll());
        model.addAttribute("positions", dictionaryController.findAllPositions());
        model.addAttribute("showrooms", showroomsController.findAll());
        return "worker";

    }

    @RequestMapping(value ="/acceptModifyWorker", method = RequestMethod.POST)
    public String editWorker(@RequestParam("id") int id,
                               @RequestParam("name") String name,
                               @RequestParam(value = "surname") String surname,
                               @RequestParam(value = "payment") int payment,
                               @RequestParam(value = "position")int position,
                               @RequestParam(value = "showroom")int showroom,
                               @RequestParam(value = "login")String login,
                               @RequestParam(value = "password")String password){
        if(viewMode == ViewMode.EDIT) {
            Worker worker = workersController.updateWorker(id, name,surname,payment, position,showroom);
        }
        else if(viewMode==ViewMode.INSERT) {
            Worker worker = workersController.addShowroom(name,surname,payment, position, showroom, login,password);
        }
        return "redirect:/worker/";
    }
}

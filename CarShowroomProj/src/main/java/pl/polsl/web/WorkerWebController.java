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
import pl.polsl.model.Worker;

/**
 * Created by Dominika Błasiak on 09.04.2016.
 */
@Controller
public class WorkerWebController {

    private boolean addVisible = false;
    private boolean editVisible = false;
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
        addVisible = true;
        model.addAttribute("workers", workersController.findAll());
        model.addAttribute("positions", dictionaryController.findAllPositions());
        model.addAttribute("showrooms", showroomsController.findAll());
        model.addAttribute("addVisible", addVisible);
        return "worker";
    }

    @RequestMapping(value ="/addWorker", method = RequestMethod.POST)
    public String addWorker(@RequestParam("name") String name, @RequestParam(value = "surname") String surname, @RequestParam(value = "position")int position, @RequestParam(value = "showroom")int showroom,@RequestParam(value = "login")String login, @RequestParam(value = "password")String password){
        Worker worker = workersController.addShowroom(name,surname,position, showroom, login,password);
        addVisible = false;
        return "redirect:/worker/";
    }

    @RequestMapping(value ="/editWorker/{id}")
    public String editShowroom(Model model, @PathVariable("id")int id){
        editVisible=true;
        Worker worker = workersController.findOne(id);
        model.addAttribute("positionId", worker.getPosition().getId());
        model.addAttribute("showroomId", worker.getShowroom().getId());
        model.addAttribute("worker", worker);
        model.addAttribute("workers", workersController.findAll());
        model.addAttribute("positions", dictionaryController.findAllPositions());
        model.addAttribute("showrooms", showroomsController.findAll());
        model.addAttribute("editVisible", editVisible);
        return "worker";

    }

    @RequestMapping(value ="/editWorker", method = RequestMethod.POST)
    public String editShowroom(@RequestParam("id") int id,@RequestParam("name") String name, @RequestParam(value = "surname") String surname, @RequestParam(value = "position")int position, @RequestParam(value = "showroom")int showroom){
        Worker worker = workersController.updateWorker(id, name,surname,position,showroom);
        editVisible = false;
        return "redirect:/worker/";
    }
}

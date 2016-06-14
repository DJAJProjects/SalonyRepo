package pl.polsl.web;

import netscape.security.Privilege;
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
import pl.polsl.controller.*;
import pl.polsl.model.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Dominika Błasiak on 09.04.2016.
 */
@Controller
public class WorkerWebController extends  BaseWebController {

    private Worker currentWorker;
    private ArrayList<Privileges> currentPrivilegesList;
    private ArrayList<Privileges> previousPrivilegesList;
    private boolean differentWorker = false;

    @Autowired
    private ShowroomsController showroomsController;
    @Autowired
    private DictionaryController dictionaryController;
    @Autowired
    private WorkersController workersController;
    @Autowired
    private PrivilegesController privilegesController;
    @Autowired
    private WorkersPrivilegesController workersPrivilegesController;


    @RequestMapping(value ="/worker")
    public String getWorkers(Model model){
            boolean isAdmin = false;

            if (Data.user == null) {
                model.asMap().clear();
                model.addAttribute("userNotLoggedIn", true);
                return "sign_in";
            } else if (!privilegesController.getReadPriv(Data.workerModuleValue, Data.user)) {
                model.asMap().clear();
                model.addAttribute("forbiddenAccess", true);
            } else {
                model.addAttribute("workers", workersController.findWorkersRelatedToWorker(Data.user));
            }
            analisePrivileges(Data.workerModuleValue);
            model.addAttribute("insertEnabled", insertEnabled);
            model.addAttribute("updateEnabled", updateEnabled);
            model.addAttribute("deleteEnabled", deleteEnabled);
            refreshMenuPrivileges(model);
            if(!model.containsAttribute("deleteDirector"))
                model.addAttribute("deleteDirector", false);

            if(Data.user.getPosition().getValue().equals(Data.adminValue)){
                isAdmin = true;
            }

            model.addAttribute("privilegesPanelVisible", isAdmin);

            return "worker";

    }

    @RequestMapping(value ="/deleteWorker/{id}")
    public String deleteWorker(RedirectAttributes redirectAttributes,@PathVariable("id")int id){
        Worker worker = workersController.findOne(id);
        if(worker.getPosition().getId()==Data.directorId && worker.getShowroom()!=null)
            redirectAttributes.addFlashAttribute("deleteDirector", true);
        else
            workersController.deleteOne(id);
        return "redirect:/worker/";
    }

    @RequestMapping(value ="/addWorker")
    public String addWorker(RedirectAttributes redirectAttributes, Model model){
        viewMode = ViewMode.INSERT;
        Worker worker;
        if(!model.containsAttribute("error"))
           worker = new Worker();
        else{
            System.out.println("error przy dodawaniu");
            worker = (Worker)model.asMap().get("worker");
            redirectAttributes.addFlashAttribute("positionId", worker.getPosition().getId());
            redirectAttributes.addFlashAttribute("error", model.asMap().get("error"));
        }

        redirectAttributes.addFlashAttribute("controlsPanelVisible", true);
        redirectAttributes.addFlashAttribute("controlsDisabled", false);
        redirectAttributes.addFlashAttribute("worker", worker);
        redirectAttributes.addFlashAttribute("positions", dictionaryController.findAllPositions().stream()
                .filter(dictionary -> dictionary.getId()!=Data.adminId).collect(Collectors.toList()));
        redirectAttributes.addFlashAttribute("showrooms", showroomsController.findAll());
        redirectAttributes.addFlashAttribute("controlsLoginVisible", true);
        redirectAttributes.addFlashAttribute("controlsPasswordVisible", true);

        if(differentWorker) {
            previousPrivilegesList = new ArrayList<Privileges>();
            currentPrivilegesList = new ArrayList<Privileges>();
        }

        redirectAttributes.addFlashAttribute("choosenPrivileges", currentPrivilegesList);
        //redirectAttributes.addFlashAttribute("privileges", privilegesController.findPrivilegesNotRelatedToWorker(worker));
        return "redirect:/worker";
    }

    @RequestMapping(value ="/viewWorker/{id}")
    public String displayWorker(RedirectAttributes redirectAttributes, @PathVariable("id")int id) {
        viewMode = ViewMode.VIEW_ALL;
        Worker worker = workersController.findOne(id);

        if(currentWorker == null || currentWorker.getId() != worker.getId())
            differentWorker = true;

        currentWorker = worker;

        redirectAttributes.addFlashAttribute("worker", worker);
        redirectAttributes.addFlashAttribute("controlsPanelVisible", true);
        redirectAttributes.addFlashAttribute("controlsDisabled", true);
        redirectAttributes.addFlashAttribute("positionId", worker.getPosition().getId());
        if(worker.getShowroom()!=null)
            redirectAttributes.addFlashAttribute("showroomId", worker.getShowroom().getId());
        redirectAttributes.addFlashAttribute("positions", dictionaryController.findAllPositions());
        redirectAttributes.addFlashAttribute("showrooms", showroomsController.findAll());
        redirectAttributes.addFlashAttribute("controlsLoginVisible", true);
        redirectAttributes.addFlashAttribute("controlsPasswordVisible", false);

        if(differentWorker) {
            previousPrivilegesList = (ArrayList) privilegesController.findPrivilegesOfWorker(worker);
            currentPrivilegesList = (ArrayList) privilegesController.findPrivilegesOfWorker(worker);
        }

        redirectAttributes.addFlashAttribute("choosenPrivileges", currentPrivilegesList);
        redirectAttributes.addFlashAttribute("privileges", privilegesController.findPrivilegesNotRelatedToWorker(worker));

        differentWorker = false;

        return "redirect:/worker";
    }

    @RequestMapping(value ="/editWorker/{id}")
    public String editWorker(RedirectAttributes redirectAttributes, Model model, @PathVariable("id")int id){
        viewMode = ViewMode.EDIT;

        Worker worker;
        if(!model.containsAttribute("error"))
            worker = workersController.findOne(id);
        else{
            worker = (Worker)model.asMap().get("worker");
        }

        if(currentWorker == null || currentWorker.getId() != worker.getId())
            differentWorker = true;

        currentWorker = worker;

        redirectAttributes.addFlashAttribute("controlsPanelVisible", true);
        redirectAttributes.addFlashAttribute("controlsDisabled", false);
        redirectAttributes.addFlashAttribute("positionId", worker.getPosition().getId());
        if(worker.getShowroom()!=null)
            redirectAttributes.addFlashAttribute("showroomId", worker.getShowroom().getId());
        redirectAttributes.addFlashAttribute("worker", worker);
        redirectAttributes.addFlashAttribute("positions",  dictionaryController.findAllPositions().stream()
                .filter(dictionary -> dictionary.getId() != Data.adminId).collect(Collectors.toList()));
        redirectAttributes.addFlashAttribute("showrooms", showroomsController.findAll());
        redirectAttributes.addFlashAttribute("controlsLoginVisible", false);
        redirectAttributes.addFlashAttribute("controlsPasswordVisible", false);
        if(worker.getPosition().getId() == Data.directorId && worker.getShowroom()!=null){
            redirectAttributes.addFlashAttribute("positionDisabled", true);
        }

        if(differentWorker) {
            previousPrivilegesList = (ArrayList) privilegesController.findPrivilegesOfWorker(worker);
            currentPrivilegesList = (ArrayList) privilegesController.findPrivilegesOfWorker(worker);
        }

        redirectAttributes.addFlashAttribute("choosenPrivileges", currentPrivilegesList);
        redirectAttributes.addFlashAttribute("privileges", privilegesController.findPrivilegesNotRelatedToWorker(worker));

        differentWorker = false;

        return "redirect:/worker";

    }

    @RequestMapping(value ="/acceptModifyWorker", method = RequestMethod.POST)
    public String editWorker(RedirectAttributes redirectAttributes, @RequestParam("id") int id,
                               @RequestParam("name") String name,
                               @RequestParam(value = "surname") String surname,
                               @RequestParam(value = "payment") int payment,
                               @RequestParam(value="dateHired") Date dateHired,
                               @RequestParam(value = "position")int position,
                               @RequestParam(value = "showroom")int showroom,
                               @RequestParam(value = "login")String login,
                               @RequestParam(value = "password")String password) {
        differentWorker = true;
        if (viewMode == ViewMode.EDIT) {
            Worker worker = workersController.updateWorker(false, id, name, surname, payment, dateHired, position, showroom);
            if (worker != null) {
                updatePrivileges(worker);
                return "redirect:/worker/";
            }
            else {
                redirectAttributes.addFlashAttribute("error", "Niestety nie udało się edytować użytkownika");
                worker = workersController.updateWorker(true, id, name, surname, payment, dateHired, position, showroom);
                redirectAttributes.addFlashAttribute("worker", worker);
                return "redirect:/editWorker/";
            }

        } else if (viewMode == ViewMode.INSERT) {
            String error = null;
            Worker worker;
            if (!workersController.checkIfLoginIsUnique(login)) {
                error = "Niestety podany login już istnieje w bazie";
            } else if (login.length() < 4) {
                error = "Login powinien zawierać co najmniej 4 znaki";
            } else if (password.length() < 4) {
                error = "Hasło powninno zawierać co najmniej 4 znaki";
            } else if (dictionaryController.findOne(position).getValue().equals("Dyrektor") && (showroom != -1)) {
                error = "Dodawany dyrektor nie powinien mieć przypisanego salonu";
            } else {
                worker = workersController.addWorker(false, name, surname, payment, dateHired, position, showroom, login, password);
                if (worker != null) {
                    updatePrivileges(worker);
                    return "redirect:/worker/";
                }
                else
                    error = "Niestety nie udało się dodać użytkownika do bazy";
            }
            worker = workersController.addWorker(true, name, surname, payment, dateHired, position, showroom, login, password);

            redirectAttributes.addFlashAttribute("error", error);
            redirectAttributes.addFlashAttribute("worker", worker);
            return "redirect:/addWorker/";
        }
        return "redirect:/worker/";
    }

    // DLA UPRAWNIEN

    @RequestMapping(value ="/addPrivilege", method = RequestMethod.POST)
    public String addPrivilege(RedirectAttributes redirectAttributes, @RequestParam("privilege") int privilege) {
        Privileges priv = privilegesController.findOne(privilege);
        currentPrivilegesList.add(priv);
        if(this.viewMode == ViewMode.INSERT)
            return "redirect:/addWorker/";
        else return "redirect:/editWorker/"+currentWorker.getId();
    }

    @RequestMapping(value ="/deleteRelatedPrivilege/{id}")
    public String deleteRelatedPrivilege(@PathVariable("id")int id){
        for(Privileges priv: currentPrivilegesList){
            if(priv.getId() == id) {
                currentPrivilegesList.remove(priv);
                break;
            }
        }
        if(this.viewMode == ViewMode.INSERT)
            return "redirect:/addWorker/";
        else return "redirect:/editWorker/"+currentWorker.getId();
    }

    private boolean updatePrivileges(Worker worker){

        boolean ret = true;

        for(Privileges actPriv: currentPrivilegesList){
            boolean found = false;
            for(Privileges prevPriv: previousPrivilegesList){
                if(prevPriv.getId() == actPriv.getId()){
                    found = true;
                }
            }
            if(!found){
                workersPrivilegesController.addWorkersPrivileges(worker, actPriv);
            }
        }

        for(Privileges prevPriv: previousPrivilegesList){
            boolean found = false;
            for(Privileges actPriv: currentPrivilegesList){
                if(prevPriv.getId() == actPriv.getId()){
                    found = true;
                }
            }
            if(!found){
                WorkersPrivileges target = (workersPrivilegesController.getWorkersPrivilegesByWorkerAndPrivilege(worker, prevPriv)).get(0);
                workersPrivilegesController.deleteWorkersPrivileges(target.getId());
            }
        }
        return ret;
    }
    @RequestMapping(value ="/changePassword")
    public String changePassword(Model model){
        if (Data.user == null) {
            model.asMap().clear();
            model.addAttribute("userNotLoggedIn", true);
            return "sign_in";
        }
        refreshMenuPrivileges(model);
        return "password";
    }
    @RequestMapping(value ="/changePassword", method = RequestMethod.POST)
    public String changePassword(Model model,
                                 @RequestParam(value="oldPassword") String oldPassword,
                                 @RequestParam(value = "password") String newPassword,
                                 @RequestParam(value="confirmPassword") String confirmPassword){
        if(!newPassword.equals(confirmPassword)){
            model.addAttribute("error", "Hasła nie są takie same");
        }else if(!oldPassword.equals(Data.user.getPassword()))
            model.addAttribute("error", "Podano nieprawidłowe hasło");
        else {
            workersController.updatePassword(Data.user.getId(), newPassword);
            model.addAttribute("info", "Zmieniono hasło");
        }
        refreshMenuPrivileges(model);
        return "password";
    }

    @RequestMapping(value="/resetPasswordChange")
    public  String resetPasswordChange(){
        viewMode = ViewMode.DEFAULT;
        return "redirect:/menu";
    }
}

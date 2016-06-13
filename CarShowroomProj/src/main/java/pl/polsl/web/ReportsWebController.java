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
import pl.polsl.controller.*;
import pl.polsl.model.Report;

import java.sql.Date;

/**
 * Created by Kuba on 20.04.2016.
 */
@Controller
public class ReportsWebController extends BaseWebController {


    @Autowired
    private ReportsController reportsController;

    @Autowired
    private ShowroomsController showroomsController;

    @RequestMapping(value ="/reports")
    public String getReports(Model model){

        if(viewMode == ViewMode.DEFAULT){
            model.addAttribute("controlsPanelVisible", false);
        }
        else{
            model.addAttribute("controlsPanelVisible", true);
        }

        model.addAttribute("reports", reportsController.findReportsRelatedToWorker(Data.user));

        refreshMenuPrivileges(model);
        return "reports";
    }

    @RequestMapping(value ="/viewReport/{id}")
    public String viewReport(RedirectAttributes redirectAttributes, @PathVariable("id")int id) {

        viewMode = ViewMode.VIEW_ALL;

        Report report = reportsController.findOne(id);

        redirectAttributes.addFlashAttribute("showroom", report.getShowroom().getId());
        redirectAttributes.addFlashAttribute("report", report);

        redirectAttributes.addFlashAttribute("showrooms", showroomsController.findAll());
        redirectAttributes.addFlashAttribute("controlsDisabled", true);

        return "redirect:/reports";
    }

    @RequestMapping(value ="/addReport")
    public String addReports(RedirectAttributes redirectAttributes){

        viewMode = ViewMode.INSERT;

        Report report = new Report();

        redirectAttributes.addFlashAttribute("report", report);
        redirectAttributes.addFlashAttribute("controlsDisabled", false);
        redirectAttributes.addFlashAttribute("showrooms", showroomsController.findAll());
        return "redirect:/reports";
    }

    @RequestMapping(value = "/deleteReport/{id}")
    public String deleteReport(@PathVariable("id")int id) {
        reportsController.deleteReport(id);
        return "redirect:/reports";
    }

    @RequestMapping(value ="/acceptModifyReport", method = RequestMethod.POST)
    public String acceptModifyReport(@RequestParam(value = "name") String name,
                             @RequestParam(value = "showroom") int showroom,
                             @RequestParam(value = "content") String content,
                             @RequestParam(value = "dateBeggining") String dateBeggining,
                             @RequestParam(value = "dateEnd") String dateEnd ){
        if(viewMode == ViewMode.INSERT){
            Report report = reportsController.addReport(name, showroom, content, Date.valueOf(dateBeggining),Date.valueOf(dateEnd));
            viewMode = ViewMode.VIEW_ALL;
            return "redirect:/viewReport/"+report.getId();
        }
        viewMode = ViewMode.DEFAULT;
        return "redirect:/reports";


    }

}

package pl.polsl.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import pl.polsl.ViewMode;
import pl.polsl.controller.*;
import pl.polsl.model.Contractor;
import pl.polsl.model.Report;

import java.sql.Date;

/**
 * Created by Kuba on 20.04.2016.
 */
@Controller
public class ReportsWebController {

    private ViewMode viewMode;

    @Autowired
    private ReportsController reportsController;

    @Autowired
    private ShowroomsController showroomsController;

    @RequestMapping(value ="/reports")
    public String getReports(Model model){
        model.addAttribute("reports", reportsController.findAllRaports());
        return "reports";
    }

    @RequestMapping(value ="/viewReport/{id}")
    public String viewReport(Model model, @PathVariable("id")int id) {

        viewMode = ViewMode.VIEW_ALL;

        Report report = reportsController.findOne(id);

        model.addAttribute("showroom", report.getShowroom().getId());
        model.addAttribute("report", report);
        model.addAttribute("reports", reportsController.findAllRaports());
        model.addAttribute("showrooms", showroomsController.findAll());
        model.addAttribute("controlsPanelVisible", true);
        model.addAttribute("controlsDisabled", true);

        return "reports";
    }

    @RequestMapping(value ="/addReport")
    public String addReports(Model model){

        viewMode = ViewMode.INSERT;

        Report report = new Report();

        model.addAttribute("report", report);
        model.addAttribute("controlsPanelVisible", true);
        model.addAttribute("controlsDisabled", false);
        model.addAttribute("reports", reportsController.findAllRaports());
        model.addAttribute("showrooms", showroomsController.findAll());
        return "reports";
    }

    @RequestMapping(value ="/acceptModifyReport", method = RequestMethod.POST)
    public String acceptModifyReport(@RequestParam(value = "name") String name,
                             @RequestParam(value = "showroom") int showroom,
                             @RequestParam(value = "content") String content,
                             @RequestParam(value = "dateBeggining") String dateBeggining,
                             @RequestParam(value = "dateEnd") String dateEnd ){
        if(viewMode == ViewMode.INSERT){
            Report report = reportsController.addReport(name, showroom, content, Date.valueOf(dateBeggining),Date.valueOf(dateEnd));
        }
        return "redirect:/reports/";
    }

}

package pl.polsl.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.polsl.controller.ReportsController;
import pl.polsl.model.ReportEntity;


@Controller
public class GreetingController {
    @Autowired
    private ReportsController reportsController;
    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {


        ReportEntity r = reportsController.findReport(1);
        if(r != null){
            model.addAttribute("name", r.getContent());
        }
        else{
            model.addAttribute("name", "dupa");
        }
        return "greeting";
    }

}
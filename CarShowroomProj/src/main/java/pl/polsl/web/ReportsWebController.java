package pl.polsl.web;

import com.itextpdf.text.Document;
import com.itextpdf.text.html.simpleparser.HTMLWorker;
import com.itextpdf.text.pdf.PdfWriter;
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

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
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

    private boolean generatePdfLoop = false;

    private InputStream is;

    @RequestMapping(value ="/reports")
    public String getReports(Model model){

        analisePrivileges("reports");
        model.addAttribute("insertEnabled", true);// hehe

        if(viewMode == ViewMode.DEFAULT){
            model.addAttribute("controlsPanelVisible", false);
        }
        else{
            if(model.containsAttribute("report")) {
                model.addAttribute("controlsPanelVisible", true);
            }
            else{
                viewMode = ViewMode.DEFAULT;
                model.addAttribute("controlsPanelVisible", false);
            }
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
        redirectAttributes.addFlashAttribute("dateBeggining", new Date(report.getDateBeggining().getTime()));
        redirectAttributes.addFlashAttribute("dateEnd", new Date(report.getDateEnd().getTime()));
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
    @RequestMapping(value ="/generateReport2/{id}")
    public String generateReport2(Model model, @PathVariable("id")int id) {
        viewMode = ViewMode.VIEW_ALL;
        System.out.println("JOł" + id);
        Report report = reportsController.findOne(id);
        model.addAttribute("showroom", report.getShowroom().getId());
        //report.getContent().replaceAll("\n","<br/>");
        model.addAttribute("report", report);
        model.addAttribute("dateBeggining", new Date(report.getDateBeggining().getTime()));
        model.addAttribute("dateEnd", new Date(report.getDateEnd().getTime()));
        model.addAttribute("showrooms", showroomsController.findAll());
        model.addAttribute("controlsDisabled", true);
        return "report_generate";
    }
    @RequestMapping(value ="/generateReport/{id}")
    public String generateReport(Model model, @PathVariable("id")int id) {
        viewMode = ViewMode.VIEW_ALL;
        System.out.println("JOł" + id);
        Report report = reportsController.findOne(id);
        model.addAttribute("showroom", report.getShowroom().getId());
        //report.getContent().replaceAll("\n","<br/>");
        model.addAttribute("report", report);
        model.addAttribute("dateBeggining", new Date(report.getDateBeggining().getTime()));
        model.addAttribute("dateEnd", new Date(report.getDateEnd().getTime()));
        model.addAttribute("showrooms", showroomsController.findAll());
        model.addAttribute("controlsDisabled", true);

        URL url = null;
        StringBuffer buffer = null;
        try {
            url = new URL("http://localhost:8080/generateReport2/"+id);

//            if(!generatePdfLoop) {
//                generatePdfLoop = true;
                is = url.openStream();
//            }
        int ptr = 0;
            int counter = 1;
        buffer = new StringBuffer();
        while ((ptr = is.read()) != -1) {
            buffer.append((char)ptr);

          if((char)ptr =='\n'){
              counter++;
               if(counter>24){
                   buffer.append("<br/>");
                }
            }
        }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            System.out.println(buffer.toString());
            String k = buffer.toString();
            OutputStream file = new FileOutputStream(new File("C:\\Users\\Dominika Błasiak\\Desktop\\raport_pdf\\raport_"+id+".pdf"));
            Document document = new Document();
            PdfWriter.getInstance(document, file);
            document.open();
            HTMLWorker htmlWorker = new HTMLWorker(document);
            htmlWorker.parse(new StringReader(k));
            document.close();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "report_generate";
    }

}

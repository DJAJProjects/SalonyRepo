package pl.polsl.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.polsl.ViewMode;
import pl.polsl.controller.ContractsController;
import pl.polsl.controller.InvoiceController;

import java.io.Console;

/**
 * Created by Julia on 2016-04-12.
 */
@Controller
public class InvoiceWebController extends  BaseWebController{

    @Autowired
    private InvoiceController invoiceController;
    private ViewMode viewMode;

    @RequestMapping(value = "/invoices", method = RequestMethod.GET)
    public String getInvoices(Model model) {
        model.addAttribute("invoices",invoiceController.findAll());
        refreshMenuPrivileges(model);
        return "invoices";
    }

    @RequestMapping(value = "/deleteInvoice/{id}", method = RequestMethod.GET)
     public String deleteInvoice(@PathVariable("id")int id) {
        invoiceController.delete(id);
        return "redirect:/invoices";
    }

    /**
     * Created by: Aleksandra Chronowska
     * @param redirectAttributes
     * @param id invoice id
     * @return
     */
    @RequestMapping(value = "/viewInvoice/{id}", method = RequestMethod.GET)
    public String displayInvoice(RedirectAttributes redirectAttributes, @PathVariable("id")int id) {
        viewMode = ViewMode.VIEW_ALL;
        redirectAttributes.addAttribute("invoice", invoiceController.findOne(id));
        redirectAttributes.addAttribute("contract", invoiceController.findOne(id).getContract().getId());
        return "redirect:/invoiceGenerate";
    }


}

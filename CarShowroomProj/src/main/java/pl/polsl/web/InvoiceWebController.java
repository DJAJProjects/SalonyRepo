package pl.polsl.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.polsl.Data;
import pl.polsl.ViewMode;
import pl.polsl.controller.InvoiceController;

/**
 * Invoices web controller class
 * @author Julia Kubieniec
 * @version 1.0
 */
@Controller
public class InvoiceWebController extends  BaseWebController{

    @Autowired
    private InvoiceController invoiceController;
    private ViewMode viewMode;

    /**
     * GET method for main invoices view
     * @author Aleksandra Chronowska
     * @param model actual model
     * @return actual html view
     */
    @RequestMapping(value = "/invoices", method = RequestMethod.GET)
    public String getInvoices(Model model) {
        if (Data.user == null) {
            model.asMap().clear();
            model.addAttribute("userNotLoggedIn", true);
            return "sign_in";
        }
        model.addAttribute("invoices",invoiceController.findInvoices());
        analisePrivileges(Data.invoiceModuleValue);
        model.addAttribute("deleteEnabled", deleteEnabled);
        refreshMenuPrivileges(model);
        return "invoices";
    }

    /**
     * Method deleting clicked invoice
     * @param id current invoice
     * @return redirect for main invoice view
     */
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

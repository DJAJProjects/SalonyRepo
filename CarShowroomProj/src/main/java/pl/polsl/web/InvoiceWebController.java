package pl.polsl.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.polsl.controller.ContractsController;
import pl.polsl.controller.InvoiceController;

import java.io.Console;

/**
 * Created by Julia on 2016-04-12.
 */
@Controller
public class InvoiceWebController {

    @Autowired
    private InvoiceController invoiceController;

    @RequestMapping(value = "/invoices", method = RequestMethod.GET)
    public String getInvoices(Model model) {
        model.addAttribute("invoices",invoiceController.findAll());
        return "invoices";
    }

    @RequestMapping(value = "/deleteInvoice/{id}", method = RequestMethod.GET)
    public String deleteInvoice(@PathVariable("id")int id) {
        invoiceController.delete(id);
        return "redirect:/invoices";
    }

}

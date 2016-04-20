package pl.polsl.controller;

import jersey.repackaged.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.model.Accessory;
import pl.polsl.model.Invoice;
import pl.polsl.repository.AccessoriesRepository;
import pl.polsl.repository.InvoiceRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Date;
import java.util.List;

/**
 * Created by Aleksandra on 2016-04-07.
 */
@Component
@Path("/invoices")
@Produces(MediaType.APPLICATION_JSON)

public class InvoiceController {
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private DictionaryController dictionaryController;
    @Autowired
    ContractsController contractsController;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Invoice> findAll() {
        return Lists.newArrayList(invoiceRepository.findAll());
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Invoice findOne(int id){
        return invoiceRepository.findOne(id);}

    public void delete(int id){
        invoiceRepository.delete(id);
    }

    public Invoice addInvoice(Date dateCreate, Date dateSold, Date paymentDeadline, int paymentForm, int type, int contract) {
        return invoiceRepository.save(new Invoice(dateCreate, dateSold, paymentDeadline,
                dictionaryController.findOne(paymentForm), dictionaryController.findOne(type),
                contractsController.findOne(contract)));
    }

}

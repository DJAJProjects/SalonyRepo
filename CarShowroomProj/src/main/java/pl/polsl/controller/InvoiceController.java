package pl.polsl.controller;

import jersey.repackaged.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.Data;
import pl.polsl.model.Contract;
import pl.polsl.model.Invoice;
import pl.polsl.repository.InvoiceRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    public void edit(int id) {invoiceRepository.save(findOne(id));}
    public Invoice addNew(Contract con, int paymentForm, int type){
        Invoice invoice = new Invoice();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String stringDate= dateFormat.format(new Date());
        java.sql.Date sqlDate=  java.sql.Date.valueOf(stringDate);
        invoice.setDateCreated(sqlDate);
        invoice.setContract(con);
        invoice.setInvoiceType(dictionaryController.findOne(type));
        invoice.setPaymentForm(dictionaryController.findOne(paymentForm));
        return invoiceRepository.save(invoice);
    }
    public List<Invoice> findInvoices(){
        List<Invoice> retList = null;
        if( Data.user.getPosition().getId() == 11)
            retList = invoiceRepository.findForDirector(Data.user.getShowroom().getId());
        else if(Data.user.getPosition().getId() == 10) {
            retList = invoiceRepository.findForWorker(Data.user.getId());
        }
        else if(Data.user.getPosition().getId() == Data.adminId)
            retList =  findAll();
        if(retList == null)retList = new ArrayList<Invoice>();
        return retList;
    }

//    public Invoice addInvoice(Date dateCreate, Date dateSold, Date paymentDeadline, int paymentForm, int type, int contract) {
//        return invoiceRepository.save(new Invoice(dateCreate, dateSold, paymentDeadline,
//                dictionaryController.findOne(paymentForm), dictionaryController.findOne(type),
//                contractsController.findOne(contract)));
//    }

}

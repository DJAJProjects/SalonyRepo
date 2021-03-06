package pl.polsl.controller;

import jersey.repackaged.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.model.Invoice;
import pl.polsl.model.Promotion;
import pl.polsl.repository.InvoiceRepository;
import pl.polsl.repository.PromotionsRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Aleksandra on 2016-04-07.
 */
@Component
@Path("/promotions")
@Produces(MediaType.APPLICATION_JSON)

public class PromotionsController {
    @Autowired
    private PromotionsRepository promotionsRepository;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Promotion> findAll() {
        return Lists.newArrayList(promotionsRepository.findAll());
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Promotion findOne(int id){
        return promotionsRepository.findOne(id);}
}

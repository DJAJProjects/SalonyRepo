package pl.polsl.controller;

import jersey.repackaged.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.model.Contract;
import pl.polsl.repository.AccessoriesRepository;
import pl.polsl.repository.ContractsRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Aleksandra on 2016-04-07.
 */
@Component
@Path("/contracts")
@Produces(MediaType.APPLICATION_JSON)

public class ContractsController {
    @Autowired
    private ContractsRepository contractController;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Contract> findAllContracts() {
        return Lists.newArrayList(contractController.findAll());
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Contract findOne(int id){
        return contractController.findOne(id);}
}

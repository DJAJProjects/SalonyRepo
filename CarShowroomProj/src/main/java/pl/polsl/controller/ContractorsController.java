package pl.polsl.controller;

import jersey.repackaged.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.model.Contract;
import pl.polsl.model.Contractor;
import pl.polsl.repository.ContractorsRepository;
import pl.polsl.repository.ContractsRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Aleksandra on 2016-04-15.
 */
@Component
@Path("/contractors")
@Produces(MediaType.APPLICATION_JSON)
public class ContractorsController {
    @Autowired
    private ContractorsRepository contractorsRepository;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Contractor> findAllContractors() {
        return Lists.newArrayList(contractorsRepository.findAll());
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Contractor findOne(int id){
        return contractorsRepository.findOne(id);}
}

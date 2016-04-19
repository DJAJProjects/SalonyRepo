package pl.polsl.controller;

import jersey.repackaged.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.model.Contract;
import pl.polsl.model.Contractor;
import pl.polsl.repository.AccessoriesRepository;
import pl.polsl.repository.ContractsRepository;
import pl.polsl.web.MainController;

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
    private ContractsRepository contractsRepository;
    @Autowired
    private WorkersController workersController;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Contract> findAllContracts() {
        return Lists.newArrayList(contractsRepository.findAll());
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Contract findOne(int id){
        return contractsRepository.findOne(id);}

    public void delete(int id) {
        contractsRepository.delete(id);
    }
    public Contract edit(Contract con) {
        return contractsRepository.save(con);
    }

    public Contract addNew(Contractor contractor) {
        Contract contract= new Contract();
        //TODO mokeup
        contract.setTotalCost(0);
        MainController.worker= workersController.findOne(1);
        contract.setWorker(MainController.worker);
        contract.setContractor(contractor);
        return contractsRepository.save(contract);
    }
}

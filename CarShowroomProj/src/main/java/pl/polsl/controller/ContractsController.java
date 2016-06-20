package pl.polsl.controller;

import jersey.repackaged.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.Data;
import pl.polsl.model.*;
import pl.polsl.repository.ContractsRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Contract controller class
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
    @Autowired
    private CarsController carsController;
    @Autowired
    private AccessoriesController accessoriesController;
    @Autowired
    private PromotionsController promotionsController;
    @Autowired
    private ContractorsController contractorsController;
    @Autowired
    private InvoiceController invoiceController;

    /**
     * Rest get method
     * @author Aleksadra Chronowska
     * @return list of all contracts
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Contract> findAllContracts() {
        return Lists.newArrayList(contractsRepository.findAll());
    }

    /**
     * Rest method find contract by id
     * @param id
     * @return contractor data by json
     */
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Contract findOne(int id){
        return contractsRepository.findOne(id);}

    /**
     * Method deleting current contract with given id
     * @param id contract id
     */
    public void delete(int id) {
        contractsRepository.delete(id);
    }
    /**
     * Method save object in repository.
     * @author Aleksadra Chronowska
     * @param con contract id
     */
    public Contract edit(Contract con) {
        return contractsRepository.save(con);
    }

    /**
     * Method for finding contracts, which can be visible for actual signin user
     * @return contracts list
     */
    public List<Contract> findContracts(){
        List<Contract> retList = null;
        if( Data.user.getPosition().getId() == 11)
            retList = contractsRepository.findForDirector(Data.user.getShowroom().getId());
        else if(Data.user.getPosition().getId() == 10) {
            retList = contractsRepository.findForWorker(Data.user.getId());
        }
        else if(Data.user.getPosition().getId() == 12) {
            retList = contractsRepository.findForServisman(Data.user);
        }
        else if(Data.user.getPosition().getId() == Data.adminId)
            retList =  findAllContracts();
        if(retList == null)retList = new ArrayList<Contract>();

        return retList;
    }

    /**
     * Method updating current contract
     * @param contract curret contract
     * @param carList saved choosen car list
     * @param accessoryList saved choosen accessory list
     * @param promotionList saved choosen promotion list
     * @param contractor contractor
     * @return edited object
     */
    public Contract updateContract(Contract contract, Set<Car>carList, Set<Accessory>accessoryList,Set<Promotion> promotionList, int contractor){
        final int[] totalCost = {0};
        contract.setCarList(carList);
        contract.setAccessoryList(accessoryList);
        contract.setPromotions(promotionList);
        edit(contract);
        carList.stream().forEach((car) -> {
            car = carsController.findOne(car.getId());
            car.setContract(contract);
            totalCost[0] = totalCost[0] + car.getCost();
            carsController.edit(car.getId());
        });

        accessoryList.stream().forEach((accessory) -> {
            accessory = accessoriesController.findOne(accessory.getId());
            accessory.setContract(contract);
            totalCost[0] = totalCost[0] + accessory.getCost();
            accessoriesController.edit(accessory.getId());
        });

        promotionList.stream().forEach((promotion) -> {
            promotion = promotionsController.findOne(promotion.getId());
            promotion.getContracts().add(contract);
            totalCost[0] = totalCost[0] - (int) totalCost[0] * promotion.getPercValue()/100;
            promotionsController.edit(promotion);
        });

        contract.setTotalCost(totalCost[0]);
        contract.setWorker(Data.user);

        if(contract.getInvoice() != null) {
            Invoice invoice = invoiceController.findOne(contract.getInvoice().getId());
            contract.setInvoice(null);
            invoiceController.delete(invoice.getId());
        }
        if(contractor != 0)
            contract.setContractor(contractorsController.findOne(contractor));

        return contractsRepository.save(contract);
    }

    /**
     * Method creating new contract
     @param carList saved choosen car list
      * @param accessoryList saved choosen accessory list
     * @param promotionList saved choosen promotion list
     * @param contractor contractor
     * @return new contract object
     */
    public Contract addNew(Set<Car>carList, Set<Accessory>accessoryList,Set<Promotion> promotionList, int contractor) {
        Contract contract= new Contract();
        return updateContract(contract, carList, accessoryList, promotionList, contractor);
    }

    /**
     * Method used for order car, create temporaty contract object
     * @param carList saved choosen car list
     * @param accessoryList saved choosen accessory list
     * @param promotionList saved choosen promotion list
     * @return
     */
    public Contract makeTemporaryContract(Set<Car>carList,Set<Promotion>promotionList, Set<Accessory>accessoryList) {
        Contract contract = new Contract();
        contract.setId(findAllContracts().size() + 1);
        contract.setCarList(carList);
        contract.setAccessoryList(accessoryList);
        contract.setPromotions(promotionList);
        return contract;
    }

}
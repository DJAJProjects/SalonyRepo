package pl.polsl.controller;

import jersey.repackaged.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.model.Contractor;
import pl.polsl.model.Worker;
import pl.polsl.repository.ContractorsRepository;
import pl.polsl.repository.DictionaryRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Contractor Controller class
 * @author Jakub Wieczorek
 * @version 1.0
 */
@Component
@Path("/contractors")
@Produces(MediaType.APPLICATION_JSON)

public class ContractorsController {
    @Autowired
    private ContractorsRepository contractorsRepository;

    @Autowired
    private DictionaryRepository dictionaryRepository;

    /**
     * Rest get method
     * @author Aleksadra Chronowska
     * @return list of all contractors
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Contractor> findAllContractors() {
        return Lists.newArrayList(contractorsRepository.findAll());
    }

    /**
     * Rest method find contractor by id
     * @param id
     * @return contractor data by json
     */
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Contractor findOne(int id){
        return contractorsRepository.findOne(id);}

    public Contractor addContractor(String name,
                                    String surname,
                                    String pesel,
                                    String nip,
                                    String regon,
                                    int city,
                                    int country,
                                    String street) {
        return contractorsRepository.save(new Contractor(name, surname, pesel, nip, regon,
                                                    dictionaryRepository.findOne(city),
                                                    dictionaryRepository.findOne(country), street));
    }

    /**
     * Method deleting one contractor from given id
     * @param id contractor id
     */
    public void deleteContractor(int id){
        contractorsRepository.delete(id);
    }

    /**
     * Method for edit contractor object
     * @param id contractor id
     * @param name contractor name
     * @param surname contractor surname
     * @param pesel contractor pesel
     * @param nip contractor nip
     * @param regon contractor regon
     * @param city contractor city
     * @param country contractor country
     * @param street contractor street
     * @return edit contractor object
     */
    public Contractor updateContractor(int id,
                                     String name,
                                     String surname,
                                     String pesel,
                                     String nip,
                                     String regon,
                                     int city,
                                     int country,
                                     String street) {
        Contractor contractor = contractorsRepository.findOne(id);
        contractor.setName(name);
        contractor.setSurname(surname);
        contractor.setStreet(street);
        contractor.setPesel(pesel);
        contractor.setNip(nip);
        contractor.setRegon(regon);
        contractor.setCity(dictionaryRepository.findOne(city));
        contractor.setCountry(dictionaryRepository.findOne(country));
        return  contractorsRepository.save(contractor);
    }

    /**
     * Method finding all contractors related to given worker
     * @param worker current worker
     * @return contractors list
     */
    public List<Contractor> findContractorsRelatedToWorker(Worker worker){
        List<Contractor> retList;
        int positionId = worker.getPosition().getId();
        if( positionId == 11)
            retList = contractorsRepository.findRelatedToDirector(worker);
        else if(positionId == 12)
            retList = contractorsRepository.findRelatedToServiceman(worker);
        else if(positionId == 10)
            retList = contractorsRepository.findRelatedToSalesman(worker);
        else
            retList =  findAllContractors();

        if(retList == null)retList = new ArrayList<Contractor>();

        return retList;
    }


}

package pl.polsl.controller;

import jersey.repackaged.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.model.Contractor;
import pl.polsl.repository.ContractorsRepository;
import pl.polsl.repository.DictionaryRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Kuba on 2016-04-15.
 */
@Component
@Path("/contracts")
@Produces(MediaType.APPLICATION_JSON)

public class ContractorsController {
    @Autowired
    private ContractorsRepository contractorsRepository;

    @Autowired
    private DictionaryRepository dictionaryRepository;

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

    public void deleteContractor(int id){
        contractorsRepository.delete(id);
    }

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
}

package pl.polsl.controller;

import jersey.repackaged.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.model.Dictionary;
import pl.polsl.repository.DictionaryRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Dominika Błasiak on 2016-04-07.
 */
@Component
@Path("/dictionary")
@Produces(MediaType.APPLICATION_JSON)

public class DictionaryController {
    @Autowired
    private DictionaryRepository dictionaryRepository;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Dictionary> findAll() {
        return Lists.newArrayList(dictionaryRepository.findAll());
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Dictionary findOne(int id){
        return dictionaryRepository.findOne(id);}

    public List<Dictionary> findAllCities(){
        return dictionaryRepository.findAllTheSameType("city");
    }

    public List<Dictionary> findAllCountries() {
        return dictionaryRepository.findAllTheSameType("country");
    }

    public List<Dictionary> findAllPositions() { return dictionaryRepository.findAllTheSameType("position");
    }
    public List<Dictionary> findAllPaymentForm() {
        return dictionaryRepository.findAllTheSameType("payment_form");
    }
    public List<Dictionary> findAllInvoiceType() {
        return dictionaryRepository.findAllTheSameType("invoice_type");
    }

    public List<Dictionary> findAllCarName() {
        return dictionaryRepository.findAllTheSameType("car_name");
    }

    public List<Dictionary> findAllAccessories() {
        return dictionaryRepository.findAllTheSameType("accessory");
    }

    public List<Dictionary> findAllService() {
        return dictionaryRepository.findAllTheSameType("service_type");
    }

    public List<String> findAllTypes() { return dictionaryRepository.findAllTypes();
    }

    public Dictionary updateDictionaryValue(int id, String type, String value, String value2) {
        Dictionary dictionaryValue = dictionaryRepository.findOne(id);
        dictionaryValue.setType(type);
        dictionaryValue.setValue(value);
        dictionaryValue.setValue2(value2);
        return dictionaryRepository.save(dictionaryValue);
    }

    public Dictionary addDictionaryValue(int id, String type, String value1, String value2) {
        return dictionaryRepository.save(new Dictionary(id,type,value1,value2));
    }

    public void delete(int id) {
        System.out.println(id);
        dictionaryRepository.delete(id);
    }
}

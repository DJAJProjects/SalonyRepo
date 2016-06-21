package pl.polsl.controller;

import jersey.repackaged.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.Data;
import pl.polsl.model.Dictionary;
import pl.polsl.repository.DictionaryRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Dictionary controller class
 * @author Dominika Błasiak
 * @version 1.0
 */
@Service
@Path("/dictionary")
@Produces(MediaType.APPLICATION_JSON)

public class DictionaryController {
    @Autowired
    private DictionaryRepository dictionaryRepository;
    /**
     * Rest get method
     * @author Aleksadra Chronowska
     * @return list of all dictionary object
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Dictionary> findAll() {
        return Lists.newArrayList(dictionaryRepository.findAll());
    }

    /**
     * Rest method to find dictionary object by id
     * @param id
     * @return dictionary object data by json
     */
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Dictionary findOne(int id){
        return dictionaryRepository.findOne(id);}

    /**
     * Method to find all cities
     * @return cities list
     */
    public List<Dictionary> findAllCities(){
        return dictionaryRepository.findAllTheSameType("city");
    }

    /**
     * Method to find all countries
     * @return countries list
     */
    public List<Dictionary> findAllCountries() {
        return dictionaryRepository.findAllTheSameType("country");
    }

    /**
     * Method to find all positions
     * @return positions list
     */
    public List<Dictionary> findAllPositions() { return dictionaryRepository.findAllTheSameType("position");
    }

    /**
     * Method to find all available payment form
     * @return payment form list
     */
    public List<Dictionary> findAllPaymentForm() {
        return dictionaryRepository.findAllTheSameType("payment_form");
    }

    /**
     * Method to find all available invoice type
     * @return invoice type list
     */
    public List<Dictionary> findAllInvoiceType() {
        return dictionaryRepository.findAllTheSameType("invoice_type");
    }
    /**
     * Method to find all car names
     * @return invoice car name
     */
    public List<Dictionary> findAllCarName() {
        return dictionaryRepository.findAllTheSameType("car_name");
    }

    /**
     * Method to find all accessory names
     * @return invoice accessory name
     */
    public List<Dictionary> findAllAccessories() {
        return dictionaryRepository.findAllTheSameType("accessory");
    }

    /**
     * Method to find all modules
     * @return modules list
     */
    public List<Dictionary> findAllModules() {
        return dictionaryRepository.findAllTheSameType("module");
    }

    /**
     * Method to find all service
     * @return service list
     */
    public List<Dictionary> findAllService() {
        return dictionaryRepository.findAllTheSameType("service_type");
    }

    /**
     * Method find all subservice
     * @return subservice list
     */
    public List<Dictionary> findAllSubservice() {
        return dictionaryRepository.findAllTheSameType("subservice_type");
    }

    /**
     * Method to find all types
     * @return types list
     */
    public List<String> findAllTypes() { return dictionaryRepository.findAllTypes();
    }

    /**
     * Method to edit dictionary object
     * @param id dictionary id
     * @param type dictionary type
     * @param value dictionary value
     * @param value2 alternative dictionary value
     * @param value3 alternative dictionary value
     * @return edited dictionary object
     */
    public Dictionary updateDictionaryValue(int id, String type, String value, String value2, String value3) {
        Dictionary dictionaryValue = dictionaryRepository.findOne(id);
        dictionaryValue.setType(type);
        dictionaryValue.setValue(value);
        dictionaryValue.setValue2(value2);
        dictionaryValue.setValue3(value3);
        return dictionaryRepository.save(dictionaryValue);
    }
    /**
     *  MEthod adding dictionary object
     * @param id dictionary id
     * @param type dictionary type
     * @param value1 dictionary value
     * @param value2 dictionary value
     * @param value3 dictionary value
     * @return edited dictionary object
     */
    public Dictionary addDictionaryValue(int id, String type, String value1, String value2, String value3) {
        return dictionaryRepository.save(new Dictionary(id,type,value1,value2, value3));
    }

    /**
     * Method to delete object from table of dictionary objects
     * @param id given dictionary object
     */
    public void delete(int id) {
        dictionaryRepository.delete(id);
    }

    /**
     * Method to find admin id
     * @return admin id
     */
    public int findAdmin() {
        return dictionaryRepository.findPositionId(Data.adminValue);
    }

    /**
     * Method to find director id
     * @return position id
     */
    public int findDirector() {
        return dictionaryRepository.findPositionId(Data.directorValue);
    }
}

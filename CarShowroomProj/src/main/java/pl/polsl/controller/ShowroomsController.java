package pl.polsl.controller;

import jersey.repackaged.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.Data;
import pl.polsl.model.Showroom;
import pl.polsl.model.Worker;
import pl.polsl.repository.ShowroomsRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

/**
 * Showroom Controller class
 * @author Dominika Błasiak
 * @version 1.0
 */
@Service
@Path("/showrooms")
@Produces(MediaType.APPLICATION_JSON)
public class ShowroomsController {

    @Autowired
    private ShowroomsRepository showroomsRepository;

    @Autowired
    private DictionaryController dictionaryController;

    @Autowired
    private WorkersController workersController;
    /**
     * Rest get method
     * @author Aleksadra Chronowska
     * @return list of all showrooms
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Showroom> findAll() {
        return Lists.newArrayList(showroomsRepository.findAll());
    }

    /**
     * Rest method to find showroom by id
     * @param id
     * @return showroom data by json
     * @author Aleksadra Chronowska
     */
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Showroom findOne(int id){
        return showroomsRepository.findOne(id);
    }

    /**
     * Method to delete showroom from given id
     * @param id showroom id to remove
     */
    public void delete(int id){
        showroomsRepository.delete(id);
    }

    /**
     *Method to add showroom to database
     * @param name showroom name
     * @param street showroom street
     * @param city showroom city
     * @param country showroom country
     * @param director showroom director
     * @return created showroom
     */
    public Showroom addShowroom(String name, String street, int city, int country, int director) {
        Showroom showroom = showroomsRepository.save(new Showroom(name, street, dictionaryController.findOne(city), dictionaryController.findOne(country),workersController.findOne(director)));
        workersController.updateWorker(director,showroom.getId());
        return showroom;
    }

    /**
     * Method to edit showroom in database
     * @param id showroom id
     * @param name showroom name
     * @param street showroom street
     * @param city showroom city
     * @param country showroom country
     * @param director showroom director
     * @return edited showroom
     */
    public Showroom updateShowroom(int id, String name, String street, int city, int country, int director) {
        Showroom showroom = showroomsRepository.findOne(id);
        showroom.setName(name);
        showroom.setStreet(street);
        showroom.setCity(dictionaryController.findOne(city));
        showroom.setCountry(dictionaryController.findOne(country));
        showroom.setDirector(workersController.findOne(director));
        return  showroomsRepository.save(showroom);
    }

    /**
     * Method gets visible showrooms for actual user
     * @param worker actual user
     * @return list of visible showrooms
     */
    public List<Showroom> findShowroomsRelatedToWorker(Worker worker) {
        List<Showroom> retList = null;
        if (worker.getPosition().getId() == Data.directorId) {
            retList = new ArrayList<>();
            retList.add(showroomsRepository.findOne(worker.getShowroom().getId()));
        }

        else if(worker.getPosition().getId() == Data.adminId)
            retList =  findAll();
        if(retList == null)retList = new ArrayList<>();

        return retList;
    }
}

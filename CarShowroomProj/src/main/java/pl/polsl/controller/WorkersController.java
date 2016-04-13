package pl.polsl.controller;

import jersey.repackaged.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.model.Worker;
import pl.polsl.repository.WorkersRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;


/**
 * Created by Dominika Błasiak on 07.04.2016.
 */

@Component
@Path("/worksers")
@Produces(MediaType.APPLICATION_JSON)
public class WorkersController {

    @Autowired
    private WorkersRepository workersRepository;

    @Autowired
    private DictionaryController dictionaryController;

    @Autowired
    private ShowroomsController showroomsController;


    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Worker> findAll() {
        return Lists.newArrayList(workersRepository.findAll());
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Worker findOne(int id){
        return workersRepository.findOne(id);
    }

    public void delete(int id){
        workersRepository.delete(id);
    }

    //TODO napisać metodę która zwróci wszsytkich kierowników, którzy nie maja dodanego zakładu
    public List<Worker> findAllFreeDirectors() {
        return (List)workersRepository.findAll();
    }

    public Worker addShowroom(String name, String surname, int position, int showroom) {
        return workersRepository.save(new Worker(name,surname,dictionaryController.findOne(position),showroomsController.findOne(showroom)));
    }

    public Worker updateWorker(int id, String name, String surname, int position, int showroom) {
        Worker worker = workersRepository.findOne(id);
        worker.setName(name);
        worker.setSurname(surname);
        worker.setPosition(dictionaryController.findOne(position));
        worker.setShowroom(showroomsController.findOne(showroom));
        return workersRepository.save(worker);
    }
}

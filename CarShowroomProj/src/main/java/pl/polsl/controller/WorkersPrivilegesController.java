package pl.polsl.controller;

import jersey.repackaged.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.model.Accessory;
import pl.polsl.model.Privileges;
import pl.polsl.model.Worker;
import pl.polsl.model.WorkersPrivileges;
import pl.polsl.repository.AccessoriesRepository;
import pl.polsl.repository.DictionaryRepository;
import pl.polsl.repository.WorkersPrivilegesRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Kuba on 2016-04-07.
 */
@Component
@Path("/accessories")
@Produces(MediaType.APPLICATION_JSON)

public class WorkersPrivilegesController {
    @Autowired
    private WorkersPrivilegesRepository workersPrivilegesRepository;
    @Autowired
    private DictionaryRepository dictionaryRepository;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<WorkersPrivileges> findAll() {
        return Lists.newArrayList(workersPrivilegesRepository.findAll());
    }

  /*  @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Accessory findOne(int id){
        return workersPrivilegesRepository.findOne(id);}

    public void edit(int id){
        workersPrivilegesRepository.save(findOne(id));
    }*/

    public void deleteWorkersPrivileges(int id) {
        workersPrivilegesRepository.delete(id);
    }

    public WorkersPrivileges addWorkersPrivileges(Worker worker, Privileges priv) {
        return workersPrivilegesRepository.save(new WorkersPrivileges(priv, worker));
    }

/*    public Accessory editAccessory(int id, int idName, int cost) {

        Accessory accessory = accessoriesRepository.findOne(id);

        accessory.setAccessory(dictionaryRepository.findOne(idName));
        accessory.setCost(cost);
        return accessoriesRepository.save(accessory);
    }*/

}

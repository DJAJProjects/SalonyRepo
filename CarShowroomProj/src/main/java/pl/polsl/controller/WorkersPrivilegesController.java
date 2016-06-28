package pl.polsl.controller;

import jersey.repackaged.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.model.Privileges;
import pl.polsl.model.Worker;
import pl.polsl.model.WorkersPrivileges;
import pl.polsl.repository.DictionaryRepository;
import pl.polsl.repository.WorkersPrivilegesRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Worker privilages controller
 * @author Jakub Wieczorek
 * @version 1.0
 */
@Component
@Path("/workers_privileges")
@Produces(MediaType.APPLICATION_JSON)

public class WorkersPrivilegesController {
    @Autowired
    private WorkersPrivilegesRepository workersPrivilegesRepository;
    @Autowired
    private DictionaryRepository dictionaryRepository;
    /**
     * Rest get method
     * @author Aleksadra Chronowska
     * @return list of all workers
     */

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

    /**
     * Method deleting worker privilages
     * @param id id
     */
    public void deleteWorkersPrivileges(int id) {
        workersPrivilegesRepository.delete(id);
    }

    /**
     * Metohd adding worker privilages
     * @param worker worker
     * @param priv privilages
     * @return new object
     */
    public WorkersPrivileges addWorkersPrivileges(Worker worker, Privileges priv) {
        return workersPrivilegesRepository.save(new WorkersPrivileges(priv, worker));
    }

    /**
     * Method finding worker nad privilages
     * @param worker worker
     * @param priv privilages
     * @return list of worker and privilages
     */
    public List<WorkersPrivileges> getWorkersPrivilegesByWorkerAndPrivilege(Worker worker, Privileges priv){
        return workersPrivilegesRepository.getWorkersPrivilegesByWorkerAndPrivilege(worker, priv);
    }

/*    public Accessory editAccessory(int id, int idName, int cost) {

        Accessory accessory = accessoriesRepository.findOne(id);

        accessory.setAccessory(dictionaryRepository.findOne(idName));
        accessory.setCost(cost);
        return accessoriesRepository.save(accessory);
    }*/

}

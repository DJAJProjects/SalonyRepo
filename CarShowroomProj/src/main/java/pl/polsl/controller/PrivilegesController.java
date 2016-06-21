package pl.polsl.controller;

import jersey.repackaged.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.model.Privileges;
import pl.polsl.model.Worker;
import pl.polsl.model.WorkersPrivileges;
import pl.polsl.repository.PrivilegesRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Privileges Controller class
 * @author Jakub Wieczorek
 * @version 1.0
 */
@Component
@Path("/privileges")
@Produces(MediaType.APPLICATION_JSON)

public class PrivilegesController {

    @Autowired
    private DictionaryController dictionaryController;

    @Autowired
    private PrivilegesRepository privilegesRepository;
    /**
     * Rest get method
     * @author Aleksadra Chronowska
     * @return list of all priviliges
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Privileges> findAll() {
        return Lists.newArrayList(privilegesRepository.findAll());
    }
    /**
     * Rest method find priviliges by id
     * @param id
     * @return priviliges data by json
     */
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Privileges findOne(int id){
        Privileges priv = privilegesRepository.findOne(id);
        return priv;
    }

    /**
     * Method adding new privieges
     * @param name privieges name
     * @param module privieges module
     * @param read read privieges
     * @param insert insert privieges
     * @param update update privieges
     * @param delete delte privieges
     * @return new object
     */
    public Privileges addPrivilege(String name, int module, boolean read,
                                boolean insert, boolean update, boolean delete) {
        return privilegesRepository.save(new Privileges(name,dictionaryController.findOne(module),
                                                        read, insert, update, delete));
    }

    /**
     * Method edit  privieges
     * @param name privieges name
     * @param module privieges module
     * @param read read privieges
     * @param insert insert privieges
     * @param update update privieges
     * @param delete delte privieges
     * @return edited object
     */
    public Privileges updatePrivilege(int id, String name, int module, boolean read,
                                   boolean insert, boolean update, boolean delete) {
        Privileges priv = privilegesRepository.findOne(id);
        priv.setName(name);
        priv.setModule(dictionaryController.findOne(module));
        priv.setReadPriv(read);
        priv.setInsertPriv(insert);
        priv.setUpdatePriv(update);
        priv.setDeletePriv(delete);
        return privilegesRepository.save(priv);
    }

    /**
     * Method giving insert prvileges for worker to proper module
     * @param moduleName module name
     * @param worker worker
     * @return true if operation ended successfully, false if not
     */
    public boolean getInsertPriv(String moduleName, Worker worker){
        List<WorkersPrivileges> result = privilegesRepository.getInsertPriv(moduleName, worker);
        if(result == null || result.size() == 0) return false;
        else return true;
    }

    /**
     * Method giving delete prvileges for worker to proper module
     * @param moduleName module name
     * @param worker worker
     * @return true if operation ended successfully, false if not
     */
    public boolean getDeletePriv(String moduleName, Worker worker){
        List<WorkersPrivileges> result = privilegesRepository.getDeletePriv(moduleName, worker);
        if(result == null || result.size() == 0) return false;
        else return true;
    }

    /**
     * Method giving update prvileges for worker to proper module
     * @param moduleName module name
     * @param worker worker
     * @return true if operation ended successfully, false if not
     */
    public boolean getUpdatePriv(String moduleName, Worker worker){
        List<WorkersPrivileges> result = privilegesRepository.getUpdatePriv(moduleName, worker);
        if(result == null || result.size() == 0) return false;
        else return true;
    }
    /**
     * Method giving read prvileges for worker to proper module
     * @param moduleName module name
     * @param worker worker
     * @return true if operation ended successfully, false if not
     */
    public boolean getReadPriv(String moduleName, Worker worker){
        List<WorkersPrivileges> result = privilegesRepository.getReadPriv(moduleName, worker);
        if(result == null || result.size() == 0) return false;
        else return true;
    }
    /**
     * Method finding  prvileges for worker
     * @param worker worker
     * @return privilages list
     */
    public List<Privileges> findPrivilegesOfWorker(Worker worker){
        List<Privileges> ret =  privilegesRepository.getPrivsForWorker(worker);
        return ret;
    }
    /**
     * Method finding  prvileges  is not for worker
     * @param worker worker
     * @return privilages list
     */
    public List<Privileges> findPrivilegesNotRelatedToWorker(Worker worker){
        List<Privileges> ret =  privilegesRepository.getUnrelatedPrivsForWorker(worker);
        return ret;
    }



}

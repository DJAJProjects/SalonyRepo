package pl.polsl.controller;

import jersey.repackaged.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.Data;
import pl.polsl.model.Privileges;
import pl.polsl.model.Worker;
import pl.polsl.repository.WorkersRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * Workers Controller class
 * @author Dominika Błasiak
 * @version 1.0
 */
@Service
@Path("/worksers")
@Produces(MediaType.APPLICATION_JSON)
public class WorkersController {

    @Autowired
    private WorkersRepository workersRepository;

    @Autowired
    private PrivilegesController privilegesController;

    @Autowired
    private WorkersPrivilegesController workersPrivilegesController;

    @Autowired
    private DictionaryController dictionaryController;

    @Autowired
    private ShowroomsController showroomsController;
    /**
     * Rest get method
     * @author Aleksadra Chronowska
     * @return list of all workers
     */

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Worker> findAll() {
        return Lists.newArrayList(workersRepository.findAll());
    }
    /**
     * Rest method find worker by id
     * @param id
     * @return worker data by json
     */
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Worker findOne(int id){
        return workersRepository.findOne(id);
    }
    /**
     * Method deleting worker from given id
     * @param id worker id
     */
    public void deleteOne(int id){
        workersRepository.delete(id);
    }

    /**
     * Method findind free directors
     * @return list of free workers -directors
     */
    public List<Worker> findAllFreeDirectors() {
        return (List)workersRepository.findAllOneTypeWithoutShowroom(Data.directorId);
    }

<<<<<<< HEAD
    public boolean updatePassword(Integer userID, String pass){
        Worker worker = findOne(userID);
        worker.setPassword(pass);
        return workersRepository.save(worker)!=null;
=======
    /**
     * Method edit password
     * @param userID
     * @param pass
     */
    public void updatePassword(Integer userID, String pass){

>>>>>>> origin/develeop
    }

    /**
     * Method adding a new worker to database
     * @param error error
     * @param name worker name
     * @param surname worker surname
     * @param payment worker payment
     * @param dateHired worker data hired
     * @param position worker position
     * @param showroom worker showroom
     * @param login worker login
     * @param password worker password
     * @return new object
     */
    public Worker addWorker(boolean error,
                            String name,
                            String surname,
                            int payment,
                            Date dateHired,
                            int position,
                            int showroom,
                            String login,
                            String password) {
        MessageDigest md = null;
        Worker retWorker = null;
        try {
            md = MessageDigest.getInstance("SHA");
            String data = password;
            byte[] dataDigest = md.digest(data.getBytes());
            Worker worker = new Worker(name,surname,payment, dateHired,dictionaryController.findOne(position),showroomsController.findOne(showroom),login, password);
            if(error)
                retWorker = worker;
            else
                retWorker = workersRepository.save(worker);

            //Dodawanie domyslnych uprawnien
            Integer[] privKeys = null;
            String posValue = retWorker.getPosition().getValue();

            if( posValue.equals(Data.servicemanValue))
                privKeys = Data.defServicemanPrivKeys;
            else if( posValue.equals(Data.salesmanValue))
                privKeys = Data.defSalesmanPrivKeys;
            else if( posValue.equals(Data.directorValue))
                privKeys = Data.defDirectorPrivKeys;
            else if( posValue.equals(Data.adminValue))
                privKeys = Data.defAdminPrivKeys;
            else return retWorker;

            for(int privKey : privKeys){
                Privileges priv = privilegesController.findOne(privKey);
                workersPrivilegesController.addWorkersPrivileges(retWorker, priv);
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        return retWorker;
    }
    /**
     * Method updating a new worker
     * @param error error
     * @param name worker name
     * @param surname worker surname
     * @param payment worker payment
     * @param dateHired worker data hired
     * @param position worker position
     * @param showroom worker showroom

     * @return edited object
     */
    public Worker updateWorker(boolean error,
                               int id,
                               String name,
                               String surname,
                               int payment,
                               Date dateHired,
                               int position,
                               int showroom) {
        Worker worker = workersRepository.findOne(id);
        worker.setName(name);
        worker.setSurname(surname);
        worker.setPayment(payment);
        worker.setDateHired(dateHired);
        worker.setPosition(dictionaryController.findOne(position));
        worker.setShowroom(showroomsController.findOne(showroom));
        if(error)
            return worker;
        return workersRepository.save(worker);
    }

    /**
     * Method find one worker from current data
     * @param login worker login
     * @param password worker password
     * @return worker
     */
    public Worker findOne(String login, String password) {
        return workersRepository.findOne(login, password);
    }

    /**
     * Method finding all of serviceman
     * @return serviceman list
     */
    public List<Worker> findAllServicemans() {
        return (List)workersRepository.findAllServicemans(12);
    }

    /**
     * Method check if login is unique
     * @param login new login
     * @return null - is login is unique
     */
    public boolean checkIfLoginIsUnique(String login) {
        return workersRepository.getWorkerByLogin(login)==null;
    }

    /**
     * Method updating worker
     * @param idWorker worker id
     * @param idShowroom worker showroom id
     * @return worker edited object
     */
    public Worker updateWorker(int idWorker, int idShowroom) {
        Worker worker = workersRepository.findOne(idWorker);
        worker.setShowroom(showroomsController.findOne(idShowroom));
        return workersRepository.save(worker);
    }

    /**
     * Method gets visible workers for actual user
     * @param worker actual user
     * @return list of visible workers
     */
    public List<Worker> findWorkersRelatedToWorker(Worker worker){
        List<Worker> retList = null;
        if( worker.getPosition().getId() == Data.directorId)
            retList = workersRepository.findRelatedToDirector(worker.getShowroom());
        else if(worker.getPosition().getId() == Data.adminId)
            retList =  findAll().stream().filter(worker1 -> worker1.getPosition().getId()!=Data.adminId).collect(Collectors.toList());
        if(retList == null)retList = new ArrayList<>();

        return retList;
    }

    /**
     * Method finding privilages for worker
     * @param worker worker
     * @return list of pirvilages
     */
    public List<Privileges> findPrivilegesOfWorker(Worker worker) {
        return privilegesController.findPrivilegesOfWorker(worker);
    }
}

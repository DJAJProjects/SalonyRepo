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
@Path("/workers")
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
     * Method to find free directors
     * @return list of free directors without any showroom
     */
    public List<Worker> findAllFreeDirectors() {
        return (List)workersRepository.findAllOneTypeWithoutShowroom(Data.directorId);
    }

    /**
     * Method to change user's password
     * @param userID user id to change password
     * @param pass new password
     * @return boolean true - if changing ends success
     */
    public boolean updatePassword(Integer userID, String pass){
        Worker worker = findOne(userID);
        worker.setPassword(pass);
        return workersRepository.save(worker)!=null;
    }

    /**
     * Method to add a new worker to database or only create worker
     * @param error error if error equals true, method ony create worker (method doesn't save in database)
     * @param name worker name
     * @param surname worker surname
     * @param payment worker payment
     * @param dateHired worker data hired
     * @param position worker position
     * @param showroom worker showroom
     * @param login worker login
     * @param password worker password
     * @return new created object
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
            String newPass = new String(dataDigest);
            Worker worker = new Worker(name,surname,payment, dateHired,dictionaryController.findOne(position),showroomsController.findOne(showroom),login, newPass);
            if(error)
                retWorker = worker;
            else {
                retWorker = workersRepository.save(worker);

                //Dodawanie domyslnych uprawnien
                Integer[] privKeys = null;
                String posValue = retWorker.getPosition().getValue();

                if (posValue.equals(Data.servicemanValue))
                    privKeys = Data.defServicemanPrivKeys;
                else if (posValue.equals(Data.salesmanValue))
                    privKeys = Data.defSalesmanPrivKeys;
                else if (posValue.equals(Data.directorValue))
                    privKeys = Data.defDirectorPrivKeys;
                else if (posValue.equals(Data.adminValue))
                    privKeys = Data.defAdminPrivKeys;
                else return retWorker;

                for (int privKey : privKeys) {
                    Privileges priv = privilegesController.findOne(privKey);
                    workersPrivilegesController.addWorkersPrivileges(retWorker, priv);
                }
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        return retWorker;
    }
    /**
     * Method to update worker in database or only in application
     * @param error error if error equals true, method ony update worker (method doesn't save any change in database)
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
     * Method to find one worker from current data. Method is needed to sing in
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
     * @author Julia Kubieniec
     */
    public List<Worker> findAllServicemans() {
        return (List)workersRepository.findAllServicemans(12);
    }

    /**
     * Method to check if login is unique (doestn.y extist in database)
     * @param login new login
     * @return null - is login is unique
     */
    public boolean checkIfLoginIsUnique(String login) {
        return workersRepository.getWorkerByLogin(login)==null;
    }

    /**
     * Method to update worker position after change showroom director in showroom module
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
     * Method to find privilages for worker
     * @param worker worker
     * @return list of pirvilages
     * @author Jakub Wieczorek
     */
    public List<Privileges> findPrivilegesOfWorker(Worker worker) {
        return privilegesController.findPrivilegesOfWorker(worker);
    }
}

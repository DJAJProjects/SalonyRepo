package pl.polsl.controller;

import jersey.repackaged.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.polsl.Data;
import pl.polsl.model.Contractor;
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
 * Created by Dominika Błasiak on 07.04.2016.
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

    public void deleteOne(int id){
        workersRepository.delete(id);
    }

    public List<Worker> findAllFreeDirectors() {
        return (List)workersRepository.findAllOneType(11);
    }

    public Worker addWorker(boolean error, String name, String surname, int payment, Date dateHired, int position, int showroom, String login, String password) {
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

    public Worker updateWorker(boolean error, int id, String name, String surname, int payment, Date dateHired, int position, int showroom) {
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

    public Worker findOne(String login, String password) {
        return workersRepository.findOne(login, password);
    }

    public List<Worker> findAllServicemans() {
        return (List)workersRepository.findAllServicemans(12);
    }

    public boolean checkIfLoginIsUnique(String login) {
        return workersRepository.getWorkerByLogin(login)==null;
    }

    public Worker updateWorker(int idWorker, int idShowroom) {
        Worker worker = workersRepository.findOne(idWorker);
        worker.setShowroom(showroomsController.findOne(idShowroom));
//        if(error)
//            return worker;
        return workersRepository.save(worker);
    }

    public List<Worker> findContractorsRelatedToWorker(Worker worker){
        List<Worker> retList = null;
        if( worker.getPosition().getId() == Data.directorId)
            retList = workersRepository.findRelatedToDirector(worker.getShowroom());
        else if(worker.getPosition().getId() == Data.adminId)
            retList =  findAll().stream().filter(worker1 -> worker1.getPosition().getId()!=Data.adminId).collect(Collectors.toList());

        if(retList == null)retList = new ArrayList<Worker>();

        return retList;
    }
}

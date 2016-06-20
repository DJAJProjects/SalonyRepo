package pl.polsl.controller;


import jersey.repackaged.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.model.Accessory;
import pl.polsl.model.Service;
import pl.polsl.repository.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Date;
import java.util.List;

/**
 * Services Controller class
 * @author Julia Kubieniec
 * @version 1.0
 */
@Component
@Path("/services")
@Produces(MediaType.APPLICATION_JSON)
public class ServicesController {

    @Autowired
    private ServicesRepository servicesRepository;
    @Autowired
    private DictionaryRepository dictionaryRepository;
    @Autowired
    private WorkersRepository workersRepository;
    @Autowired
    private CarsRepository carsRepository;
    @Autowired
    private AccessoriesRepository accessoriesRepository;
    /**
     * Rest get method
     * @author Aleksadra Chronowska
     * @return list of all services
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Service> findAll() {
        return Lists.newArrayList(servicesRepository.findAll());
    }

    /**
     * Rest method find service by id
     * @param id
     * @return service data by json
     */
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Service findOne(int id) {
        return servicesRepository.findOne(id);
    }

    /**
     * Method save object in repository.
     * @param id service id
     */
    public void edit(int id) {
        servicesRepository.save(findOne(id));
    }

    /**
     * Method deleting service from proper id
     * @param id service id
     */
    public void deleteService(int id) {
        servicesRepository.delete(id);
    }

    /**
     * Method adding new object to database
     * @param type service type
     * @param idServiceman serviceman id
     * @param idCar car, which is serviced
     * @param idAccessory accessory, which is servieced
     * @param idSubservice subservice id
     * @param cost service cost
     * @param dateConducted date conducted
     * @return new object
     */
    public Service addService(int type, int idServiceman, int idCar, int idAccessory, int idSubservice, int cost, Date dateConducted) {

        if(idAccessory != 0) {
            Service service = new Service();
            service.setServiceType(dictionaryRepository.findOne(type));
            service.setServiceman(workersRepository.findOne(idServiceman));
            service.setCar(carsRepository.findOne(idCar));
            service.setAccessory(accessoriesRepository.findOne(idAccessory));
            Accessory accessory = accessoriesRepository.findOne(idAccessory);
            accessory.setCar(carsRepository.findOne(idCar));
            accessoriesRepository.save(accessory);
            service.setCost(cost);
            service.setDateConducted(dateConducted);
            return servicesRepository.save(service);
        } else {
            return servicesRepository.save(new Service(dictionaryRepository.findOne(type), workersRepository.findOne(idServiceman),
                    carsRepository.findOne(idCar),dictionaryRepository.findOne(idSubservice),cost,dateConducted));
        }
    }

    /**
     * Method updating  object to database
     * @param type service type
     * @param idServiceman serviceman id
     * @param idCar car, which is serviced
     * @param idAccessory accessory, which is servieced
     * @param idSubservice subservice id
     * @param cost service cost
     * @param dateConducted date conducted
     * @return edited object
     */
    public Service editService(int id, int type, int idServiceman, int idCar, int idAccessory, int idSubservice, int cost, Date dateConducted, int idPreviousAccessory) {
        Service service = servicesRepository.findOne(id);
        Accessory accessory;
        service.setServiceType(dictionaryRepository.findOne(type));
        service.setServiceman(workersRepository.findOne(idServiceman));
        service.setCar(carsRepository.findOne(idCar));
        if(idAccessory != 0) {
            service.setAccessory(accessoriesRepository.findOne(idAccessory));
            accessory = accessoriesRepository.findOne(idAccessory);
            accessory.setCar(carsRepository.findOne(idCar));
            accessoriesRepository.save(accessory);
        } else {
            service.setSubserviceType(dictionaryRepository.findOne(idSubservice));
        }
        if(idPreviousAccessory != 0) {
            accessory = accessoriesRepository.findOne(idPreviousAccessory);
            accessory.setCar(null);
            accessoriesRepository.save(accessory);
        }
        service.setCost(cost);
        service.setDateConducted(dateConducted);

        return servicesRepository.save(service);
    }
}


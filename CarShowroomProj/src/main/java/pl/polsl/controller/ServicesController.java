package pl.polsl.controller;


import jersey.repackaged.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.model.Service;
import pl.polsl.repository.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Date;
import java.util.List;

/**
 * Created by Julia on 2016-04-28.
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

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Service> findAll() {
        return Lists.newArrayList(servicesRepository.findAll());
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Service findOne(int id) {
        return servicesRepository.findOne(id);
    }

    public void edit(int id) {
        servicesRepository.save(findOne(id));
    }

    public void deleteService(int id) {
        servicesRepository.delete(id);
    }

    public Service addService(int type, int idServiceman, int idCar, int idAccessory, int idSubservice, int cost, Date dateConducted) {

        if(idAccessory != 0) {
            return servicesRepository.save(new Service(dictionaryRepository.findOne(type), workersRepository.findOne(idServiceman),
                    carsRepository.findOne(idCar),accessoriesRepository.findOne(idAccessory),cost,dateConducted));
        } else {
            return servicesRepository.save(new Service(dictionaryRepository.findOne(type), workersRepository.findOne(idServiceman),
                    carsRepository.findOne(idCar),dictionaryRepository.findOne(idSubservice),cost,dateConducted));
        }
    }

    public Service editService(int id, int type, int idServiceman, int idCar, int idAccessory, int idSubservice, int cost, Date dateConducted) {
        Service service = servicesRepository.findOne(id);

        service.setServiceType(dictionaryRepository.findOne(type));
        service.setServiceman(workersRepository.findOne(idServiceman));
        service.setCar(carsRepository.findOne(idCar));
        if(idAccessory != 0) {
            service.setAccessory(accessoriesRepository.findOne(idAccessory));
        } else {
            service.setSubserviceType(dictionaryRepository.findOne(idSubservice));
        }
        service.setCost(cost);
        service.setDateConducted(dateConducted);

        return servicesRepository.save(service);
    }
}


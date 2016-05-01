package pl.polsl.controller;

import jersey.repackaged.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.model.*;
import pl.polsl.repository.*;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Date;
import java.util.List;


/**
 * Created by Kuba on 02.04.2016.
 */

@Component
@Path("/raports")
@Produces(MediaType.APPLICATION_JSON)
public class ReportsController {

    @Autowired
    private ReportsRepository reportsRepository;

    @Autowired
    private ShowroomsRepository showroomsRepository;

    @Autowired
    private CarsRepository carsRepository;

    @Autowired
    private WorkersRepository workersRepository;

    @Transactional
    public Report findReport(int id){
        return reportsRepository.findOne(id);}

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Report> findAllRaports() {
        return Lists.newArrayList(reportsRepository.findAll());
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Report findOne(int id){
        return reportsRepository.findOne(id);
    }

    public Report addReport(String name,
                            int showroomId ,
                            String content,
                            Date dateBeggining,
                            Date dateEnd
                            ) {
        Showroom targetShowroom = showroomsRepository.findOne(showroomId);
        List<Car> cars = carsRepository.findAllTheSameShowroom(targetShowroom);
        List<Worker> workers = workersRepository.findAllTheSameShowroom(targetShowroom);
        String showroomName = targetShowroom.getName();

        content = "";
        content += name + "\n";
        content += "Raport dotyczy: " + showroomName + "\n";
        content += "Ilość samochodów: " + cars.size() + "\n";
        content += "Ilość pracowników: " + workers.size();

        return reportsRepository.save(new Report(name, targetShowroom,content, dateBeggining, dateEnd));
    }
}

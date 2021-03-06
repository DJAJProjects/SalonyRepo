package pl.polsl.controller;

import jersey.repackaged.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.model.Car;
import pl.polsl.repository.ReportsRepository;
import pl.polsl.model.Report;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
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
}

package pl.polsl.controller;

import jersey.repackaged.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.model.Showroom;
import pl.polsl.repository.ShowroomsRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;


/**
 * Created by Dominika Błasiak on 07.04.2016.
 */

@Component
@Path("/showroom")
@Produces(MediaType.APPLICATION_JSON)
public class ShowroomsController {

    @Autowired
    private ShowroomsRepository showroomsRepository;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Showroom> findAll() {
        return Lists.newArrayList(showroomsRepository.findAll());
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Showroom findOne(int id){
        return showroomsRepository.findOne(id);
    }

    public void delete(int id){
        showroomsRepository.delete(id);
    }
}

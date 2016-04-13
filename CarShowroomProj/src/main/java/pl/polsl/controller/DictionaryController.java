package pl.polsl.controller;

import jersey.repackaged.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.model.Dictionary;
import pl.polsl.repository.DictionaryRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by Aleksandra on 2016-04-07.
 */
@Component
@Path("/dictionary")
@Produces(MediaType.APPLICATION_JSON)

public class DictionaryController {
    @Autowired
    private DictionaryRepository dictionaryRepository;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Dictionary> findAll() {
        return Lists.newArrayList(dictionaryRepository.findAll());
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Dictionary findOne(int id){
        return dictionaryRepository.findOne(id);}

    public List<Dictionary> findAllCities(){
        return dictionaryRepository.findAllTheSameType("city");
    }

    public List<Dictionary> findAllCountries() {
        return dictionaryRepository.findAllTheSameType("country");
    }

    public List<Dictionary> findAllPositions() { return dictionaryRepository.findAllTheSameType("position");
    }
}

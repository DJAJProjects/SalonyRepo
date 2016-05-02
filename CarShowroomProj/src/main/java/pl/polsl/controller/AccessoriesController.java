package pl.polsl.controller;

import jersey.repackaged.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.model.Accessory;
import pl.polsl.model.Car;
import pl.polsl.repository.AccessoriesRepository;
import pl.polsl.repository.CarsRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Aleksandra on 2016-04-07.
 */
@Component
@Path("/accessories")
@Produces(MediaType.APPLICATION_JSON)

public class AccessoriesController {
    @Autowired
    private AccessoriesRepository accessoriesRepository;

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Accessory> findAll() {
        return Lists.newArrayList(accessoriesRepository.findAll());
    }

    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Accessory findOne(int id){
        return accessoriesRepository.findOne(id);}
    public void edit(int id){
        accessoriesRepository.save(findOne(id));
    }
    public void deleteOne(int id) {
        accessoriesRepository.delete(id);
    }

    /**
     * Created by: Aleksandra Chronowska
     * @param accessories choosen accessories
     * @return avaliable to choose accessory
     */
    public Set<Accessory>findAccessoriesAvaliable(Set<Accessory>accessories) {
        Set<Accessory>accessoriesSet = findAll().stream().filter((Accessory ac)->ac.getContract()== null).collect(Collectors.toSet());
        List<Accessory>properList=new ArrayList<>();
        for(Accessory accessory : accessories) {
            Optional<Accessory> result = accessoriesSet.stream().filter(x -> x.getId() == accessory.getId()).findAny();
            if(result.isPresent())
                properList.add(result.get());
        }
        accessoriesSet.removeAll(properList);
        return accessoriesSet;
    }
}

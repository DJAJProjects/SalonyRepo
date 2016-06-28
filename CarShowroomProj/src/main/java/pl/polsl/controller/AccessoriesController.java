package pl.polsl.controller;

import jersey.repackaged.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.model.Accessory;
import pl.polsl.repository.AccessoriesRepository;
import pl.polsl.repository.DictionaryRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Accessories Contoller class
 * @author Julia Kubieniec
 * @version 1.0
 * Accessories controller class
 */
@Component
@Path("/accessories")
@Produces(MediaType.APPLICATION_JSON)

public class AccessoriesController {
    @Autowired
    private AccessoriesRepository accessoriesRepository;
    @Autowired
    private DictionaryRepository dictionaryRepository;

    /**
     * Rest get method
     * @return all accessory
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Accessory> findAll() {
        return Lists.newArrayList(accessoriesRepository.findAll());
    }

    /**
     * Rest method find accessory by id
     * @param id given accessory id
     * @return accessory data by json
     */
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Accessory findOne(int id){
        return accessoriesRepository.findOne(id);}

    /**
     * Method save object in repository.
     * Used to confirm contract in contract module.
     * @author Aleksadra Chronowska
     * @param id acccessory id
     */
    public void edit(int id){
        accessoriesRepository.save(findOne(id));
    }

    /**
     * Method delete proper accessory with given id
     * @param id accessory id
     */
    public void deleteOne(int id) {
        accessoriesRepository.delete(id);
    }
    public void deleteAccessory(int id) {
        accessoriesRepository.delete(id);
    }

    /**
     * Method added accessory to database
     * @param name accessory name
     * @param cost accessory cost
     * @param assemblyCost
     * @return added accessory object
     */
    public Accessory addAccessory(int name, int cost, int assemblyCost) {
        return accessoriesRepository.save(new Accessory(dictionaryRepository.findOne(name),cost, assemblyCost));
    }

    /**
     * Method used to edit current accessory
     * @param id accessory id
     * @param idName accessory name id
     * @param cost accessory cost
     * @param assemblyCost
     * @return
     */
    public Accessory editAccessory(int id, int idName, int cost, int assemblyCost) {

        Accessory accessory = accessoriesRepository.findOne(id);

        accessory.setAccessory(dictionaryRepository.findOne(idName));
        accessory.setCost(cost);
        accessory.setAssemblyCost(assemblyCost);
        return accessoriesRepository.save(accessory);
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

    /**
     * Method used to find accessories without any connections(with cars and contract)
     * @return avaliable accessory list
     */
    public Set<Accessory> findFreeAccessories() {
        List<Accessory> allAccessory = findAll();
        Set<Accessory> freeAccesssory = new HashSet<>();

        for(int i = 0; i < allAccessory.size(); i++) {
            if(allAccessory.get(i).getContract() == null && allAccessory.get(i).getCar() == null) {
                freeAccesssory.add(allAccessory.get(i));
            }
        }

        return freeAccesssory;
    }

    /**
     * Method for find all accessories for one car
     * @param carId current car id
     * @return list cars accessories
     */
    public Set<Accessory> findAllForOneCar(int carId) {
        List<Accessory> allAccessory = findAll();
        Set<Accessory> carAccesssory = new HashSet<>();

        for(int i = 0; i < allAccessory.size(); i++) {
            if(allAccessory.get(i).getCar().getId() == carId) {
                carAccesssory.add(allAccessory.get(i));
            }
        }

        return carAccesssory;
    }
}

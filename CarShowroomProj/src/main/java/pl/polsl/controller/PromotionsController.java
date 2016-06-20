package pl.polsl.controller;

import jersey.repackaged.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.polsl.model.Promotion;
import pl.polsl.repository.PromotionsRepository;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;


/**
 * Promotions Controller class
 * @author Aleksandra Chronowska
 * @version 1.0
 */
@Component
@Path("/promotions")
@Produces(MediaType.APPLICATION_JSON)

public class PromotionsController {
    @Autowired
    private PromotionsRepository promotionsRepository;
    /**
     * Rest get method
     * @author Aleksadra Chronowska
     * @return list of all promotions
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Promotion> findAll() {
        return Lists.newArrayList(promotionsRepository.findAll());
    }
    /**
     * Rest method find promotions by id
     * @param id
     * @return priviliges data by json
     */
    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Promotion findOne(int id){
        return promotionsRepository.findOne(id);}

    /**
     * Method finding actual promotion
     * @param date current date
     * @param promotionList list with all promotion
     * @return promotion list
     */
    public List<Promotion> findActual(Date date, Set<Promotion> promotionList) {

        List<Promotion>promotions = Lists.newArrayList(promotionsRepository.findActual(date));
        List<Promotion>properList = new ArrayList<>();
        for(Promotion promotion : promotionList) {
            Optional<Promotion> result = promotions.stream().filter(x -> x.getId() == promotion.getId()).findAny();
            if(result.isPresent())
                properList.add(result.get());
        }
        boolean remo = promotions.removeAll(properList);

        return promotions;
    }

    public Promotion edit(Promotion con) {
        return promotionsRepository.save(con);
    }

    public void deletePromotion(int id){
        promotionsRepository.delete(id);
    }

    public Promotion addPromotion(int percValue, String name, java.sql.Date dateStart, java.sql.Date dateEnd) {
        return promotionsRepository.save(new Promotion(percValue, name, dateStart, dateEnd));
    }
    /**
     * Method save object in repository.
     * Used to confirm promotion in contract module.
     * @author Aleksadra Chronowska
     * @param id promotion id
     */
    public Promotion editPromotion(int id, int percValue, String name, java.sql.Date dateStart, java.sql.Date dateEnd) {

        Promotion promotion = promotionsRepository.findOne(id);

        promotion.setPercValue(percValue);
        promotion.setName(name);
        promotion.setDateStart(dateStart);
        promotion.setDateEnd(dateEnd);
        return promotionsRepository.save(promotion);
    }

}

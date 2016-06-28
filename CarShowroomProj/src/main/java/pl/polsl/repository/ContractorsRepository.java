package pl.polsl.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.model.*;

import java.util.List;

/**
 * Created by Kuba on 2016-06-02.
 */
@Repository
@Transactional
public interface ContractorsRepository extends PagingAndSortingRepository<Contractor, Integer>{


    /**
     * @return List of clients related to director by contract model that was
     * created by worker who works i directors showroom.
     */

    @Query(value = "select contractor from Contractor contractor " +
                         "where exists (select contract from Contract contract  " +
                            " where contract.contractor = contractor " +
                            " and contract.worker.showroom.director = :director )")
    public List<Contractor> findRelatedToDirector ( @Param("director")Worker director);


    /**
     * @return List of clients related to salesman by contract model.
     */
    @Query(value = "select contractor from Contractor contractor " +
            "where exists (select contract from Contract contract  " +
                "where contract.contractor = contractor " +
                "and contract.worker = :salesman) ")
    public List<Contractor> findRelatedToSalesman( @Param("salesman")Worker salesman);

    /**
     * @return List of clients related to serviceman by car model:
     * Model of car servised by serviceman and related by contract model with client.
     */
    @Query(value = " select contractor from Contractor contractor " +
                   " where exists " +
                        " (select service from Service service  " +
                        " where service.serviceman = :serviceman " +
                        " and exists" +
                            " (select contract from Contract contract"+
                            " where contract.contractor = contractor"+
                            " and (exists"+
                                " (select car from Car car "+
                                " where car.contract = contract"+
                                " and (service.car = car " +
                                " or exists"+
                                    " (select accessory from Accessory accessory"+
                                    " where accessory.car = car )))"+
                            " or exists"+
                                " (select accessory from Accessory accessory "+
                                " where accessory.contract = contract"+
                                " and service.accessory = accessory ))))")
    public List<Contractor> findRelatedToServiceman( @Param("serviceman")Worker serviceman);
}


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
    @Query(value = "select contractor from Contractor contractor " +
                         "where exists (select contract from Contract contract  " +
                            " where contract.contractor = contractor " +
                            " and contract.worker.showroom.director = director )")
    public List<Contractor> findRelatedToDirector ( @Param("director")Worker director);


    /**
     * @return List of clients related to salesman by contract model.
     */
    @Query(value = "select contractor from Contractor contractor " +
            "where exists (select contract from Contract contract  " +
                "where contract.contractor = contractor" +
                "and contract.worker = :salesman) ")
    public List<Contractor> findRelatedToSalesman( @Param("salesman")Worker salesman);

    /**
     * @return List of clients related to serviceman by car model:
     * Model of car servised by serviceman and related by contract model with client.
     */
    @Query(value = "select contractor from Contractor contractor " +
            "where exists (select service from Service service  " +
            "where service.serviceman = :serviceman" +
            "and contract.worker = :salesman) ")
    public List<Contractor> findRelatedToServiceman( @Param("serviceman")Worker serviceman);
}


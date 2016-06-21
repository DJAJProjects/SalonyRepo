package pl.polsl.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.model.Contract;
import pl.polsl.model.Worker;

import java.util.List;

/**
 * Created by Aleksandra on 2016-04-07.
 */

@Repository
@Transactional
public interface ContractsRepository extends PagingAndSortingRepository<Contract, Integer> {

    @Query(value = "SELECT contract " +
            "FROM Contract contract " +
            "WHERE contract.worker.id = :workerId")
    public List<Contract> findForWorker(@Param("workerId") Integer workerId);

    @Query(value = "SELECT contract " +
            "FROM Contract contract " +
            "WHERE contract.worker.showroom.id = :showroomId")
    public List<Contract> findForDirector(@Param("showroomId") Integer showroomId);

    @Query(value = "SELECT contract " +
            "FROM Contract contract " +
            "WHERE EXISTS (SELECT car from Car car WHERE car.contract = contract " +
            "AND EXISTS " +
            "(SELECT service FROM Service service WHERE service.serviceman = :serviceman AND service.car = car)) " +
            "OR EXISTS " +
            "(SELECT accessory FROM Accessory accessory WHERE accessory.contract = contract AND EXISTS " +
            "(SELECT service FROM Service service WHERE service.serviceman = :serviceman AND service.accessory = accessory))")
    public List<Contract> findForServisman(@Param("serviceman") Worker serviceman);

}

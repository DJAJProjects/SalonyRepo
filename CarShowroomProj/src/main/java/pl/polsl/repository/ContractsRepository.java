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
 * Repository class for contracts module
 * @author Aleksandra Chronowska
 */

@Repository
@Transactional
public interface ContractsRepository extends PagingAndSortingRepository<Contract, Integer> {

    /**
     * Method finding all visilble contracts for worker, who is author
     * @param workerId worker id
     * @return list of visible contracts
     */
    @Query(value = "SELECT contract " +
            "FROM Contract contract " +
            "WHERE contract.worker.id = :workerId")
    public List<Contract> findForWorker(@Param("workerId") Integer workerId);

    /**
     *  Method finding visilble contracts from director,
     *  who have a showroom, where was a contracts.
     * @param showroomId showroom id
     * @return visible for director contracts
     */
    @Query(value = "SELECT contract " +
            "FROM Contract contract " +
            "WHERE contract.worker.showroom.id = :showroomId")
    public List<Contract> findForDirector(@Param("showroomId") Integer showroomId);

    /**
     * Method gets all contracts for sigin serviceman.
     * Contracts are visible only for serviceman, who servised sold car or sold accessory
     * @param serviceman actual user
     * @return visible contracts for serviceman
     */
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

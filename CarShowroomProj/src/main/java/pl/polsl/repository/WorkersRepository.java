package pl.polsl.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.model.Dictionary;
import pl.polsl.model.Showroom;
import pl.polsl.model.Worker;

import java.util.List;

/**
 * Created by Dominika Błasiak on 2016-04-07.
 */

@Repository
@Transactional
public interface WorkersRepository extends PagingAndSortingRepository<Worker, Integer> {
    @Query(value = "SELECT worker " +
                   "FROM Worker worker " +
                   "WHERE worker.login = :login AND worker.password = :password")
    public Worker findOne(@Param("login")String login, @Param("password")String password);

    @Query(value = "SELECT director " +
                   "FROM Worker director " +
                   "WHERE director.position.id = :position AND director.showroom = null")
    public List<Worker> findAllOneType(@Param("position")int position);

    @Query(value = "SELECT worker " +
                   "FROM Worker worker " +
                   "WHERE worker.showroom = :showroom ")
    public List<Worker> findAllTheSameShowroom (@Param("showroom")Showroom showroom);


    @Query(value = "SELECT serviceman " +
                   "FROM Worker serviceman " +
                   "WHERE serviceman.position.id = :position")
    public List<Worker> findAllServicemans(@Param("position")int position);

    /**
     * @author Kuba
     * Used in raports
     * @return Sum of workers monthly payments in given showroom
     */
    @Query(value = "SELECT SUM(worker.payment) " +
                   " FROM Worker worker " +
                   " WHERE worker.showroom = :showroom ")
    public Double getMonthlyPaymentsFromSameShowroom (@Param("showroom")Showroom showroom);

    @Query(value = "SELECT worker " +
                   "FROM Worker worker " +
                   "WHERE worker.login = :login")
    public Worker getWorkerByLogin(@Param("login")String login);
}

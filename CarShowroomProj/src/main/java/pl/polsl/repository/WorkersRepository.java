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
    @Query(value = "select worker from Worker worker where worker.login = :login AND worker.password = :password")
    public Worker findOne(@Param("login")String login, @Param("password")String password);
//    @Query(value = "select worker from Workers worker where worker.login = :login")
//    public Worker findOne(@Param("login")String login, @Param("password")String password);
    @Query(value = "select director from Worker director where director.position.id = :position AND director.showroom=null")
    public List<Worker> findAllOneType(@Param("position")int position);

    @Query(value = "select worker from Worker worker where worker.showroom = :showroom ")
    public List<Worker> findAllTheSameShowroom (@Param("showroom")Showroom showroom);


    @Query(value = "select serviceman from Worker serviceman where serviceman.position.id = :position")
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
}

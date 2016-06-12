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

    /**
     * Method needs to log in
     * @param login user login
     * @param password user password
     * @return user who has the same login like @param login and the same password like @param password
     * or null if that user doesn't exist
     */
    @Query(value = "SELECT worker " +
                   "FROM Worker worker " +
                   "WHERE worker.login = :login AND worker.password = :password")
    public Worker findOne(@Param("login")String login, @Param("password")String password);


    /**
     * @param position position identifier in application
     * @return all workers of one type (e.g. directors), whom position hasn't been related to any showroom yet.
     */
    @Query(value = "SELECT director " +
                   "FROM Worker director " +
                   "WHERE director.position.id = :position AND director.showroom = null")
    public List<Worker> findAllOneTypeWithoutShowroom(@Param("position")int position);

    @Query(value = "SELECT worker " +
                   "FROM Worker worker " +
                   "WHERE worker.showroom = :showroom OR worker.showroom = null")
    public List<Worker> findAllFromTheSameShowroom (@Param("showroom")Showroom showroom);


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

    @Query(value = "SELECT worker " +
            "FROM Worker worker " +
            "WHERE worker.showroom = :showroom")
    List<Worker> findRelatedToDirector(@Param("showroom") Showroom showroom);
}

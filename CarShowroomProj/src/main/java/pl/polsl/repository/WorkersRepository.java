package pl.polsl.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.model.Dictionary;
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
//    @Query(value = "select director from workers director where director.position.id = :1")
//    List<Worker> findAllFreeDirectors();
}

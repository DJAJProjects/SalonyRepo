package pl.polsl.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.model.Car;
import pl.polsl.model.Showroom;

import java.util.List;

/**
 * Created by Kuba on 3/19/2016.
 */

    @Repository
    @Transactional
    public interface CarsRepository extends PagingAndSortingRepository<Car, Integer> {

    @Query(value = "select car from Car car where car.showroom = :showroom ")
    public List<Car> findAllTheSameShowroom ( @Param("showroom")Showroom showroom);
}

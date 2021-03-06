package pl.polsl.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.model.Car;

/**
 * Created by Kuba on 3/19/2016.
 */

    @Repository
    @Transactional
    public interface CarsRepository extends PagingAndSortingRepository<Car, Integer> {
}

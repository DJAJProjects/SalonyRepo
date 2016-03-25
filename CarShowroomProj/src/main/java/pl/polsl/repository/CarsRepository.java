package pl.polsl.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import pl.polsl.model.CarEntity;

/**
 * Created by Kuba on 3/19/2016.
 */
public interface CarsRepository extends PagingAndSortingRepository<CarEntity, Long> {

}

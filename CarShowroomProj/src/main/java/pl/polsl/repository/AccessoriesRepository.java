package pl.polsl.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.model.Accessory;
import pl.polsl.model.Car;

/**
/**
 * Created by Aleksandra on 2016-04-07.
 */
@Repository
@Transactional
public interface AccessoriesRepository extends PagingAndSortingRepository<Accessory, Integer>{
}

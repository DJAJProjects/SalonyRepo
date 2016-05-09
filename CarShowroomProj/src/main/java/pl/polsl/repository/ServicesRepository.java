package pl.polsl.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.model.Service;

/**
 * Created by Julia on 2016-04-28.
 */
@Repository
@Transactional
public interface ServicesRepository extends PagingAndSortingRepository<Service, Integer> {
}

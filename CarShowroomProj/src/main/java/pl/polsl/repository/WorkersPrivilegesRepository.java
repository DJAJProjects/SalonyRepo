package pl.polsl.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.model.Accessory;
import pl.polsl.model.WorkersPrivileges;

/**
/**
 * Created by Kuba on 2016-06-07.
 */
@Repository
@Transactional
public interface WorkersPrivilegesRepository extends PagingAndSortingRepository<WorkersPrivileges, Integer>{
}

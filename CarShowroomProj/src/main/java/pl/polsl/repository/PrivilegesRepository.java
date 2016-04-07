package pl.polsl.repository;

import netscape.security.Privilege;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.model.Car;
import pl.polsl.model.Privileges;

/**
 * Created by Aleksandra on 2016-04-07.
 */

    @Repository
    @Transactional
    public interface PrivilegesRepository extends PagingAndSortingRepository<Privileges, Integer> {
}

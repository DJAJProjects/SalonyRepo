package pl.polsl.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.model.Contract;

/**
 * Created by Aleksandra on 2016-04-07.
 */

    @Repository
    @Transactional
    public interface ContractsRepository extends PagingAndSortingRepository<Contract, Integer> {
}

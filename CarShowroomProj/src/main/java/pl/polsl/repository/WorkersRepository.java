package pl.polsl.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.model.Worker;

/**
 * Created by Dominika Błasiak on 2016-04-07.
 */

    @Repository
    @Transactional
    public interface WorkersRepository extends PagingAndSortingRepository<Worker, Integer> {
}

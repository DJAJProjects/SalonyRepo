package pl.polsl.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.model.Dictionary;

/**
 * Created by Aleksandra on 2016-04-07.
 */
@Repository
@Transactional
public interface DictionaryRepository extends PagingAndSortingRepository<Dictionary, Integer>{
}

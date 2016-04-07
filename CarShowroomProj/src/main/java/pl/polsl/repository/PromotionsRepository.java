package pl.polsl.repository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.model.Promotion;
import pl.polsl.model.Report;

/**
 * Created by Aleksandra on 2016-04-07.
 */
@Repository
@Transactional
public interface PromotionsRepository extends PagingAndSortingRepository<Promotion, Integer>  {
}

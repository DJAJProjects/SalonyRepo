package pl.polsl.repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.model.Promotion;
import pl.polsl.model.Report;
import pl.polsl.model.Worker;

import java.util.Date;
import java.util.List;

/**
 * Created by Aleksandra on 2016-04-07.
 */
@Repository
@Transactional
public interface PromotionsRepository extends PagingAndSortingRepository<Promotion, Integer>  {
    @Query(value = "select promotion from Promotion promotion where promotion.dateStart < :date AND promotion.dateEnd > :date")
    public List<Promotion> findActual(@Param("date")Date date);
}

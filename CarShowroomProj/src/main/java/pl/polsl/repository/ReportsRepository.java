package pl.polsl.repository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.model.Report;

/**
 * Created by Kuba on 02.04.2016.
 */
@Repository
@Transactional
public interface ReportsRepository extends PagingAndSortingRepository<Report, Integer>  {
}

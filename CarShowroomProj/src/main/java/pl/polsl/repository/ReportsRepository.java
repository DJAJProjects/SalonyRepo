package pl.polsl.repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.model.Contractor;
import pl.polsl.model.Report;
import pl.polsl.model.Worker;

import java.util.List;

/**
 * Created by Kuba on 02.04.2016.
 */
@Repository
@Transactional
public interface ReportsRepository extends PagingAndSortingRepository<Report, Integer>  {

    /**
     * @return List of reports related to director by report target
     */
    @Query(value = "select report from Report report " +
            "where report.showroom.director = :director")
    public List<Report> findRelatedToDirector (@Param("director")Worker director);

}

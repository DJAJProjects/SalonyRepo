package pl.polsl.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.model.Car;
import pl.polsl.model.Contractor;
import pl.polsl.model.Promotion;
import pl.polsl.model.Showroom;

import java.util.List;

/**
 * Created by Aleksandra on 2016-04-15.
 */
@Repository
@Transactional
public interface ContractorsRepository extends PagingAndSortingRepository<Contractor, Integer>{

}


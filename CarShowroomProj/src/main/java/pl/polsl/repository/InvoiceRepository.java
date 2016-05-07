package pl.polsl.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.model.Accessory;

import pl.polsl.model.Invoice;
import pl.polsl.model.Showroom;
import java.sql.Date;

/**
 * Created by Aleksandra on 2016-04-07.
 */
@Repository
@Transactional
public interface InvoiceRepository extends PagingAndSortingRepository<Invoice, Integer>{

    /**
     * @author Kuba
     * Used in raports
     * @return incomes for showroom in given time interval.
     */
    @Query(value = "SELECT SUM(invoice.contract.totalCost) " +
                   " FROM Invoice invoice " +
                   " WHERE  invoice.contract.worker.showroom = :showroom " +
                      " AND invoice.isPaid = true " +
                      " AND invoice.dateSold > :dateBeg " +
                      " AND invoice.dateSold < :dateEnd ")
    public Double getIncomeForShowroom(@Param("showroom")Showroom showroom,
                                       @Param("dateBeg")Date dateBeg,
                                       @Param("dateEnd")Date dateEnd);
}

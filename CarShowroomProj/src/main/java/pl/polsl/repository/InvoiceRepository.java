package pl.polsl.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.model.Accessory;

import pl.polsl.model.Contract;
import pl.polsl.model.Invoice;
import pl.polsl.model.Showroom;
import java.sql.Date;
import java.util.List;

/**
 * Invoices class repository
 * @author Aleksandra Chronowska
 * @version 1.0
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


    /**
     * Method finding all visible invoices for current worker
     * Method return list invoices for worker, who was creator
     * @param workerId worker id
     * @return list visible invoices
     */
    @Query(value = "SELECT invoice " +
            "FROM Invoice invoice " +
            "WHERE invoice.contract.worker.id = :workerId")
    public List<Invoice> findForWorker(@Param("workerId") Integer workerId);

    /**
     * Method show director all invoices from his showroom
     * @param showroomId showroom id
     * @return visible list of invoices
     */
    @Query(value = "SELECT invoice " +
            "FROM Invoice invoice " +
            "WHERE invoice.contract.worker.showroom.id = :showroomId")
    public List<Invoice> findForDirector(@Param("showroomId") Integer showroomId);
}

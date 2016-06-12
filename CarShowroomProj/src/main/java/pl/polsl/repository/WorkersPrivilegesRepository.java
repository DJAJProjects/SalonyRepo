package pl.polsl.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.model.Accessory;
import pl.polsl.model.Worker;
import pl.polsl.model.WorkersPrivileges;
import pl.polsl.model.Privileges;

import java.util.List;

/**
/**
 * Created by Kuba on 2016-06-07.
 */
@Repository
@Transactional
public interface WorkersPrivilegesRepository extends PagingAndSortingRepository<WorkersPrivileges, Integer>{
    @Query(value = "SELECT wp " +
            " FROM WorkersPrivileges wp" +
            " WHERE  wp.worker = :worker " +
            " AND wp.privileges = :priv")
    public List<WorkersPrivileges> getWorkersPrivilegesByWorkerAndPrivilege(@Param("worker")Worker worker,
                                                                            @Param("priv")Privileges priv);
}

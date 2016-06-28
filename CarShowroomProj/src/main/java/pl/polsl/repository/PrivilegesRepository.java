package pl.polsl.repository;

import netscape.security.Privilege;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.model.*;

import java.sql.Date;
import java.util.List;

/**
 * Created by Kuba on 2016-04-07.
 */

    @Repository
    @Transactional
    public interface PrivilegesRepository extends PagingAndSortingRepository<Privileges, Integer> {


    @Query(value = "SELECT wp " +
            " FROM WorkersPrivileges wp" +
            " WHERE wp.privileges.insertPriv = true" +
            " AND wp.worker = :worker " +
            " AND wp.privileges.module.value = :moduleName")
    public List<WorkersPrivileges> getInsertPriv(@Param("moduleName")String moduleName,
                                                 @Param("worker")Worker worker);

    @Query(value = "SELECT wp " +
            " FROM WorkersPrivileges wp" +
            " WHERE wp.privileges.updatePriv = true" +
            " AND wp.worker = :worker " +
            " AND wp.privileges.module.value = :moduleName")
    public List<WorkersPrivileges> getUpdatePriv(@Param("moduleName")String moduleName,
                                                 @Param("worker")Worker worker);

    @Query(value = "SELECT wp " +
            " FROM WorkersPrivileges wp" +
            " WHERE wp.privileges.deletePriv = true" +
            " AND wp.worker = :worker " +
            " AND wp.privileges.module.value = :moduleName")
    public List<WorkersPrivileges> getDeletePriv(@Param("moduleName")String moduleName,
                                                 @Param("worker")Worker worker);

    @Query(value = "SELECT wp " +
            " FROM WorkersPrivileges wp" +
            " WHERE wp.privileges.readPriv = true" +
            " AND wp.worker = :worker " +
            " AND wp.privileges.module.value = :moduleName")
    public List<WorkersPrivileges> getReadPriv(@Param("moduleName")String moduleName,
                                                 @Param("worker")Worker worker);

    @Query(value = "SELECT p " +
            " FROM Privileges p" +
            " WHERE exists( " +
                "SELECT wp FROM WorkersPrivileges wp " +
                "WHERE wp.privileges = p " +
                "AND wp.worker = :worker)")
    public List<Privileges> getPrivsForWorker(@Param("worker")Worker worker);

    @Query(value = "SELECT p " +
            " FROM Privileges p" +
            " WHERE not exists( " +
            "SELECT wp FROM WorkersPrivileges wp " +
            "WHERE wp.privileges = p " +
            "AND wp.worker = :worker)")
    public List<Privileges> getUnrelatedPrivsForWorker(@Param("worker")Worker worker);


}

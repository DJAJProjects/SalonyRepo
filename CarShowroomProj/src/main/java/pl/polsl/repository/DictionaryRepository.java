package pl.polsl.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.Data;
import pl.polsl.model.Dictionary;

import java.util.List;

/**
 * Repository class for dictionary module
 * @author Dominika Błasiak
 */
@Repository
@Transactional
public interface DictionaryRepository extends PagingAndSortingRepository<Dictionary, Integer>{

    /**
     * Find all object of the same type
     * @param type
     * @return
     */
    @Query(value = "SELECT dictionary " +
                   "FROM Dictionary dictionary " +
                   "WHERE dictionary.type = :type")
    public List<Dictionary> findAllTheSameType(@Param("type")String type);

    /**
     * Find all type
     * @return
     */
    @Query(value = "SELECT DISTINCT type " +
                   "FROM Dictionary dictionary")
    List<String> findAllTypes();

    /**
     * Find position id
     * @param type type position
     * @returnpostion id
     */
    @Query(value = "SELECT dictionary.id " +
            "FROM Dictionary dictionary WHERE dictionary.value = :type")
    int findPositionId(@Param("type")String type);

}

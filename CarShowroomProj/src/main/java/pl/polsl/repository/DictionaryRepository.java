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
 * Created by Dominika Błasiak on 2016-04-07.
 */
@Repository
@Transactional
public interface DictionaryRepository extends PagingAndSortingRepository<Dictionary, Integer>{
    @Query(value = "SELECT dictionary " +
                   "FROM Dictionary dictionary " +
                   "WHERE dictionary.type = :type")
    public List<Dictionary> findAllTheSameType(@Param("type")String type);

    @Query(value = "SELECT DISTINCT type " +
                   "FROM Dictionary dictionary")
    List<String> findAllTypes();

    @Query(value = "SELECT dictionary.id " +
            "FROM Dictionary dictionary WHERE dictionary.value = :type")
    int findPositionId(@Param("type")String type);

//    @Query(value = "select types from AttachmentType types where types.type = :name")
//    public AttachmentType findAttachmentType(@Param("name")String name);
}

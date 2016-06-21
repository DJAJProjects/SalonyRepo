package pl.polsl.repository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.polsl.model.Showroom;
/**
 * Repository class for showroom module
 * @author Dominika Błasiak
 */
@Repository
@Transactional
public interface ShowroomsRepository extends PagingAndSortingRepository<Showroom, Integer>  {
}

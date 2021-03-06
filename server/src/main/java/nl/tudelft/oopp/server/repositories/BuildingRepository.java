package nl.tudelft.oopp.server.repositories;

import java.util.List;
import java.util.Optional;
import nl.tudelft.oopp.server.models.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * interface for building to implement crud operations.
 */
@Repository
public interface BuildingRepository extends JpaRepository<Building, Long> {

    List<Building> findAll();

    List<BuildingsDetails> findAllBy();

    List<BuildingNumber> getAllBy();

    boolean existsByNumber(Long number);

    Optional<Building> findBuildingByNumber(Long id);

}

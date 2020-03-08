package nl.tudelft.oopp.server.services;

import java.util.List;
import java.util.Optional;
import nl.tudelft.oopp.server.models.Building;
import nl.tudelft.oopp.server.repositories.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuildingService {

    @Autowired
    private BuildingRepository buidlingRepository;

    /**
     * Gets all buildings.
     *
     * @return a list of all buildings
     */
    public List<Building> getAllBuildings() {
        return buidlingRepository.findAll();
    }

    /**
     * Gets a building.
     *
     * @param id we search for
     * @return the building with that exact id if it exists or null if not
     */
    public Optional<Building> getBuilding(Long id) {
        return buidlingRepository.findById(id);
    }

    /**
     * Adds a building.
     *
     * @param building to be added to the list of buildings
     */
    public void addBuilding(Building building) {
        buidlingRepository.save(building);
    }

    /**
     * Updates a building.
     *
     * @param id       new one to be updated to
     * @param building to be updated
     */
    public void updateBuilding(Integer id, Building building) {
        buidlingRepository.save(building);
    }

    /**
     * Deletes a building.
     *
     * @param id to be deleted from the list of buildings
     */
    public void delete(Long id) {
        buidlingRepository.deleteById(id);
    }
}

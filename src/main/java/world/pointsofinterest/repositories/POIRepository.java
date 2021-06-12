package world.pointsofinterest.repositories;

import org.springframework.data.repository.CrudRepository;
import world.pointsofinterest.model.POI;

public interface POIRepository extends CrudRepository<POI, Long> {
}

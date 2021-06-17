package world.pointsofinterest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import world.pointsofinterest.model.POI;

public interface POIRepository extends JpaRepository<POI, Long> {
}

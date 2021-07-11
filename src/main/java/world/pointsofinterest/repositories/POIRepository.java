package world.pointsofinterest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import world.pointsofinterest.model.POI;

import java.util.List;

public interface POIRepository extends JpaRepository<POI, Long> {
    List<POI> findByIdIn(List<Long> ids);

    List<POI> findByLatitudeBetweenAndLongitudeBetween(Double minLat, Double maxLat, Double minLon, Double maxlon);
}

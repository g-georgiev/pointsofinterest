package world.pointsofinterest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import world.pointsofinterest.model.POI;

import java.util.List;

public interface POIRepository extends JpaRepository<POI, Long> {
    List<POI> findByIdIn(List<Long> ids);
}

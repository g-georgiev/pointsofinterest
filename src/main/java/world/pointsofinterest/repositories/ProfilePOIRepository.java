package world.pointsofinterest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import world.pointsofinterest.model.ProfilePOI;

import java.util.List;

public interface ProfilePOIRepository extends JpaRepository<ProfilePOI, Long> {
    List<ProfilePOI> findByProfileIdIn(List<Long> ids);
}

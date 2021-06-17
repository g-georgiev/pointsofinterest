package world.pointsofinterest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import world.pointsofinterest.model.Profile;

import java.util.List;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
        List<Profile> findByIdIn(Long[] ids);
}

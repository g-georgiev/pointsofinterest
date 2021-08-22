package world.pointsofinterest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import world.pointsofinterest.model.UserProfile;

import java.util.List;
import java.util.Optional;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
        List<UserProfile> findByIdIn(List<Long> ids);
        Optional<UserProfile> findByUsername(String username);
}

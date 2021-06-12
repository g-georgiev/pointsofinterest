package world.pointsofinterest.repositories;

import org.springframework.data.repository.CrudRepository;
import world.pointsofinterest.model.Profile;

public interface ProfileRepository extends CrudRepository<Profile, Long> {
}

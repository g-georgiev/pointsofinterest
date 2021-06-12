package world.pointsofinterest.repositories;

import org.springframework.data.repository.CrudRepository;
import world.pointsofinterest.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
}

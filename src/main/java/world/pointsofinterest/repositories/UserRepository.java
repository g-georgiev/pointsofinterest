package world.pointsofinterest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import world.pointsofinterest.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}

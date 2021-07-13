package world.pointsofinterest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import world.pointsofinterest.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}

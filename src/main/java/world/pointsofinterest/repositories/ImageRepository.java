package world.pointsofinterest.repositories;

import org.springframework.data.repository.CrudRepository;
import world.pointsofinterest.model.Image;

public interface ImageRepository extends CrudRepository<Image, Long> {
}

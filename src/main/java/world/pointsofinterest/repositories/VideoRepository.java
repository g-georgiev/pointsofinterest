package world.pointsofinterest.repositories;

import org.springframework.data.repository.CrudRepository;
import world.pointsofinterest.model.Video;

public interface VideoRepository extends CrudRepository<Video, Long> {
}

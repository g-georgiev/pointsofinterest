package world.pointsofinterest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import world.pointsofinterest.model.Video;

public interface VideoRepository extends JpaRepository<Video, Long> {
}

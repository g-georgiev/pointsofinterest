package world.pointsofinterest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import world.pointsofinterest.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPosterId(Long id);

    List<Comment> findByPoiId(Long id);

    List<Comment> findByProfileId(Long id);
}

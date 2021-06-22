package world.pointsofinterest.repositories;

import org.springframework.data.repository.CrudRepository;
import world.pointsofinterest.model.Comment;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {
    List<Comment> findByPosterId(Long id);

    List<Comment> findByPoiId(Long id);

    List<Comment> findByProfileId(Long id);
}

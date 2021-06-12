package world.pointsofinterest.repositories;

import org.springframework.data.repository.CrudRepository;
import world.pointsofinterest.model.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long> {
}

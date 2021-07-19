package world.pointsofinterest.services.interfaces;

import org.springframework.transaction.annotation.Transactional;
import world.pointsofinterest.api.v1.model.CommentDTO;

import java.util.List;

@Transactional
public interface CommentService extends CommonService<CommentDTO, CommentDTO, Long> {

    List<CommentDTO> findAllByPOI(Long id);

    List<CommentDTO> findAllByProfile(Long id);

    List<CommentDTO> findAllByPoster(Long id);
}

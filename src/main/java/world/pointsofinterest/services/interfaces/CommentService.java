package world.pointsofinterest.services.interfaces;

import org.springframework.transaction.annotation.Transactional;
import world.pointsofinterest.api.v1.model.CommentDTO;
import world.pointsofinterest.model.POI;
import world.pointsofinterest.model.Profile;

import java.util.List;

@Transactional
public interface CommentService extends CommonService<CommentDTO, CommentDTO, Long> {

    List<CommentDTO> findAllByPOI(POI poi);

    List<CommentDTO> findAllByProfile(Profile profile);

    List<CommentDTO> findAllByPoster(Profile profile);
}

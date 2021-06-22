package world.pointsofinterest.services.interfaces;

import world.pointsofinterest.api.v1.model.CommentDTO;
import world.pointsofinterest.model.POI;
import world.pointsofinterest.model.Profile;

import java.util.List;

public interface CommentService {

    List<CommentDTO> findAllByPOI(POI poi);

    List<CommentDTO> findAllByProfile(Profile profile);

    List<CommentDTO> findAllByPoster(Profile profile);

    CommentDTO save(CommentDTO commentDTO);

    CommentDTO update(Long id, CommentDTO commentDTO);

    void deleteById(Long id);
}

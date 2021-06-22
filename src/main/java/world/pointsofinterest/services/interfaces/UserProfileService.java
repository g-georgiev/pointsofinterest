package world.pointsofinterest.services.interfaces;

import world.pointsofinterest.api.v1.model.CommentDTO;
import world.pointsofinterest.api.v1.model.ProfileDTO;

import java.util.List;

public interface UserProfileService extends CommonService<ProfileDTO, ProfileDTO, Long> {

    List<CommentDTO> findAllPostedComments(Long id);

    List<CommentDTO> findAllReceivedComments(Long id);

    CommentDTO addComment(Long id, CommentDTO commentDTO);
}

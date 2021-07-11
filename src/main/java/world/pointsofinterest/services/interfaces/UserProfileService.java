package world.pointsofinterest.services.interfaces;

import org.springframework.transaction.annotation.Transactional;
import world.pointsofinterest.api.v1.model.CommentDTO;
import world.pointsofinterest.api.v1.model.ImageDTO;
import world.pointsofinterest.api.v1.model.ProfileDTO;

import java.util.List;

@Transactional
public interface UserProfileService extends CommonService<ProfileDTO, ProfileDTO, Long> {

    List<ProfileDTO> findAll();

    List<CommentDTO> findAllPostedComments(Long id);

    List<CommentDTO> findAllReceivedComments(Long id);

    CommentDTO addComment(Long id, CommentDTO commentDTO);

    List<ImageDTO> findAllImages(Long id);

    ImageDTO addImage(Long id, ImageDTO imageDTO);
}

package world.pointsofinterest.services.interfaces;

import world.pointsofinterest.api.v1.model.CommentDTO;
import world.pointsofinterest.api.v1.model.POIRequestDTO;
import world.pointsofinterest.api.v1.model.POIResponseDTO;

import java.util.List;

public interface POIService extends CommonService<POIRequestDTO, POIResponseDTO, Long> {

    List<POIResponseDTO> findAllByCategory(Long id);

    List<POIResponseDTO> findAllByProfile(Long id);

    List<CommentDTO> findAllComments(Long id);

    CommentDTO addComment(Long id, CommentDTO commentDTO);

}

package world.pointsofinterest.services.interfaces;

import org.springframework.transaction.annotation.Transactional;
import world.pointsofinterest.api.v1.model.*;

import java.io.IOException;
import java.util.List;

@Transactional
public interface POIService extends CommonService<POIRequestDTO, POIResponseDTO, Long> {
    
    List<POIResponseDTO> findAll();

    List<POIResponseDTO> findAllByCategory(Long id);

    List<POIResponseDTO> findAllPostedPOIsByProfile(Long id);

    List<POIResponseDTO> findAllByRange(Double currentLat, Double currentLon, Double rangeInKm);

    List<CommentDTO> findAllComments(Long id);

    CommentDTO addComment(Long id, CommentDTO commentDTO);

    List<ImageDTO> findAllImages(Long id);

    ImageDTO addImage(Long id, ImageDTO image) throws IOException;

}

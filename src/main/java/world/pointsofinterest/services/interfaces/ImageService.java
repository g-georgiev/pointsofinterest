package world.pointsofinterest.services.interfaces;

import org.springframework.transaction.annotation.Transactional;
import world.pointsofinterest.api.v1.model.ImageDTO;

import java.util.List;

@Transactional
public interface ImageService extends CommonService<ImageDTO, ImageDTO, Long> {

    List<ImageDTO> findAllByPOI(Long id);

    List<ImageDTO> findAllByProfile(Long id);
}

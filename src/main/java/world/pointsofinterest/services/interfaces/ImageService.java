package world.pointsofinterest.services.interfaces;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import world.pointsofinterest.api.v1.model.ImageDTO;
import world.pointsofinterest.model.POI;
import world.pointsofinterest.model.Profile;

import java.util.List;

@Transactional
public interface ImageService extends CommonService<ImageDTO, ImageDTO, Long> {

    List<ImageDTO> findAllByPOI(POI poi);

    List<ImageDTO> findAllByProfile(Profile profile);
}

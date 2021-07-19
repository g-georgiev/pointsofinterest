package world.pointsofinterest.services.interfaces;

import org.springframework.transaction.annotation.Transactional;
import world.pointsofinterest.api.v1.model.*;

import java.io.IOException;
import java.util.List;

@Transactional
public interface POIService extends CommonService<POIRequestDTO, POIResponseDTO, Long> {

    List<POIResponseDTO> findAllByCategory(Long id);

    List<POIResponseDTO> findAllPOIsByProfile(Long id, Boolean checkIn);

    List<POIResponseDTO> findAllByRange(Double currentLat, Double currentLon, Double rangeInKm);

    POIResponseDTO checkIn(Long poiId, Long profileId);

}

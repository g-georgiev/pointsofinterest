package world.pointsofinterest.controllers.v1;

import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import world.pointsofinterest.api.v1.model.POIRequestDTO;
import world.pointsofinterest.api.v1.model.POIResponseDTO;
import world.pointsofinterest.services.interfaces.POIService;

import javax.validation.Valid;

/**
 * Used for creating POIs without authentication.
 * A mighty temporary solution. To be removed
 */
@RestController
@RequestMapping("/v1/pub/poi")
public class PublicPOIController {

    private final POIService poiService;

    public PublicPOIController(POIService poiService) {
        this.poiService = poiService;
    }


    @PostMapping
    public POIResponseDTO createNewPOI(
            @Parameter(description = "Data for the new point of interest", required = true)
            @Valid @RequestBody POIRequestDTO poiDTO
    ) {
        // TODO: Fake authentication
        return poiService.save(poiDTO);
    }
}

package world.pointsofinterest.controllers.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import world.pointsofinterest.api.v1.model.CommentDTO;
import world.pointsofinterest.api.v1.model.ImageDTO;
import world.pointsofinterest.api.v1.model.POIRequestDTO;
import world.pointsofinterest.api.v1.model.POIResponseDTO;
import world.pointsofinterest.services.interfaces.CommentService;
import world.pointsofinterest.services.interfaces.CommonService;
import world.pointsofinterest.services.interfaces.ImageService;
import world.pointsofinterest.services.interfaces.POIService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping(POIController.BASE_URL)
public class POIController {
    public static final String BASE_URL = "/api/v1/poi";
    public static final String POI_IMAGE_PATH = "/images";
    public static final String POI_VIDEO_PATH = "/videos";
    public static final String POI_COMMENT_PATH = "/comments";

    private final POIService poiService;
    private final ImageService imageService;
    private final CommentService commentService;

    public POIController(POIService poiService, ImageService imageService, CommentService commentService) {
        this.poiService = poiService;
        this.imageService = imageService;
        this.commentService = commentService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get a list of all points of interest with possible range filtration",
    description = "To apply the additional range filtration all the params must be passed." +
            "The range is defined by the latitude and longitude of its center and its radius.")
    public List<POIResponseDTO> getListOfPOIs(
            @Parameter(description = "The latitude of the center of the range")
            @RequestParam(required = false, name = "lat") Double latitude,
            @Parameter(description = "The longitude of the center of the range")
            @RequestParam(required = false, name = "lon") Double longitude,
            @Parameter(description = "The radius of the range in kilometers")
            @RequestParam(required = false, name = "r") Double rangeInKm){
        List<POIResponseDTO> foundPOI;
        if(latitude != null && longitude != null && rangeInKm != null){
            foundPOI = poiService.findAllByRange(latitude, longitude, rangeInKm);
        } else {
            foundPOI = poiService.findAll();
        }

        return foundPOI;
    }

    @GetMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get a point of interest by ID")
    public POIResponseDTO getPOIById(
            @Parameter(description = "The id of the point of interest to fetch", required = true)
            @PathVariable Long id){
        return poiService.findById(id);
    }

    @GetMapping({"/{id}" + POI_COMMENT_PATH})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get a list of all the comments for a given point of interest")
    public List<CommentDTO> getAllCommentsForPOI(
            @Parameter(description = "The id of the point of interest whose comments to fetch", required = true)
            @PathVariable Long id){ return commentService.findAllByPOI(id); }

    @GetMapping({"/{id}" + POI_IMAGE_PATH})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get a list of all the images for a given point of interest")
    public List<ImageDTO> getAllImagesForPOI(
            @Parameter(description = "The id of the point of interest whose images to fetch", required = true)
            @PathVariable Long id){ return imageService.findAllByPOI(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new point of interest")
    public POIResponseDTO createNewPOI(
            @Parameter(description = "Data for the new point of interest", required = true)
            @Valid
            @RequestBody POIRequestDTO poiDTO){
        return poiService.save(poiDTO);
    }

    @PutMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update an exiting point of interest")
    public POIResponseDTO updatePOI(
            @Parameter(description = "The id of the point of interest to update", required = true)
            @PathVariable Long id,
            @Parameter(description = "New data for the point of interest to update", required = true)
            @Valid
            @RequestBody POIRequestDTO poiDTO){
        return poiService.update(id, poiDTO);
    }

    @PostMapping({"/{id}/checkin"})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Check into a point of interest")
    public POIResponseDTO checkInPOI(
            @Parameter(description = "The id of the point of interest to check into", required = true)
            @PathVariable Long id,
            @Parameter(description = "The user userProfile that checked in", required = true)
            @Valid
            @NotNull
            @RequestParam(name = "profile_id") Long profile_id){
        return poiService.checkIn(id, profile_id);
    }

    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete a point of interest")
    public void deletePOI(
            @Parameter(description = "The id of the point of interest to delete", required = true)
            @PathVariable Long id){
        poiService.deleteById(id);
    }
}

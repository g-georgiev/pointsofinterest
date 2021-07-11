package world.pointsofinterest.controllers.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import world.pointsofinterest.api.v1.model.CommentDTO;
import world.pointsofinterest.api.v1.model.ImageDTO;
import world.pointsofinterest.api.v1.model.POIRequestDTO;
import world.pointsofinterest.api.v1.model.POIResponseDTO;
import world.pointsofinterest.services.interfaces.POIService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(POIController.BASE_URL)
public class POIController {
    public static final String BASE_URL = "/api/v1/poi";
    public static final String POI_IMAGE_PATH = "/images";
    public static final String POI_VIDEO_PATH = "/videos";
    public static final String POI_COMMENT_PATH = "/comments";

    private final POIService poiService;

    public POIController(POIService poiService) {
        this.poiService = poiService;
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
            @PathVariable Long id){ return poiService.findAllComments(id); }

    @GetMapping({"/{id}" + POI_IMAGE_PATH})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get a list of all the images for a given point of interest")
    public List<ImageDTO> getAllImagesForPOI(
            @Parameter(description = "The id of the point of interest whose images to fetch", required = true)
            @PathVariable Long id){ return poiService.findAllImages(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new point of interest")
    public POIResponseDTO createNewPOI(
            @Parameter(description = "Data for the new point of interest", required = true)
            @RequestBody POIRequestDTO poiDTO){
        return poiService.save(poiDTO);
    }

    @PostMapping({"/{id}" + POI_COMMENT_PATH})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Post a comment for a point of interest")
    public CommentDTO addCommentForPOI(
            @Parameter(description = "The id of the point of interest", required = true)
            @PathVariable Long id,
            @Parameter(description = "The data of the comment to be posted", required = true)
            @RequestBody CommentDTO commentDTO){
        return poiService.addComment(id, commentDTO);
    }

    @PostMapping({"/{id}" + POI_IMAGE_PATH})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Add an image for a point of interest")
    public ImageDTO addImageForPOI(
            @Parameter(description = "The id of the point of interest", required = true)
            @PathVariable Long id,
            @Parameter(description = "The image to be added", required = true)
            @RequestBody ImageDTO imageDTO) throws IOException {
        return poiService.addImage(id, imageDTO);
    }

    @PutMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update an exiting point of interest")
    public POIResponseDTO updatePOI(
            @Parameter(description = "The id of the point of interest to update", required = true)
            @PathVariable Long id,
            @Parameter(description = "New data for the point of interest to update", required = true)
            @RequestBody POIRequestDTO poiDTO){
        return poiService.update(id, poiDTO);
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

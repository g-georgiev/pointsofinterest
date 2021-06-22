package world.pointsofinterest.controllers.v1;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import world.pointsofinterest.api.v1.model.CommentDTO;
import world.pointsofinterest.api.v1.model.POIRequestDTO;
import world.pointsofinterest.api.v1.model.POIResponseDTO;
import world.pointsofinterest.services.interfaces.POIService;

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
    public List<POIResponseDTO> getListOfPOIs(){
        return poiService.findAll();
    }

    @GetMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public POIResponseDTO getPOIById(@PathVariable Long id){
        return poiService.findById(id);
    }

    @GetMapping({"/{id}" + POI_COMMENT_PATH})
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDTO> getAllCommentsForPOI(@PathVariable Long id){ return poiService.findAllComments(id); }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public POIResponseDTO createNewPOI(@RequestBody POIRequestDTO poiDTO){
        return poiService.save(poiDTO);
    }

    @PostMapping({"/{id}" + POI_COMMENT_PATH})
    @ResponseStatus(HttpStatus.OK)
    public CommentDTO addCommentForPOI(@PathVariable Long id, @RequestBody CommentDTO commentDTO){
        return poiService.addComment(id, commentDTO);
    }

    @PutMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public POIResponseDTO updatePOI(@PathVariable Long id, @RequestBody POIRequestDTO poiDTO){
        return poiService.update(id, poiDTO);
    }

    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public void deletePOI(@PathVariable Long id){
        poiService.deleteById(id);
    }
}

package world.pointsofinterest.controllers.v1;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import world.pointsofinterest.api.v1.model.POIDTO;
import world.pointsofinterest.services.POIService;

import java.util.List;

@RestController
@RequestMapping(POIController.BASE_URL)
public class POIController {
    public static final String BASE_URL = "/api/v1/poi";
    public static final String POI_IMAGE_PATH = "/image/";
    public static final String POI_VIDEO_PATH = "/video/";
    public static final String POI_COMMENT_PATH = "/comment/";

    private final POIService poiService;

    public POIController(POIService poiService) {
        this.poiService = poiService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<POIDTO> getListOfPOIs(){
        return poiService.findAll();
    }

    @GetMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public POIDTO getPOIById(@PathVariable Long id){
        return poiService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public POIDTO createNewPOI(@RequestBody POIDTO poiDTO){
        return poiService.save(poiDTO);
    }

    @PutMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public POIDTO updatePOI(@PathVariable Long id, @RequestBody POIDTO poiDTO){
        return poiService.update(id, poiDTO);
    }


    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public void deletePOI(@PathVariable Long id){
        poiService.deleteById(id);
    }
}

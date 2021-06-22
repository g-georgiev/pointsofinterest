package world.pointsofinterest.controllers.v1;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import world.pointsofinterest.api.v1.model.CommentDTO;
import world.pointsofinterest.api.v1.model.ProfileDTO;
import world.pointsofinterest.api.v1.model.POIResponseDTO;
import world.pointsofinterest.services.interfaces.POIService;
import world.pointsofinterest.services.interfaces.UserProfileService;

import java.util.List;

@RestController
@RequestMapping(UserProfileController.BASE_URL)
public class UserProfileController {
    public static final String BASE_URL = "/api/v1/profiles";
    public static final String IMAGE_PATH = "/images";
    public static final String POSTED_COMMENT_PATH = "/posted_comments";
    public static final String RECEIVED_COMMENT_PATH = "/comments";
    public static final String POI_PATH = "/poi";

    private final UserProfileService userProfileService;
    private final POIService poiService;

    public UserProfileController(UserProfileService userProfileService, POIService poiService) {
        this.userProfileService = userProfileService;
        this.poiService = poiService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProfileDTO> getListOfProfiles(){
        return userProfileService.findAll();
    }

    @GetMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public ProfileDTO getProfileById(@PathVariable Long id){
        return userProfileService.findById(id);
    }

    @GetMapping({"/{id}" + POI_PATH})
    @ResponseStatus(HttpStatus.OK)
    public List<POIResponseDTO> getPOIByProfileId(@PathVariable Long id){
        return poiService.findAllByProfile(id);
    }

    @GetMapping({"/{id}" + POSTED_COMMENT_PATH})
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDTO> getPostedComments(@PathVariable Long id){
        return userProfileService.findAllPostedComments(id);
    }

    @GetMapping({"/{id}" + RECEIVED_COMMENT_PATH})
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDTO> getReceivedComments(@PathVariable Long id){
        return userProfileService.findAllReceivedComments(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProfileDTO createNewProfile(@RequestBody ProfileDTO ProfileDTO){
        return userProfileService.save(ProfileDTO);
    }

    @PostMapping({"/{id}" + RECEIVED_COMMENT_PATH})
    @ResponseStatus(HttpStatus.OK)
    public CommentDTO addCommentForProfile(@PathVariable Long id, @RequestBody CommentDTO commentDTO){
        return userProfileService.addComment(id, commentDTO);
    }

    @PutMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public ProfileDTO updateProfile(@PathVariable Long id, @RequestBody ProfileDTO ProfileDTO){
        return userProfileService.update(id, ProfileDTO);
    }

    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteProfile(@PathVariable Long id){
        userProfileService.deleteById(id);
    }
    
}

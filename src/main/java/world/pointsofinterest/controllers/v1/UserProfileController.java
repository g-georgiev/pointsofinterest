package world.pointsofinterest.controllers.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
    @Operation(summary = "Get a list of all user profiles")
    public List<ProfileDTO> getListOfProfiles(){
        return userProfileService.findAll();
    }

    @GetMapping({"/{id}"})
    @Operation(summary = "Get a user profile by ID")
    @ResponseStatus(HttpStatus.OK)
    public ProfileDTO getProfileById(
            @Parameter(description = "The id of the user profile to fetch", required = true)
            @PathVariable Long id){
        return userProfileService.findById(id);
    }

    @GetMapping({"/{id}" + POI_PATH})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get a list of all the points of interest a user has posted")
    public List<POIResponseDTO> getPostedPOIsByProfileId(
            @Parameter(description = "The id of the user, whose posted points of interest to fetch", required = true)
            @PathVariable Long id){
        return poiService.findAllPostedPOIsByProfile(id);
    }

    @GetMapping({"/{id}" + POSTED_COMMENT_PATH})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get a list of all the comments a user has posted")
    public List<CommentDTO> getPostedComments(
            @Parameter(description = "The id of the user, whose posted comments to fetch", required = true)
            @PathVariable Long id){
        return userProfileService.findAllPostedComments(id);
    }

    @GetMapping({"/{id}" + RECEIVED_COMMENT_PATH})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get a list of all the comments a user has received")
    public List<CommentDTO> getReceivedComments(
            @Parameter(description = "The id of the user, whose received comments to fetch", required = true)
            @PathVariable Long id){
        return userProfileService.findAllReceivedComments(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new user profile")
    public ProfileDTO createNewProfile(
            @Parameter(description = "Data for the new user profile", required = true)
            @RequestBody ProfileDTO ProfileDTO){
        return userProfileService.save(ProfileDTO);
    }

    @PostMapping({"/{id}" + RECEIVED_COMMENT_PATH})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Post a comment for a user profile")
    public CommentDTO addCommentForProfile(
            @Parameter(description = "The id of the user profile", required = true)
            @PathVariable Long id,
            @Parameter(description = "The data of the comment to be posted", required = true)
            @RequestBody CommentDTO commentDTO){
        return userProfileService.addComment(id, commentDTO);
    }

    @PutMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update an exiting user profile")
    public ProfileDTO updateProfile(
            @Parameter(description = "The id of the user profile to update", required = true)
            @PathVariable Long id,
            @Parameter(description = "New data for the user profile to update", required = true)
            @RequestBody ProfileDTO ProfileDTO){
        return userProfileService.update(id, ProfileDTO);
    }

    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete a user profile")
    public void deleteProfile(@PathVariable Long id){
        userProfileService.deleteById(id);
    }
    
}

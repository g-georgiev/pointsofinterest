package world.pointsofinterest.controllers.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import world.pointsofinterest.api.v1.model.CommentDTO;
import world.pointsofinterest.api.v1.model.ImageDTO;
import world.pointsofinterest.api.v1.model.ProfileDTO;
import world.pointsofinterest.api.v1.model.POIResponseDTO;
import world.pointsofinterest.services.interfaces.CommentService;
import world.pointsofinterest.services.interfaces.ImageService;
import world.pointsofinterest.services.interfaces.POIService;
import world.pointsofinterest.services.interfaces.UserProfileService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(UserProfileController.BASE_URL)
public class UserProfileController {
    public static final String BASE_URL = "/api/v1/profiles";
    public static final String IMAGE_PATH = "/images";
    public static final String POSTED_COMMENT_PATH = "/posted_comments";
    public static final String RECEIVED_COMMENT_PATH = "/comments";
    public static final String POI_PATH = "/poi";
    public static final String CHECK_INS_PATH = "/checkins";

    private final UserProfileService userProfileService;
    private final POIService poiService;
    private final ImageService imageService;
    private final CommentService commentService;
    private final PasswordEncoder passwordEncoder;

    public UserProfileController(UserProfileService userProfileService, POIService poiService, ImageService imageService, CommentService commentService, PasswordEncoder passwordEncoder) {
        this.userProfileService = userProfileService;
        this.poiService = poiService;
        this.imageService = imageService;
        this.commentService = commentService;
        this.passwordEncoder = passwordEncoder;
        userProfileService.setPasswordEncoder(passwordEncoder);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get a list of all user profiles")
    public List<ProfileDTO> getListOfProfiles(){
        return userProfileService.findAll();
    }

    @GetMapping({"/{id}"})
    @Operation(summary = "Get a user userProfile by ID")
    @ResponseStatus(HttpStatus.OK)
    public ProfileDTO getProfileById(
            @Parameter(description = "The id of the user userProfile to fetch", required = true)
            @PathVariable Long id){
        return userProfileService.findById(id);
    }

    @GetMapping({"/{id}" + POI_PATH})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get a list of all the points of interest a user has posted")
    public List<POIResponseDTO> getPostedPOIsByProfileId(
            @Parameter(description = "The id of the user, whose posted points of interest to fetch", required = true)
            @PathVariable Long id){
        return poiService.findAllPOIsByProfile(id, false);
    }

    @GetMapping({"/{id}" + CHECK_INS_PATH})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get a list of all the points of interest a user has checked into")
    public List<POIResponseDTO> getCheckedInPOIsByProfileId(
            @Parameter(description = "The id of the user, whose posted points of interest to fetch", required = true)
            @PathVariable Long id){
        return poiService.findAllPOIsByProfile(id, true);
    }

    @GetMapping({"/{id}" + POSTED_COMMENT_PATH})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get a list of all the comments a user has posted")
    public List<CommentDTO> getPostedComments(
            @Parameter(description = "The id of the user, whose posted comments to fetch", required = true)
            @PathVariable Long id){
        return commentService.findAllByPoster(id);
    }

    @GetMapping({"/{id}" + RECEIVED_COMMENT_PATH})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get a list of all the comments a user has received")
    public List<CommentDTO> getReceivedComments(
            @Parameter(description = "The id of the user, whose received comments to fetch", required = true)
            @PathVariable Long id){
        return commentService.findAllByProfile(id);
    }

    @GetMapping({"/{id}" + IMAGE_PATH})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get a list of all userProfile images a user has uploaded")
    public List<ImageDTO> getProfileImages(
            @Parameter(description = "The id of the user, whose userProfile images to fetch", required = true)
            @PathVariable Long id){
        return imageService.findAllByProfile(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new user userProfile")
    public ProfileDTO createNewProfile(
            @Parameter(description = "Data for the new user userProfile", required = true)
            @Valid
            @RequestBody ProfileDTO ProfileDTO){
        return userProfileService.save(ProfileDTO);
    }

    @PutMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update an exiting user userProfile")
    public ProfileDTO updateProfile(
            @Parameter(description = "The id of the user userProfile to update", required = true)
            @PathVariable Long id,
            @Parameter(description = "New data for the user userProfile to update", required = true)
            @Valid
            @RequestBody ProfileDTO ProfileDTO){
        return userProfileService.update(id, ProfileDTO);
    }

    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete a user userProfile")
    public void deleteProfile(@PathVariable Long id){
        userProfileService.deleteById(id);
    }
    
}

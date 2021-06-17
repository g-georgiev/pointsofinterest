package world.pointsofinterest.controllers.v1;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UserProfileController.BASE_URL)
public class UserProfileController {
    public static final String BASE_URL = "/api/v1/profile";
    public static final String PROFILE_IMAGE_PATH = "/image/";
    public static final String PROFILE_COMMENT_PATH = "/comment/";
}

package world.pointsofinterest.api.v1.mappers;

import org.springframework.stereotype.Component;
import world.pointsofinterest.api.v1.model.CommentDTO;
import world.pointsofinterest.api.v1.model.ProfileDTO;
import world.pointsofinterest.model.Profile;
import world.pointsofinterest.model.User;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProfileMapper {
    private final CommentMapper commentMapper;

    public ProfileMapper(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    public ProfileDTO profileToProfileDTO(Profile profile){
        List<CommentDTO> receivedComments = profile.getReceivedComments().stream()
                .map(commentMapper::commentToCommentDTO)
                .collect(Collectors.toList());

        return new ProfileDTO(profile.getId(), profile.getUser().getUsername(), profile.getBanned(),
                profile.getDescription(), profile.getRating(), receivedComments);
    }

    public Profile profileDTOToProfile(ProfileDTO profileDTO){
        Profile profile = new Profile(null, profileDTO.getDescription(), profileDTO.getRating(), new User());
        profile.getUser().setUsername(profileDTO.getUsername());
        profile.getUser().setProfile(profile);
        return profile;
    }
}

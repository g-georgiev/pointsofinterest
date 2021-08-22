package world.pointsofinterest.api.v1.mappers;

import org.springframework.stereotype.Component;
import world.pointsofinterest.api.v1.model.CommentDTO;
import world.pointsofinterest.api.v1.model.ImageDTO;
import world.pointsofinterest.api.v1.model.ProfileDTO;
import world.pointsofinterest.model.Role;
import world.pointsofinterest.model.UserProfile;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ProfileMapper {
    private final CommentMapper commentMapper;
    private final ImageMapper imageMapper;

    public ProfileMapper(CommentMapper commentMapper, ImageMapper imageMapper) {
        this.commentMapper = commentMapper;
        this.imageMapper = imageMapper;
    }

    public ProfileDTO profileToProfileDTO(UserProfile userProfile){

        Set<ImageDTO> images = userProfile.getImageSet().stream()
                .map(imageMapper::imageToImageDTO)
                .collect(Collectors.toSet());

        List<CommentDTO> receivedComments = userProfile.getReceivedComments().stream()
                .map(commentMapper::commentToCommentDTO)
                .collect(Collectors.toList());

        Set<String> authorities = userProfile.getAuthorities().stream()
                .map(Role::getAuthority)
                .collect(Collectors.toSet());

        return new ProfileDTO(userProfile.getId(), userProfile.getUsername(), userProfile.getPassword(),
                authorities, userProfile.getBanned(),
                userProfile.getDescription(), userProfile.getRating(), images, receivedComments);
    }

    public UserProfile profileDTOToProfile(ProfileDTO profileDTO, Set<Role> roles){
        return new UserProfile(null, profileDTO.getDescription(), profileDTO.getRating(),
                profileDTO.getUsername(), profileDTO.getPassword(), roles, profileDTO.getBanned());
    }
}

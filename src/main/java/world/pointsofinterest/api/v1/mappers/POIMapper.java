package world.pointsofinterest.api.v1.mappers;

import org.springframework.stereotype.Component;
import world.pointsofinterest.api.v1.model.*;
import world.pointsofinterest.model.Category;
import world.pointsofinterest.model.POI;
import world.pointsofinterest.model.ProfilePOI;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class POIMapper {

    private final CommentMapper commentMapper;
    private final ProfileMapper profileMapper;
    private final CategoryMapper categoryMapper;
    private final ImageMapper imageMapper;

    public POIMapper(CommentMapper commentMapper, ProfileMapper profileMapper, CategoryMapper categoryMapper, ImageMapper imageMapper) {
        this.commentMapper = commentMapper;
        this.profileMapper = profileMapper;
        this.categoryMapper = categoryMapper;
        this.imageMapper = imageMapper;
    }

    public POIResponseDTO POIToPOIDTO(POI POI){

        Boolean hasComment = !POI.getComments().isEmpty();

        Set<ImageDTO> images = POI.getImageSet().stream()
                .map(imageMapper::imageToImageDTO)
                .collect(Collectors.toSet());

        Set<VideoDTO> videos = POI.getVideoSet().stream()
                .map(video -> new VideoDTO(video.getId(), video.getDescription(), video.getRating()))
                .collect(Collectors.toSet());

        Set<CategoryDTO> categories = POI.getCategories().stream()
                .map(categoryMapper::categoryToCategoryDTO)
                .collect(Collectors.toSet());

        Set<ProfileDTO> posters = POI.getProfilePOIs(false).stream()
                .map(profileMapper::profileToProfileDTO)
                .collect(Collectors.toSet());

        Set<ProfileDTO> checkIns = POI.getProfilePOIs(true).stream()
                .map(profileMapper::profileToProfileDTO)
                .collect(Collectors.toSet());

        Set<CommentDTO> comments = POI.getComments().stream()
                .map(commentMapper::commentToCommentDTO)
                .collect(Collectors.toSet());

        return new POIResponseDTO(
                POI.getId(), POI.getLatitude(), POI.getLongitude(), POI.getDescription(),
                POI.getRating(), hasComment, images, videos, categories, posters, checkIns, comments);
    }

    public POI POIDTOToPOI(POIRequestDTO POIRequestDTO, Set<Category> categories, Set<ProfilePOI> profilePOIS) {
        return new POI(
                null,
                POIRequestDTO.getDescription(),
                POIRequestDTO.getRating(),
                POIRequestDTO.getLatitude(),
                POIRequestDTO.getLongitude(),
                categories,
                profilePOIS
        );
    }

}

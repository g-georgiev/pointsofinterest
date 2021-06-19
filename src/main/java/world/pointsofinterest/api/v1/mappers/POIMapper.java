package world.pointsofinterest.api.v1.mappers;

import org.springframework.stereotype.Component;
import world.pointsofinterest.api.v1.model.*;
import world.pointsofinterest.model.*;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class POIMapper {

    public POIResponseDTO POIToPOIDTO(POI POI){

        Boolean hasComment = !POI.getComments().isEmpty();

        Map<Long, URL> images =
                POI.getImageSet().stream().collect(Collectors.toMap(Image::getId, Image::getUrl));

        Map<Long, URL> videos =
                POI.getVideoSet().stream().collect(Collectors.toMap(Video::getId, Video::getUrl));

        Map<Long, String> categories =
                POI.getCategories().stream().collect(Collectors.toMap(Category::getId, Category::getName));

        Map<Long, String> profiles =
                POI.getProfiles().stream().collect(Collectors.toMap(Profile::getId, Profile::getUsername));

        return new POIResponseDTO(
                POI.getId(), POI.getLatitude(), POI.getLongitude(), POI.getDescription(),
                POI.getRating(), hasComment, images, videos, categories, profiles
        );
    }

    public POI POIDTOToPOI(POIRequestDTO POIRequestDTO, Set<Category> categories, Set<Profile> profiles) {
        return new POI(
                null,
                POIRequestDTO.getDescription(),
                POIRequestDTO.getRating(),
                POIRequestDTO.getLatitude(),
                POIRequestDTO.getLongitude(),
                categories,
                profiles
        );
    }

}

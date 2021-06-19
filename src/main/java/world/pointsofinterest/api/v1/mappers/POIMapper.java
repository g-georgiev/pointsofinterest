package world.pointsofinterest.api.v1.mappers;

import org.springframework.stereotype.Component;
import world.pointsofinterest.api.v1.model.*;
import world.pointsofinterest.model.Category;
import world.pointsofinterest.model.POI;
import world.pointsofinterest.model.Profile;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class POIMapper {

    public POIResponseDTO POIToPOIDTO(POI POI){

        Boolean hasComment = !POI.getComments().isEmpty();

        Map<Long, String> images = new HashMap<>();
        POI.getImageSet().stream().map(image -> { images.put(image.getId(), null); return null;});

        Map<Long, String> videos = new HashMap<>();
        POI.getVideoSet().stream().map(video -> { videos.put(video.getId(), null); return null;});

        Map<Long, String> categories = new HashMap<>();
        POI.getCategories().stream().map(category -> { categories.put(category.getId(), null); return null;});

        Map<Long, String> profiles = new HashMap<>();
        POI.getProfiles().stream().map(profile -> { profiles.put(profile.getId(), null); return null;});

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

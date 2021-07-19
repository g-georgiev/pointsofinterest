package world.pointsofinterest.services;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;
import org.springframework.stereotype.Service;
import world.pointsofinterest.api.v1.mappers.POIMapper;
import world.pointsofinterest.api.v1.model.POIRequestDTO;
import world.pointsofinterest.api.v1.model.POIResponseDTO;
import world.pointsofinterest.model.Category;
import world.pointsofinterest.model.POI;
import world.pointsofinterest.model.Profile;
import world.pointsofinterest.model.ProfilePOI;
import world.pointsofinterest.repositories.CategoryRepository;
import world.pointsofinterest.repositories.POIRepository;
import world.pointsofinterest.repositories.ProfileRepository;
import world.pointsofinterest.services.interfaces.POIService;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class POIServiceImpl implements POIService {

    private final POIRepository poiRepository;
    private final CategoryRepository categoryRepository;
    private final ProfileRepository profileRepository;

    private final POIMapper poiMapper;

    public POIServiceImpl(POIRepository poiRepository,
                          CategoryRepository categoryRepository,
                          ProfileRepository profileRepository,
                          POIMapper poiMapper) {
        this.poiRepository = poiRepository;
        this.categoryRepository = categoryRepository;
        this.profileRepository = profileRepository;
        this.poiMapper = poiMapper;
    }

    @Override
    public List<POIResponseDTO> findAll() {
        return poiRepository.findAll()
                .stream()
                .map(poiMapper::POIToPOIDTO)
                .collect(Collectors.toList());
    }

    @Override
    public POIResponseDTO findById(Long aLong) {
        return poiRepository.findById(aLong)
                .map(poiMapper::POIToPOIDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List<POIResponseDTO> findAllByCategory(Long id){
        Category category = categoryRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return category.getPOIs()
                .stream()
                .map(poiMapper::POIToPOIDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<POIResponseDTO> findAllPOIsByProfile(Long id, Boolean checkIn) {
        Profile profile = profileRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return profile.getProfilePOIs(checkIn)
                .stream()
                .map(poiMapper::POIToPOIDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<POIResponseDTO> findAllByRange(Double currentLat, Double currentLon, Double rangeInKm){
        POI.validateCoordinates(currentLat, currentLon);

        LatLng currentPoint = new LatLng(currentLat, currentLon);
        List<POIResponseDTO> foundPOIs = new ArrayList<>();

        //Initial filtration
        //Approximate km to degrees conversion
        Double rangeInDegrees = rangeInKm / 40000 * 360;
        //Finding POIs in an approximate rectangle around the current point
        List<POI> POIs = poiRepository.findByLatitudeBetweenAndLongitudeBetween(
                currentLat - rangeInDegrees,
                currentLat + rangeInDegrees,
                currentLon - rangeInDegrees,
                currentLon + rangeInDegrees
        );

        //Fine filtration using Geo distance calculation lib
        for(POI poi : POIs){
            double distance = LatLngTool.distance(currentPoint,
                    new LatLng(poi.getLatitude(), poi.getLongitude()),
                    LengthUnit.KILOMETER);
            if(rangeInKm >= distance){
                foundPOIs.add(poiMapper.POIToPOIDTO(poi));
            }
        }

        return foundPOIs;
    }

    @Override
    public POIResponseDTO checkIn(Long poiId, Long profileId) {
        POI poi = poiRepository.findById(poiId).orElseThrow(ResourceNotFoundException::new);
        Profile profile = profileRepository.findById(profileId).orElseThrow(ResourceNotFoundException::new);

        poi.addProfilePOIS(new ProfilePOI(poi, profile, true));
        return poiMapper.POIToPOIDTO(poiRepository.save(poi));
    }

    @Override
    public POIResponseDTO save(POIRequestDTO poiRequestDTO) {
        if(poiRequestDTO == null || poiRequestDTO.getCategories() == null || poiRequestDTO.getPosters() == null){
            throw new InvalidParameterException("Both categories and original posters are required for POI creation");
        }

        Set<Category> categories = new HashSet<>(categoryRepository.findByIdIn(poiRequestDTO.getCategories()));
        Set<ProfilePOI> profilePOIS = profileRepository.findByIdIn(poiRequestDTO.getPosters()).stream()
                .map(profile -> new ProfilePOI(null, profile, false))
                .collect(Collectors.toSet());
        POI newPOI = poiMapper.POIDTOToPOI(poiRequestDTO, categories, profilePOIS);
        newPOI.getProfilePOIS().forEach(profilePOI -> profilePOI.setPoi(newPOI));

        return poiMapper.POIToPOIDTO(poiRepository.save(newPOI));
    }

    @Override
    public POIResponseDTO update(Long id, POIRequestDTO poiRequestDTO) {
        if(id == null) {throw new InvalidParameterException("ID of POI to update is required");}
        if(poiRequestDTO == null || poiRequestDTO.getDescription() == null && poiRequestDTO.getRating() == null){
            throw new InvalidParameterException("Either description or rating must be passed to update");
        }

        return poiRepository.findById(id).map(poi -> {
            if(poiRequestDTO.getDescription() != null){
                poi.setDescription(poiRequestDTO.getDescription());
            }
            if(poiRequestDTO.getRating() != null){
                poi.addRating(poiRequestDTO.getRating());
            }

            return poiMapper.POIToPOIDTO(poiRepository.save(poi));
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteById(Long id) {
        poiRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        poiRepository.deleteById(id);
    }
}

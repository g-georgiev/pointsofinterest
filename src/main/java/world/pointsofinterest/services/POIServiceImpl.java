package world.pointsofinterest.services;

import com.javadocmd.simplelatlng.LatLng;
import com.javadocmd.simplelatlng.LatLngTool;
import com.javadocmd.simplelatlng.util.LengthUnit;
import org.springframework.stereotype.Service;
import world.pointsofinterest.api.v1.mappers.CommentMapper;
import world.pointsofinterest.api.v1.mappers.POIMapper;
import world.pointsofinterest.api.v1.model.CommentDTO;
import world.pointsofinterest.api.v1.model.ImageDTO;
import world.pointsofinterest.api.v1.model.POIRequestDTO;
import world.pointsofinterest.api.v1.model.POIResponseDTO;
import world.pointsofinterest.model.Category;
import world.pointsofinterest.model.POI;
import world.pointsofinterest.model.Profile;
import world.pointsofinterest.model.ProfilePOI;
import world.pointsofinterest.repositories.*;
import world.pointsofinterest.services.interfaces.CommentService;
import world.pointsofinterest.services.interfaces.ImageService;
import world.pointsofinterest.services.interfaces.POIService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class POIServiceImpl implements POIService {

    private final POIRepository poiRepository;
    private final ProfilePOIRepository profilePOIRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final VideoRepository videoRepository;
    private final ProfileRepository profileRepository;

    private final POIMapper poiMapper;
    private final CommentMapper commentMapper;

    private final CommentService commentService;
    private final ImageService imageService;

    public POIServiceImpl(POIRepository poiRepository, ProfilePOIRepository profilePOIRepository, CategoryRepository categoryRepository,
                          ImageRepository imageRepository, VideoRepository videoRepository,
                          ProfileRepository profileRepository, POIMapper poiMapper,
                          CommentMapper commentMapper, CommentService commentService, ImageService imageService) {
        this.poiRepository = poiRepository;
        this.profilePOIRepository = profilePOIRepository;
        this.categoryRepository = categoryRepository;
        this.imageRepository = imageRepository;
        this.videoRepository = videoRepository;
        this.profileRepository = profileRepository;
        this.poiMapper = poiMapper;
        this.commentMapper = commentMapper;
        this.commentService = commentService;
        this.imageService = imageService;
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
            if(rangeInKm >= LatLngTool.distance(currentPoint,
                    new LatLng(poi.getLatitude(), poi.getLongitude()),
                    LengthUnit.KILOMETER)){
                foundPOIs.add(poiMapper.POIToPOIDTO(poi));
            }
        }

        return foundPOIs;
    }

    @Override
    public List<CommentDTO> findAllComments(Long id) {
        POI poi = poiRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return commentService.findAllByPOI(poi);
    }

    @Override
    public CommentDTO addComment(Long id, CommentDTO commentDTO) {
        commentDTO.setPOIId(id);
        return commentService.save(commentDTO);
    }

    @Override
    public List<ImageDTO> findAllImages(Long id) {
        POI poi = poiRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return imageService.findAllByPOI(poi);
    }

    @Override
    public ImageDTO addImage(Long id, ImageDTO imageDTO) {
        imageDTO.setPoiId(id);
        return imageService.save(imageDTO);
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
        Set<Category> categories = new HashSet<>(categoryRepository.findByIdIn(poiRequestDTO.getCategories()));
        Set<ProfilePOI> profilePOIS = new HashSet<>(
                profilePOIRepository.findByProfileIdIn(poiRequestDTO.getPosters()));

        return poiMapper.POIToPOIDTO(poiRepository.save(
                poiMapper.POIDTOToPOI(poiRequestDTO, categories, profilePOIS)));
    }

    @Override
    public POIResponseDTO update(Long id, POIRequestDTO poiRequestDTO) {

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

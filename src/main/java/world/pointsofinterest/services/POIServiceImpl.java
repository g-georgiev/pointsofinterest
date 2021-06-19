package world.pointsofinterest.services;

import org.springframework.stereotype.Service;
import world.pointsofinterest.api.v1.mappers.POIMapper;
import world.pointsofinterest.api.v1.model.POIRequestDTO;
import world.pointsofinterest.api.v1.model.POIResponseDTO;
import world.pointsofinterest.controllers.v1.CategoryController;
import world.pointsofinterest.controllers.v1.POIController;
import world.pointsofinterest.controllers.v1.UserProfileController;
import world.pointsofinterest.model.Category;
import world.pointsofinterest.model.POI;
import world.pointsofinterest.model.Profile;
import world.pointsofinterest.repositories.*;
import world.pointsofinterest.services.interfaces.POIService;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class POIServiceImpl implements POIService {

    private final POIRepository poiRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final VideoRepository videoRepository;
    private final ProfileRepository profileRepository;

    private final POIMapper poiMapper;

    public POIServiceImpl(POIRepository poiRepository, CategoryRepository categoryRepository,
                          ImageRepository imageRepository, VideoRepository videoRepository,
                          ProfileRepository profileRepository, POIMapper poiMapper) {
        this.poiRepository = poiRepository;
        this.categoryRepository = categoryRepository;
        this.imageRepository = imageRepository;
        this.videoRepository = videoRepository;
        this.profileRepository = profileRepository;
        this.poiMapper = poiMapper;
    }

    @Override
    public List<POIResponseDTO> findAll() {
        return poiRepository.findAll()
                .stream()
                .map(poi -> {
                    POIResponseDTO poiDTO = poiMapper.POIToPOIDTO(poi);
                    poiDTO.setLinks(initLinks(poi));
                    return poiDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public POIResponseDTO findById(Long aLong) {
        return poiRepository.findById(aLong)
                .map(poi -> {
                    POIResponseDTO poiDTO = poiMapper.POIToPOIDTO(poi);
                    poiDTO.setLinks(initLinks(poi));
                    return poiDTO;
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    public List<POIResponseDTO> findAllByCategory(Long id){
        Category category = categoryRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return category.getPOIs()
                .stream()
                .map(poi -> {
                    POIResponseDTO poiDTO = poiMapper.POIToPOIDTO(poi);
                    poiDTO.setLinks(initLinks(poi));
                    return poiDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public POIResponseDTO save(POIRequestDTO poiRequestDTO) {
        Set<Category> categories = new HashSet<>(categoryRepository.findByIdIn(poiRequestDTO.getCategories()));
        Set<Profile> profiles = new HashSet<>(profileRepository.findByIdIn(poiRequestDTO.getProfiles()));

        POI poi = poiRepository.save(poiMapper.POIDTOToPOI(poiRequestDTO, categories, profiles));
        POIResponseDTO newPoiDTO = poiMapper.POIToPOIDTO(poi);
        newPoiDTO.setLinks(initLinks(poi));

        return newPoiDTO;
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

            POIResponseDTO returnDto = poiMapper.POIToPOIDTO(poiRepository.save(poi));
            returnDto.setLinks(initLinks(poi));

            return returnDto;

        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteById(Long id) {
        poiRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        poiRepository.deleteById(id);
    }

    private Map<String, String> initLinks(POI poi) {
        Map<String, String> links = new HashMap<>();
        links.put("showProfiles", UserProfileController.BASE_URL);
        links.put("showCategories", CategoryController.BASE_URL);
        links.put("showComments", POIController.BASE_URL + "/" + poi.getId() + POIController.POI_COMMENT_PATH);
        links.put("showImages", POIController.BASE_URL + "/" + poi.getId() + POIController.POI_IMAGE_PATH);
        links.put("showVideos", POIController.BASE_URL + "/" + poi.getId() + POIController.POI_VIDEO_PATH);
        return links;
    }
}

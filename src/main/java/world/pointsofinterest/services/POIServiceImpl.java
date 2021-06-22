package world.pointsofinterest.services;

import org.springframework.stereotype.Service;
import world.pointsofinterest.api.v1.mappers.CommentMapper;
import world.pointsofinterest.api.v1.mappers.POIMapper;
import world.pointsofinterest.api.v1.model.CommentDTO;
import world.pointsofinterest.api.v1.model.POIRequestDTO;
import world.pointsofinterest.api.v1.model.POIResponseDTO;
import world.pointsofinterest.model.Category;
import world.pointsofinterest.model.Comment;
import world.pointsofinterest.model.POI;
import world.pointsofinterest.model.Profile;
import world.pointsofinterest.repositories.*;
import world.pointsofinterest.services.interfaces.CommentService;
import world.pointsofinterest.services.interfaces.POIService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class POIServiceImpl implements POIService {

    private final POIRepository poiRepository;
    private final CategoryRepository categoryRepository;
    private final ImageRepository imageRepository;
    private final VideoRepository videoRepository;
    private final ProfileRepository profileRepository;

    private final POIMapper poiMapper;
    private final CommentMapper commentMapper;

    private final CommentService commentService;

    public POIServiceImpl(POIRepository poiRepository, CategoryRepository categoryRepository,
                          ImageRepository imageRepository, VideoRepository videoRepository,
                          ProfileRepository profileRepository, POIMapper poiMapper,
                          CommentMapper commentMapper, CommentService commentService) {
        this.poiRepository = poiRepository;
        this.categoryRepository = categoryRepository;
        this.imageRepository = imageRepository;
        this.videoRepository = videoRepository;
        this.profileRepository = profileRepository;
        this.poiMapper = poiMapper;
        this.commentMapper = commentMapper;
        this.commentService = commentService;
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

    public List<POIResponseDTO> findAllByCategory(Long id){
        Category category = categoryRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return category.getPOIs()
                .stream()
                .map(poiMapper::POIToPOIDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<POIResponseDTO> findAllByProfile(Long id) {
        Profile profile = profileRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return profile.getPostedPOIs()
                .stream()
                .map(poiMapper::POIToPOIDTO)
                .collect(Collectors.toList());
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
    public POIResponseDTO save(POIRequestDTO poiRequestDTO) {
        Set<Category> categories = new HashSet<>(categoryRepository.findByIdIn(poiRequestDTO.getCategories()));
        Set<Profile> profiles = new HashSet<>(profileRepository.findByIdIn(poiRequestDTO.getProfiles()));

        return poiMapper.POIToPOIDTO(poiRepository.save(
                poiMapper.POIDTOToPOI(poiRequestDTO, categories, profiles)));
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

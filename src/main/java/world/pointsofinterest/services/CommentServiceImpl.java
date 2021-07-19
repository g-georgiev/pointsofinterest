package world.pointsofinterest.services;

import org.springframework.stereotype.Service;
import world.pointsofinterest.api.v1.mappers.CommentMapper;
import world.pointsofinterest.api.v1.model.CommentDTO;
import world.pointsofinterest.model.POI;
import world.pointsofinterest.model.Profile;
import world.pointsofinterest.repositories.CommentRepository;
import world.pointsofinterest.repositories.POIRepository;
import world.pointsofinterest.repositories.ProfileRepository;
import world.pointsofinterest.services.interfaces.CommentService;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ProfileRepository profileRepository;
    private final POIRepository poiRepository;

    private final CommentMapper commentMapper;

    public CommentServiceImpl(CommentRepository commentRepository,
                              ProfileRepository profileRepository,
                              POIRepository poiRepository,
                              CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.profileRepository = profileRepository;
        this.poiRepository = poiRepository;
        this.commentMapper = commentMapper;
    }

    @Override
    public List<CommentDTO> findAllByPOI(Long id) {
        POI poi = poiRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return poi.getComments().stream()
                .map(commentMapper::commentToCommentDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentDTO> findAllByProfile(Long id) {
        Profile profile = profileRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return profile.getReceivedComments().stream()
                .map(commentMapper::commentToCommentDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentDTO> findAllByPoster(Long id) {
        Profile profile = profileRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return profile.getPostedComments().stream()
                .map(commentMapper::commentToCommentDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentDTO> findAll() {
        return commentRepository.findAll().stream()
                .map(commentMapper::commentToCommentDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CommentDTO findById(Long id) {
        return commentRepository.findById(id)
                .map(commentMapper::commentToCommentDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public CommentDTO save(CommentDTO commentDTO) {
        if(commentDTO == null || commentDTO.getPOIId() == null || commentDTO.getProfileId() == null){
            throw new InvalidParameterException("Both categories and original posters are required for POI creation");
        }

        Profile poster = profileRepository.findById(commentDTO.getPosterId()).orElseThrow(ResourceNotFoundException::new);
        POI poi = null;
        Profile profile = null;
        if(commentDTO.getPOIId() != null) {
            poi = poiRepository.findById(commentDTO.getPOIId()).orElseThrow(ResourceNotFoundException::new);
        }
        if(commentDTO.getProfileId() != null) {
             profile = profileRepository.findById(commentDTO.getProfileId()).orElseThrow(ResourceNotFoundException::new);
        }

        return commentMapper.commentToCommentDTO(commentRepository.save(
                commentMapper.commentDTOToComment(commentDTO, poster, poi, profile)));
    }

    @Override
    public CommentDTO update(Long id, CommentDTO commentDTO) {
        return commentRepository.findById(id).map(comment -> {
            if(commentDTO.getComment() != null){
                comment.setComment(commentDTO.getComment());
            }

            return commentMapper.commentToCommentDTO(commentRepository.save(comment));
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteById(Long id) {
        commentRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        commentRepository.deleteById(id);
    }
}

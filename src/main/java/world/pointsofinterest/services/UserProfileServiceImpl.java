package world.pointsofinterest.services;

import org.springframework.stereotype.Service;
import world.pointsofinterest.api.v1.mappers.ProfileMapper;
import world.pointsofinterest.api.v1.model.CommentDTO;
import world.pointsofinterest.api.v1.model.ImageDTO;
import world.pointsofinterest.api.v1.model.ProfileDTO;
import world.pointsofinterest.model.Profile;
import world.pointsofinterest.repositories.ProfileRepository;
import world.pointsofinterest.repositories.UserRepository;
import world.pointsofinterest.services.interfaces.CommentService;
import world.pointsofinterest.services.interfaces.ImageService;
import world.pointsofinterest.services.interfaces.UserProfileService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    private final ProfileMapper profileMapper;

    private final CommentService commentService;
    private final ImageService imageService;

    public UserProfileServiceImpl(UserRepository userRepository, ProfileRepository profileRepository,
                                  ProfileMapper profileMapper, CommentService commentService, ImageService imageService) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
        this.commentService = commentService;
        this.imageService = imageService;
    }

    @Override
    public List<ProfileDTO> findAll() {
        return profileRepository.findAll()
                .stream()
                .map(profileMapper::profileToProfileDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProfileDTO findById(Long id) {
        return profileRepository.findById(id)
                .map(profileMapper::profileToProfileDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public List<CommentDTO> findAllPostedComments(Long id) {
        Profile profile = profileRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return commentService.findAllByPoster(profile);
    }

    @Override
    public List<CommentDTO> findAllReceivedComments(Long id) {
        Profile profile = profileRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return commentService.findAllByProfile(profile);
    }

    @Override
    public CommentDTO addComment(Long id, CommentDTO commentDTO) {
        commentDTO.setProfileId(id);
        return commentService.save(commentDTO);
    }

    @Override
    public List<ImageDTO> findAllImages(Long id) {
        Profile profile = profileRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        return imageService.findAllByProfile(profile);
    }

    @Override
    public ImageDTO addImage(Long id, ImageDTO imageDTO) {
        imageDTO.setProfileId(id);
        return imageService.save(imageDTO);
    }

    @Override
    public ProfileDTO save(ProfileDTO profileDTO) {
        return profileMapper.profileToProfileDTO(
                profileRepository.save(profileMapper.profileDTOToProfile(profileDTO)));
    }

    @Override
    public ProfileDTO update(Long id, ProfileDTO profileDTO) {
        return profileRepository.findById(id).map(profile -> {
            if(profileDTO.getBanned() != null){
                profile.setBanned(profileDTO.getBanned());
            }
            if(profileDTO.getDescription() != null){
                profile.setDescription(profileDTO.getDescription());
            }
            if(profileDTO.getRating() != null){
                profile.addRating(profileDTO.getRating());
            }

            return profileMapper.profileToProfileDTO(profileRepository.save(profile));
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteById(Long id) {
        profileRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        profileRepository.deleteById(id);
    }


}

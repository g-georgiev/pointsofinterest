package world.pointsofinterest.services;

import org.springframework.stereotype.Service;
import world.pointsofinterest.api.v1.mappers.ProfileMapper;
import world.pointsofinterest.api.v1.model.ProfileDTO;
import world.pointsofinterest.repositories.ProfileRepository;
import world.pointsofinterest.services.interfaces.UserProfileService;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final ProfileRepository profileRepository;

    private final ProfileMapper profileMapper;

    public UserProfileServiceImpl(ProfileRepository profileRepository, ProfileMapper profileMapper) {
        this.profileRepository = profileRepository;
        this.profileMapper = profileMapper;
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
    public ProfileDTO save(ProfileDTO profileDTO) {
        if(profileDTO == null) { throw new InvalidParameterException("Profile DTO is required"); }
        return profileMapper.profileToProfileDTO(
                profileRepository.save(profileMapper.profileDTOToProfile(profileDTO)));
    }

    @Override
    public ProfileDTO update(Long id, ProfileDTO profileDTO) {
        if(id == null) {throw new InvalidParameterException("ID of profile to update is required");}
        if(profileDTO == null || profileDTO.getDescription() == null && profileDTO.getBanned() == null &&
                profileDTO.getRating() == null){
            throw new InvalidParameterException(
                    "Either description, banned flag or rating must be passed to update");
        }

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

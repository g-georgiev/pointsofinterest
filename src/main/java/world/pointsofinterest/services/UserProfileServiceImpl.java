package world.pointsofinterest.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import world.pointsofinterest.api.v1.mappers.ProfileMapper;
import world.pointsofinterest.api.v1.model.ProfileDTO;
import world.pointsofinterest.model.Authority;
import world.pointsofinterest.model.Role;
import world.pointsofinterest.repositories.RoleRepository;
import world.pointsofinterest.repositories.UserProfileRepository;
import world.pointsofinterest.services.interfaces.UserProfileService;

import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final RoleRepository roleRepository;

    private final ProfileMapper profileMapper;
    private PasswordEncoder passwordEncoder;

    public UserProfileServiceImpl(UserProfileRepository userProfileRepository,
                                  RoleRepository roleRepository,
                                  ProfileMapper profileMapper) {
        this.userProfileRepository = userProfileRepository;
        this.roleRepository = roleRepository;
        this.profileMapper = profileMapper;
    }

    @Override
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userProfileRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User " + username + " not found"));
    }

    @Override
    public List<ProfileDTO> findAll() {
        return userProfileRepository.findAll()
                .stream()
                .map(profileMapper::profileToProfileDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProfileDTO findById(Long id) {
        return userProfileRepository.findById(id)
                .map(profileMapper::profileToProfileDTO)
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public ProfileDTO save(ProfileDTO profileDTO) {
        if(profileDTO == null) { throw new InvalidParameterException("Profile DTO is required"); }
        List<Role> userRoles = roleRepository.findByAuthorityIn(
                profileDTO.getAuthorities().stream().map(Authority::findByString)
                .collect(Collectors.toList()) );
        profileDTO.setPassword(passwordEncoder.encode(profileDTO.getPassword()));
        return profileMapper.profileToProfileDTO( userProfileRepository.save(
                        profileMapper.profileDTOToProfile(profileDTO, new HashSet<>(userRoles))));
    }

    @Override
    public ProfileDTO update(Long id, ProfileDTO profileDTO) {
        if(id == null) {throw new InvalidParameterException("ID of userProfile to update is required");}
        if(profileDTO == null || profileDTO.getDescription() == null && profileDTO.getBanned() == null &&
                profileDTO.getRating() == null && profileDTO.getPassword() == null){
            throw new InvalidParameterException(
                    "Either password, description, banned flag or rating must be passed to update");
        }

        return userProfileRepository.findById(id).map(userProfile -> {
            if(profileDTO.getBanned() != null){
                userProfile.setBanned(profileDTO.getBanned());
            }
            if(profileDTO.getDescription() != null){
                userProfile.setDescription(profileDTO.getDescription());
            }
            if(profileDTO.getRating() != null){
                userProfile.addRating(profileDTO.getRating());
            }
            if(profileDTO.getPassword() != null){
                userProfile.setPassword(passwordEncoder.encode(profileDTO.getPassword()));
            }

            return profileMapper.profileToProfileDTO(userProfileRepository.save(userProfile));
        }).orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteById(Long id) {
        userProfileRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        userProfileRepository.deleteById(id);
    }


}

package world.pointsofinterest.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import world.pointsofinterest.api.v1.mappers.CommentMapper;
import world.pointsofinterest.api.v1.mappers.ImageMapper;
import world.pointsofinterest.api.v1.mappers.ProfileMapper;
import world.pointsofinterest.api.v1.model.ProfileDTO;
import world.pointsofinterest.model.Authority;
import world.pointsofinterest.model.Role;
import world.pointsofinterest.model.UserProfile;
import world.pointsofinterest.repositories.RoleRepository;
import world.pointsofinterest.repositories.UserProfileRepository;
import world.pointsofinterest.services.interfaces.UserProfileService;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserProfileServiceImplTest {

    @Mock
    private UserProfileRepository userProfileRepository;

    @Mock
    private RoleRepository roleRepository;

    private UserProfileService userProfileService;

    //Test data
    private UserProfile testUserProfile1;
    private Role testRole;

    @BeforeEach
    void setUp() {
        userProfileService = new UserProfileServiceImpl(userProfileRepository, roleRepository, new ProfileMapper(
                new CommentMapper(), new ImageMapper()
        ));
        userProfileService.setPasswordEncoder(new BCryptPasswordEncoder());

        //Set up user profiles
        testUserProfile1 = new UserProfile(1L, "Test userProfile 1", 10.0,
                "test_user1", "1234", null, false );
        testRole = new Role();
        testRole.setAuthority(Authority.USER);
        testRole.setUserProfiles(Set.of(testUserProfile1));
        testUserProfile1.setRoles(Set.of(testRole));
    }

    @Test
    void findAll() {
        when(userProfileRepository.findAll()).thenReturn(List.of(testUserProfile1, testUserProfile1));

        List<ProfileDTO> profileDTOS = userProfileService.findAll();

        assertNotNull(profileDTOS);
        assertEquals(2, profileDTOS.size());
    }

    @Test
    void findById() {
        when(userProfileRepository.findById(1L)).thenReturn(Optional.of(testUserProfile1));

        ProfileDTO profileDTO = userProfileService.findById(1L);

        assertNotNull(profileDTO);
        assertEquals(1, profileDTO.getId());

        assertThrows(ResourceNotFoundException.class, () -> userProfileService.findById(2L));
    }

    @Test
    void save() {
        when(userProfileRepository.save(any(UserProfile.class))).thenAnswer(ans -> ans.getArguments()[0]);
        when(roleRepository.findByAuthorityIn(List.of(Authority.USER))).thenReturn(List.of(testRole));

        ProfileDTO profileDTO = new ProfileDTO(null, testUserProfile1.getUsername(), testUserProfile1.getPassword(),
                Set.of(Authority.USER.name()), false, testUserProfile1.getDescription(), testUserProfile1.getRating(), null, null);
        ProfileDTO savedProfile = userProfileService.save(profileDTO );

        assertNotNull(savedProfile);
        verify(userProfileRepository).save(any(UserProfile.class));
        assertEquals(profileDTO.getUsername(), savedProfile.getUsername());
        assertEquals(profileDTO.getBanned(), savedProfile.getBanned());
        assertEquals(profileDTO.getDescription(), savedProfile.getDescription());
        assertEquals(profileDTO.getRating(), savedProfile.getRating());

        assertThrows(InvalidParameterException.class, () -> userProfileService.save(null));
    }

    @Test
    void update() {
        when(userProfileRepository.save(any(UserProfile.class))).thenAnswer(ans -> ans.getArguments()[0]);
        when(userProfileRepository.findById(1L)).thenReturn(Optional.of(testUserProfile1));

        ProfileDTO profileDTO = new ProfileDTO(null, null, null, null, true,
                "New description", 3.5, null, null);
        ProfileDTO savedProfile = userProfileService.update(1L, profileDTO);

        assertNotNull(savedProfile);
        verify(userProfileRepository).save(any(UserProfile.class));
        assertEquals(profileDTO.getBanned(), savedProfile.getBanned());
        assertEquals(profileDTO.getDescription(), savedProfile.getDescription());
        assertEquals(6.75, savedProfile.getRating());

        assertThrows(InvalidParameterException.class, () -> userProfileService.update(null, profileDTO));
        assertThrows(InvalidParameterException.class, () -> userProfileService.update(1L,
                new ProfileDTO( null, null, null, null, null, null, null, null, null )));
    }

    @Test
    void deleteById() {
        when(userProfileRepository.findById(1L)).thenReturn(Optional.of(testUserProfile1));
        userProfileService.deleteById(1L);

        verify(userProfileRepository).deleteById(1L);
    }
}
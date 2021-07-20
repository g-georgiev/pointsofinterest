package world.pointsofinterest.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import world.pointsofinterest.api.v1.mappers.CommentMapper;
import world.pointsofinterest.api.v1.mappers.ImageMapper;
import world.pointsofinterest.api.v1.mappers.ProfileMapper;
import world.pointsofinterest.api.v1.model.ProfileDTO;
import world.pointsofinterest.model.Profile;
import world.pointsofinterest.model.User;
import world.pointsofinterest.repositories.ProfileRepository;
import world.pointsofinterest.services.interfaces.UserProfileService;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserProfileServiceImplTest {

    @Mock
    private ProfileRepository profileRepository;

    private UserProfileService userProfileService;

    //Test data
    private Profile testProfile1;

    @BeforeEach
    void setUp() {
        userProfileService = new UserProfileServiceImpl(profileRepository, new ProfileMapper(
                new CommentMapper(), new ImageMapper()
        ));

        //Set up user profiles
        User user = new User();
        testProfile1 = new Profile(1L, "Test profile 1", 10.0, user);
        user.setId(1L);
        user.setUsername("test_user1");
        user.setProfile(testProfile1);
    }

    @Test
    void findAll() {
        when(profileRepository.findAll()).thenReturn(List.of(testProfile1, testProfile1));

        List<ProfileDTO> profileDTOS = userProfileService.findAll();

        assertNotNull(profileDTOS);
        assertEquals(2, profileDTOS.size());
    }

    @Test
    void findById() {
        when(profileRepository.findById(1L)).thenReturn(Optional.of(testProfile1));

        ProfileDTO profileDTO = userProfileService.findById(1L);

        assertNotNull(profileDTO);
        assertEquals(1, profileDTO.getId());

        assertThrows(ResourceNotFoundException.class, () -> userProfileService.findById(2L));
    }

    @Test
    void save() {
        when(profileRepository.save(any(Profile.class))).thenAnswer(ans -> ans.getArguments()[0]);

        ProfileDTO profileDTO = new ProfileDTO(null, testProfile1.getUsername(), false,
                testProfile1.getDescription(), testProfile1.getRating(), null, null);
        ProfileDTO savedProfile = userProfileService.save(profileDTO);

        assertNotNull(savedProfile);
        verify(profileRepository).save(any(Profile.class));
        assertEquals(profileDTO.getUsername(), savedProfile.getUsername());
        assertEquals(profileDTO.getBanned(), savedProfile.getBanned());
        assertEquals(profileDTO.getDescription(), savedProfile.getDescription());
        assertEquals(profileDTO.getRating(), savedProfile.getRating());

        assertThrows(InvalidParameterException.class, () -> userProfileService.save(null));
    }

    @Test
    void update() {
        when(profileRepository.save(any(Profile.class))).thenAnswer(ans -> ans.getArguments()[0]);
        when(profileRepository.findById(1L)).thenReturn(Optional.of(testProfile1));

        ProfileDTO profileDTO = new ProfileDTO(null, null, true,
                "New description", 3.5, null, null);
        ProfileDTO savedProfile = userProfileService.update(1L, profileDTO);

        assertNotNull(savedProfile);
        verify(profileRepository).save(any(Profile.class));
        assertEquals(profileDTO.getBanned(), savedProfile.getBanned());
        assertEquals(profileDTO.getDescription(), savedProfile.getDescription());
        assertEquals(6.75, savedProfile.getRating());

        assertThrows(InvalidParameterException.class, () -> userProfileService.update(null, profileDTO));
        assertThrows(InvalidParameterException.class, () -> userProfileService.update(1L,
                new ProfileDTO( null, null, null, null, null, null, null )));
    }

    @Test
    void deleteById() {
        when(profileRepository.findById(1L)).thenReturn(Optional.of(testProfile1));
        userProfileService.deleteById(1L);

        verify(profileRepository).deleteById(1L);
    }
}
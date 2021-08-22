package world.pointsofinterest.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import world.pointsofinterest.api.v1.mappers.ImageMapper;
import world.pointsofinterest.api.v1.model.ImageDTO;
import world.pointsofinterest.model.*;
import world.pointsofinterest.repositories.ImageRepository;
import world.pointsofinterest.repositories.POIRepository;
import world.pointsofinterest.repositories.UserProfileRepository;
import world.pointsofinterest.services.interfaces.ImageService;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ImageServiceImplTest {

    @Mock
    private POIRepository poiRepository;
    @Mock
    private UserProfileRepository userProfileRepository;
    @Mock
    private ImageRepository imageRepository;

    private ImageService imageService;

    private Image testImage1;
    private Image testImage2;
    private UserProfile testUserProfile1;
    private POI testPOI1;
    private final String imageData = "some base64 encoded image";

    @BeforeEach
    void setUp() throws MalformedURLException {
        //Set up services
        imageService = new ImageServiceImpl(imageRepository, userProfileRepository, poiRepository, new ImageMapper());

        //Set up user profiles
        testUserProfile1 = new UserProfile(1L, "Test userProfile 1", 10.0,
                "test_user1", "1234", null, false );

        //Set up POIs
        ProfilePOI profilePOI = new ProfilePOI();
        profilePOI.setId(1L);
        profilePOI.setProfile(testUserProfile1);
        Set<ProfilePOI> profilePOIS = new HashSet<>();
        profilePOIS.add(profilePOI);
        testPOI1 = new POI(1L, "test POI", 10.0, 45.5, 90D, null, profilePOIS);
        profilePOI.setPoi(testPOI1);
        testUserProfile1.addProfilePOI(profilePOI);

        //Set up images
        byte[] imageBin = imageData.getBytes();
        Byte[] imageBinBox = new Byte[imageBin.length];
        for(int i = 0; i < imageBin.length; i++) {
            imageBinBox[i] = imageBin[i];
        }
        testImage1 = new Image( 1L, "Test image 1", 2.3, null, imageBinBox, testPOI1, null, null);

        testImage2 = new Image(2L, "Test image 2", 3.5, new URL("https://grid.gograph.com/big-or-small-size-matters-stock-illustration_gg77801625.jpg"), null, null, testUserProfile1, null);

        testPOI1.addImage(testImage1);
        testUserProfile1.addImage(testImage2);
    }

    @Test
    void findById() {
        when(imageRepository.findById(1L)).thenReturn(Optional.of(testImage1));

        ImageDTO imageDTO = imageService.findById(1L);

        assertNotNull(imageDTO);
        assertEquals(1, imageDTO.getId());

        assertThrows(ResourceNotFoundException.class, () -> imageService.findById(2L));
    }

    @Test
    void findAll() {
        when(imageRepository.findAll()).thenReturn(List.of(testImage1, testImage2));

        List<ImageDTO> allImages = imageService.findAll();

        assertNotNull(allImages);
        assertEquals(2, allImages.size());
    }

    @Test
    void findAllByPOI() {
        when(poiRepository.findById(1L)).thenReturn(Optional.of(testPOI1));

        List<ImageDTO> allImages = imageService.findAllByPOI(1L);

        assertNotNull(allImages);
        assertEquals(1, allImages.size());
        assertEquals(1, allImages.get(0).getId());

        assertThrows(ResourceNotFoundException.class, () -> imageService.findAllByPOI(2L));
    }

    @Test
    void findAllByProfile() {
        when(userProfileRepository.findById(1L)).thenReturn(Optional.of(testUserProfile1));

        List<ImageDTO> allImages = imageService.findAllByProfile(1L);

        assertNotNull(allImages);
        assertEquals(1, allImages.size());
        assertEquals(2, allImages.get(0).getId());

        assertThrows(ResourceNotFoundException.class, () -> imageService.findAllByProfile(2L));
    }

    @Test
    void save() {
        when(imageRepository.save(any(Image.class))).thenAnswer(ans -> ans.getArguments()[0]);
        when(poiRepository.findById(1L)).thenReturn(Optional.of(testPOI1));
        when(userProfileRepository.findById(1L)).thenReturn(Optional.of(testUserProfile1));

        ImageDTO imageToSave = new ImageDTO(
                null, imageData.getBytes(), null, testImage1.getDescription(), testImage1.getRating(),
                1L, 1L
        );

        ImageDTO savedImage = imageService.save(imageToSave);

        assertNotNull(savedImage);
        verify(imageRepository).save(any(Image.class));
        assertEquals(imageToSave.getData().length, savedImage.getData().length);
        assertEquals(imageToSave.getDescription(), savedImage.getDescription());
        assertEquals(imageToSave.getRating(), savedImage.getRating());
        assertEquals(imageToSave.getPoiId(), savedImage.getPoiId());
        assertEquals(imageToSave.getProfileId(), savedImage.getProfileId());

        assertThrows(InvalidParameterException.class, () -> imageService.save(null));
        imageToSave.setPoiId(3L);
        assertThrows(ResourceNotFoundException.class, () -> imageService.save(imageToSave));
        imageToSave.setProfileId(3L);
        assertThrows(ResourceNotFoundException.class, () -> imageService.save(imageToSave));
    }

    @Test
    void update() {
        when(imageRepository.save(any(Image.class))).thenAnswer(ans -> ans.getArguments()[0]);
        when(imageRepository.findById(1L)).thenReturn(Optional.of(new Image()));
        when(poiRepository.findById(1L)).thenReturn(Optional.of(testPOI1));
        when(userProfileRepository.findById(1L)).thenReturn(Optional.of(testUserProfile1));

        ImageDTO imageToSave = new ImageDTO(
                null, null, null, testImage2.getDescription(), testImage2.getRating(),
                1L, 1L
        );

        ImageDTO savedImage = imageService.update(1L, imageToSave);

        assertNotNull(savedImage);
        verify(imageRepository).save(any(Image.class));
        assertEquals(imageToSave.getDescription(), savedImage.getDescription());
        assertEquals(1.75, savedImage.getRating());
        assertEquals(imageToSave.getPoiId(), savedImage.getPoiId());
        assertEquals(imageToSave.getProfileId(), savedImage.getProfileId());

        assertThrows(InvalidParameterException.class, () -> imageService.update(null,null));
        assertThrows(InvalidParameterException.class, () -> imageService.update(1L,
                new ImageDTO(null, null, null, null, null, null, null)));
        imageToSave.setPoiId(3L);
        assertThrows(ResourceNotFoundException.class, () -> imageService.update(1L, imageToSave));
        imageToSave.setProfileId(3L);
        assertThrows(ResourceNotFoundException.class, () -> imageService.update(1L, imageToSave));
    }

    @Test
    void deleteById() {
        when(imageRepository.findById(1L)).thenReturn(Optional.of(testImage1));
        imageService.deleteById(1L);

        verify(imageRepository).deleteById(1L);
    }
}
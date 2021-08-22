package world.pointsofinterest.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import world.pointsofinterest.api.v1.mappers.*;
import world.pointsofinterest.api.v1.model.POIRequestDTO;
import world.pointsofinterest.api.v1.model.POIResponseDTO;
import world.pointsofinterest.model.*;
import world.pointsofinterest.repositories.CategoryRepository;
import world.pointsofinterest.repositories.POIRepository;
import world.pointsofinterest.repositories.UserProfileRepository;
import world.pointsofinterest.services.interfaces.POIService;

import java.security.InvalidParameterException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class POIServiceImplTest {

    @Mock
    private POIRepository poiRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private UserProfileRepository userProfileRepository;

    private POIService poiService;

    //Testing data
    private Category testCategory1;
    private UserProfile testUserProfile1;
    private UserProfile testUserProfile2;
    private final List<POI> testPOIList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        //Set up services
        poiService = new POIServiceImpl(poiRepository,
                categoryRepository,
                userProfileRepository,
                new POIMapper(new CommentMapper(), new ProfileMapper(new CommentMapper(), new ImageMapper()),
                        new CategoryMapper(), new ImageMapper()));

        //Set up category
        testCategory1 = new Category(1L, "Sites", "Sites");

        //Set up user profiles
        testUserProfile1 = new UserProfile(1L, "Test userProfile 1", 10.0,
                "test_user1", "1234", null, false );

        testUserProfile2 = new UserProfile(2L, "Test userProfile 2", 8.8,
                "test_user2", "1234", null, false );

        //Set up POIs
        for (long i = 1; i < 4; i++) {
            ProfilePOI profilePOI = new ProfilePOI();
            profilePOI.setId(i);
            profilePOI.setProfile(testUserProfile1);
            Set<Category> categories = new HashSet<>();
            categories.add(testCategory1);
            Set<ProfilePOI> profilePOIS = new HashSet<>();
            profilePOIS.add(profilePOI);

            POI poi = new POI(i, "test POI " + i, 10.0 / i, 45.5 + i, 90D + i,
                    categories, profilePOIS);
            profilePOI.setPoi(poi);

            testUserProfile1.addProfilePOI(profilePOI);
            testPOIList.add(poi);
            testCategory1.addPOI(poi);
        }

    }

    @Test
    void findAll() {
        when(poiRepository.findAll()).thenReturn(testPOIList);

        List<POIResponseDTO> allPOIs = poiService.findAll();

        assertNotNull(allPOIs);
        assertEquals(3, allPOIs.size());
    }

    @Test
    void findById() {
        when(poiRepository.findById(1L)).thenReturn(Optional.of(testPOIList.get(0)));

        POIResponseDTO poiResponseDTO = poiService.findById(1L);

        assertNotNull(poiResponseDTO);
        assertEquals(1, poiResponseDTO.getId());

        assertThrows(ResourceNotFoundException.class, () -> poiService.findById(2L));
    }

    @Test
    void findAllByCategory() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(testCategory1));

        List<POIResponseDTO> allPOIs = poiService.findAllByCategory(1L);

        assertNotNull(allPOIs);
        assertEquals(3, allPOIs.size());

        assertThrows(ResourceNotFoundException.class, () -> poiService.findAllByCategory(2L));
    }

    @Test
    void findAllPOIsByProfile() {
        when(userProfileRepository.findById(1L)).thenReturn(Optional.of(testUserProfile1));

        List<POIResponseDTO> allPOIs = poiService.findAllPOIsByProfile(1L, false);

        assertNotNull(allPOIs);
        assertEquals(3, allPOIs.size());

        assertThrows(ResourceNotFoundException.class, () -> poiService.findAllPOIsByProfile(2L, false));
    }

    @Test
    void findAllByRange() {
        when(poiRepository.findByLatitudeBetweenAndLongitudeBetween(
                anyDouble(), anyDouble(), anyDouble(), anyDouble())).thenReturn(testPOIList);

        assertThrows(InvalidParameterException.class, () -> poiService.findAllByRange(
                124.321,322.123, 111.11
        ));

        List<POIResponseDTO> POIsInRange1 = poiService.findAllByRange(
                46.5,91D, 111.11
        );

        List<POIResponseDTO> POIsInRange2 = poiService.findAllByRange(
                46.5,91D, 135D
        );

        List<POIResponseDTO> POIsInRange3 = poiService.findAllByRange(
                46.5,91D, 270D
        );

        assertNotNull(POIsInRange1);
        assertNotNull(POIsInRange2);
        assertNotNull(POIsInRange3);
        assertEquals(1, POIsInRange1.size());
        assertEquals(2, POIsInRange2.size());
        assertEquals(3, POIsInRange3.size());
    }

    @Test
    void checkIn() {
        when(poiRepository.findById(1L)).thenReturn(Optional.of(testPOIList.get(0)));
        when(poiRepository.save(any(POI.class))).thenReturn(testPOIList.get(0));
        when(userProfileRepository.findById(2L)).thenReturn(Optional.of(testUserProfile2));

        POIResponseDTO poiResponseDTO = poiService.checkIn(1L, 2L);

        assertNotNull(poiResponseDTO);
        assertEquals(1, poiResponseDTO.getCheckIns().size());
    }

    @Test
    void save() {
        when(poiRepository.save(any(POI.class))).thenAnswer(ans -> ans.getArguments()[0]);
        when(categoryRepository.findByIdIn(List.of(1L))).thenReturn(List.of(testCategory1));
        when(userProfileRepository.findByIdIn(List.of(1L, 2L))).thenReturn(List.of(testUserProfile1, testUserProfile2));

        POIRequestDTO poiRequestDTO = new POIRequestDTO(
                null, 30.34, 160.65, "test POI", 10D, List.of(1L), List.of(1L, 2L)
        );
        POIResponseDTO poiResponseDTO = poiService.save(poiRequestDTO);

        assertNotNull(poiResponseDTO);
        verify(poiRepository).save(any(POI.class));
        assertEquals(poiRequestDTO.getLatitude(), poiResponseDTO.getLatitude());
        assertEquals(poiRequestDTO.getLongitude(), poiResponseDTO.getLongitude());
        assertEquals(poiRequestDTO.getDescription(), poiResponseDTO.getDescription());
        assertEquals(poiRequestDTO.getPosters().size(), poiResponseDTO.getPosters().size());
        assertEquals(poiRequestDTO.getCategories().size(), poiResponseDTO.getCategories().size());

        assertThrows(InvalidParameterException.class, () -> poiService.save(null));
        assertThrows(InvalidParameterException.class, () -> poiService.save(new POIRequestDTO(
                null,30.34, 160.65, "test POI", 10D, null, null)
        ));
        assertThrows(InvalidParameterException.class, () -> poiService.save(new POIRequestDTO(
                null,30.34, 160.65, "test POI", 10D, List.of(1L), null)
        ));
    }

    @Test
    void update() {
        when(poiRepository.save(any(POI.class))).thenAnswer(ans -> ans.getArguments()[0]);
        when(poiRepository.findById(1L)).thenReturn(Optional.of(testPOIList.get(0)));

        POIRequestDTO poiRequestDTO = new POIRequestDTO(
                null, null, null, "test POI", 10D, null, null
        );
        POIResponseDTO poiResponseDTO = poiService.update(1L, poiRequestDTO);

        assertNotNull(poiResponseDTO);
        verify(poiRepository).save(any(POI.class));
        assertEquals(poiRequestDTO.getRating(), poiResponseDTO.getRating());
        assertEquals(poiRequestDTO.getDescription(), poiResponseDTO.getDescription());

        assertThrows(InvalidParameterException.class, () -> poiService.update(null, poiRequestDTO));
        assertThrows(InvalidParameterException.class, () -> poiService.update(1L, new POIRequestDTO(
                null, null, null, null, null, null, null
        )));
    }

    @Test
    void deleteById() {
        when(poiRepository.findById(1L)).thenReturn(Optional.of(testPOIList.get(0)));
        poiService.deleteById(1L);

        verify(poiRepository).deleteById(1L);
    }
}
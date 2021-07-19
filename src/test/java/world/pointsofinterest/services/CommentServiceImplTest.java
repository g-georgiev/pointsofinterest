package world.pointsofinterest.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import world.pointsofinterest.api.v1.mappers.CommentMapper;
import world.pointsofinterest.api.v1.model.CommentDTO;
import world.pointsofinterest.api.v1.model.ImageDTO;
import world.pointsofinterest.model.*;
import world.pointsofinterest.repositories.CommentRepository;
import world.pointsofinterest.repositories.ImageRepository;
import world.pointsofinterest.repositories.POIRepository;
import world.pointsofinterest.repositories.ProfileRepository;
import world.pointsofinterest.services.interfaces.CommentService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @Mock
    private POIRepository poiRepository;
    @Mock
    private ProfileRepository profileRepository;
    @Mock
    private CommentRepository commentRepository;

    private CommentService commentService;

    private Comment testComment1;
    private Comment testComment2;
    private Profile testProfile1;
    private POI testPOI1;

    @BeforeEach
    void setUp() {
        //Set up services
        commentService = new CommentServiceImpl(commentRepository, profileRepository, poiRepository, new CommentMapper());

        //Set up user profiles
        User user = new User();
        testProfile1 = new Profile(1L, "Test profile 1", 10.0, user);
        user.setId(1L);
        user.setUsername("test_user1");
        user.setProfile(testProfile1);

        //Set up POIs
        ProfilePOI profilePOI = new ProfilePOI();
        profilePOI.setId(1L);
        profilePOI.setProfile(testProfile1);
        Set<ProfilePOI> profilePOIS = new HashSet<>();
        profilePOIS.add(profilePOI);
        testPOI1 = new POI(1L, "test POI", 10.0, 45.5, 90D, null, profilePOIS);
        profilePOI.setPoi(testPOI1);
        testProfile1.addProfilePOI(profilePOI);

        //Set up comments
        testComment1 = new Comment(1L, "Test comment 1", testProfile1, testPOI1, null);
        testComment2 = new Comment(2L, "Test comment 2", testProfile1, null, testProfile1);

        testPOI1.addComment(testComment1);
        testProfile1.addComment(testComment2);
        testProfile1.setPostedComments(List.of(testComment2));
    }

    @Test
    void findAllByPOI() {
        when(poiRepository.findById(1L)).thenReturn(Optional.of(testPOI1));

        List<CommentDTO> allComments = commentService.findAllByPOI(1L);

        assertNotNull(allComments);
        assertEquals(1, allComments.size());
        assertEquals(1, allComments.get(0).getId());

        assertThrows(ResourceNotFoundException.class, () -> commentService.findAllByPOI(2L));
    }

    @Test
    void findAllByProfile() {
        when(profileRepository.findById(1L)).thenReturn(Optional.of(testProfile1));

        List<CommentDTO> allComments = commentService.findAllByProfile(1L);

        assertNotNull(allComments);
        assertEquals(1, allComments.size());
        assertEquals(2, allComments.get(0).getId());

        assertThrows(ResourceNotFoundException.class, () -> commentService.findAllByProfile(2L));
    }

    @Test
    void findAllByPoster() {
        when(profileRepository.findById(1L)).thenReturn(Optional.of(testProfile1));

        List<CommentDTO> allComments = commentService.findAllByPoster(1L);

        assertNotNull(allComments);
        assertEquals(1, allComments.size());
        assertEquals(2, allComments.get(0).getId());

        assertThrows(ResourceNotFoundException.class, () -> commentService.findAllByPoster(2L));
    }

    @Test
    void findAll() {
        when(commentRepository.findAll()).thenReturn(List.of(testComment1, testComment2));

        List<CommentDTO> allComments = commentService.findAll();

        assertNotNull(allComments);
        assertEquals(2, allComments.size());
    }

    @Test
    void findById() {
        when(commentRepository.findById(1L)).thenReturn(Optional.of(testComment1));

        CommentDTO commentDTO = commentService.findById(1L);

        assertNotNull(commentDTO);
        assertEquals(1, commentDTO.getId());

        assertThrows(ResourceNotFoundException.class, () -> commentService.findById(2L));
    }

    @Test
    void save() {
    }

    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }
}
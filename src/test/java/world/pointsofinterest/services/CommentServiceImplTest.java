package world.pointsofinterest.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import world.pointsofinterest.api.v1.mappers.CommentMapper;
import world.pointsofinterest.api.v1.model.CommentDTO;
import world.pointsofinterest.model.*;
import world.pointsofinterest.repositories.CommentRepository;
import world.pointsofinterest.repositories.POIRepository;
import world.pointsofinterest.repositories.UserProfileRepository;
import world.pointsofinterest.services.interfaces.CommentService;

import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @Mock
    private POIRepository poiRepository;
    @Mock
    private UserProfileRepository userProfileRepository;
    @Mock
    private CommentRepository commentRepository;

    private CommentService commentService;

    private Comment testComment1;
    private Comment testComment2;
    private UserProfile testUserProfile1;
    private POI testPOI1;

    @BeforeEach
    void setUp() {
        //Set up services
        commentService = new CommentServiceImpl(commentRepository, userProfileRepository, poiRepository, new CommentMapper());

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

        //Set up comments
        testComment1 = new Comment(1L, "Test comment 1", testUserProfile1, testPOI1, null);
        testComment2 = new Comment(2L, "Test comment 2", testUserProfile1, null, testUserProfile1);

        testPOI1.addComment(testComment1);
        testUserProfile1.addComment(testComment2);
        testUserProfile1.setPostedComments(List.of(testComment2));
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
        when(userProfileRepository.findById(1L)).thenReturn(Optional.of(testUserProfile1));

        List<CommentDTO> allComments = commentService.findAllByProfile(1L);

        assertNotNull(allComments);
        assertEquals(1, allComments.size());
        assertEquals(2, allComments.get(0).getId());

        assertThrows(ResourceNotFoundException.class, () -> commentService.findAllByProfile(2L));
    }

    @Test
    void findAllByPoster() {
        when(userProfileRepository.findById(1L)).thenReturn(Optional.of(testUserProfile1));

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
        when(commentRepository.save(any(Comment.class))).thenAnswer(ans -> ans.getArguments()[0]);
        when(poiRepository.findById(1L)).thenReturn(Optional.of(testPOI1));
        when(userProfileRepository.findById(1L)).thenReturn(Optional.of(testUserProfile1));

        CommentDTO commentToSave1 = new CommentDTO(
                null, testComment1.getComment(), testComment1.getPoster().getUsername(),
                testComment1.getPoster().getId(), testPOI1.getId(), null
        );
        CommentDTO commentToSave2 = new CommentDTO(
                null, testComment2.getComment(), testComment2.getPoster().getUsername(),
                testComment2.getPoster().getId(), null, testComment2.getProfile().getId()
        );

        CommentDTO savedComment1 = commentService.save(commentToSave1);
        CommentDTO savedComment2 = commentService.save(commentToSave2);

        assertNotNull(savedComment1);
        assertNotNull(savedComment2);
        verify(commentRepository, times(2)).save(any(Comment.class));
        assertEquals(commentToSave1.getComment(), savedComment1.getComment());
        assertEquals(commentToSave2.getComment(), savedComment2.getComment());
        assertEquals(commentToSave1.getPosterName(), savedComment1.getPosterName());
        assertEquals(commentToSave2.getPosterName(), savedComment2.getPosterName());
        assertEquals(commentToSave1.getPosterId(), savedComment1.getPosterId());
        assertEquals(commentToSave2.getPosterId(), savedComment2.getPosterId());
        assertEquals(commentToSave1.getPOIId(), savedComment1.getPOIId());
        assertEquals(commentToSave2.getPOIId(), savedComment2.getPOIId());
        assertEquals(commentToSave1.getProfileId(), savedComment1.getProfileId());
        assertEquals(commentToSave2.getProfileId(), savedComment2.getProfileId());

        assertThrows(InvalidParameterException.class, () -> commentService.save(null));
        commentToSave1.setPOIId(3L);
        assertThrows(ResourceNotFoundException.class, () -> commentService.save(commentToSave1));
        commentToSave2.setProfileId(3L);
        assertThrows(ResourceNotFoundException.class, () -> commentService.save(commentToSave2));
        commentToSave1.setProfileId(1L);
        assertThrows(InvalidParameterException.class, () -> commentService.save(commentToSave1));
    }

    @Test
    void update() {
        when(commentRepository.save(any(Comment.class))).thenAnswer(ans -> ans.getArguments()[0]);
        when(commentRepository.findById(1L)).thenReturn(Optional.of(testComment1));

        CommentDTO commentToSave = new CommentDTO(
                null, "New comment text", testComment1.getPoster().getUsername(),
                testComment1.getPoster().getId(), testPOI1.getId(), null
        );

        CommentDTO savedComment = commentService.update(1L, commentToSave);

        assertNotNull(savedComment);
        verify(commentRepository).save(any(Comment.class));
        assertEquals(commentToSave.getComment(), savedComment.getComment());

        assertThrows(InvalidParameterException.class, () -> commentService.update(null,null));
        assertThrows(InvalidParameterException.class, () -> commentService.update(1L,
                new CommentDTO( null, null, null, null, null, null )));
    }

    @Test
    void deleteById() {
        when(commentRepository.findById(1L)).thenReturn(Optional.of(testComment1));
        commentService.deleteById(1L);

        verify(commentRepository).deleteById(1L);
    }
}
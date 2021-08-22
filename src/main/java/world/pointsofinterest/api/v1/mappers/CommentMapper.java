package world.pointsofinterest.api.v1.mappers;

import org.springframework.stereotype.Component;
import world.pointsofinterest.api.v1.model.CommentDTO;
import world.pointsofinterest.model.Comment;
import world.pointsofinterest.model.POI;
import world.pointsofinterest.model.UserProfile;

@Component
public class CommentMapper {
    public CommentDTO commentToCommentDTO(Comment comment){
        Long POIId = null;
        Long profileId = null;
        if(comment.getPoi() != null) { POIId = comment.getPoi().getId(); }
        if(comment.getProfile() != null) { profileId = comment.getProfile().getId(); }

        return new CommentDTO(comment.getId(), comment.getComment(), comment.getPoster().getUsername(),
                comment.getPoster().getId(), POIId, profileId);
    }

    public Comment commentDTOToComment(CommentDTO commentDTO, UserProfile poster, POI poi, UserProfile userProfile){
        return new Comment(commentDTO.getId(), commentDTO.getComment(), poster, poi, userProfile);
    }
}

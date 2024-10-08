package world.pointsofinterest.api.v1.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

public class CommentDTO {
    @Positive(message = "ID must be positive")
    private Long id;

    @NotNull(message = "Comment is a required property")
    @Size(min = 10, max = 300, message = "Comment must be between 10 and 300 characters long")
    private String comment;

    @NotNull(message = "Poster name is a required property")
    @NotEmpty(message = "Poster name cannot be an empty string")
    private String posterName;

    @Positive(message = "ID must be positive")
    private Long posterId;

    @Positive(message = "ID must be positive")
    private Long POIId;

    @Positive(message = "ID must be positive")
    private Long profileId;

    public CommentDTO(Long id, String comment, String posterName, Long posterId, Long poiId, Long profileId) {
        this.id = id;
        this.comment = comment;
        this.posterName = posterName;
        this.posterId = posterId;
        POIId = poiId;
        this.profileId = profileId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPosterName() {
        return posterName;
    }

    public void setPosterName(String posterName) {
        this.posterName = posterName;
    }

    public Long getPosterId() {
        return posterId;
    }

    public void setPosterId(Long posterId) {
        this.posterId = posterId;
    }

    public Long getPOIId() {
        return POIId;
    }

    public void setPOIId(Long POIId) {
        this.POIId = POIId;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }
}

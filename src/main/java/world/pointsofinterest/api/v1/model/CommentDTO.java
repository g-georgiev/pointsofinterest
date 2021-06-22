package world.pointsofinterest.api.v1.model;

public class CommentDTO {
    private Long id;
    private String comment;
    private String posterName;
    private Long posterId;
    private Long POIId;
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

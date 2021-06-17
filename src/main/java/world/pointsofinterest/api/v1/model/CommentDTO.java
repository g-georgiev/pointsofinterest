package world.pointsofinterest.api.v1.model;

public class CommentDTO {
    private Long id;
    private String comment;
    private String posterName;
    private String posterURL;
    private String POIURL;
    private String profileURL;

    public CommentDTO(Long id, String comment, String posterName, String posterURL,
                      String POIURL, String profileURL) {
        this.id = id;
        this.comment = comment;
        this.posterName = posterName;
        this.posterURL = posterURL;
        this.POIURL = POIURL;
        this.profileURL = profileURL;
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

    public String getPosterURL() {
        return posterURL;
    }

    public void setPosterURL(String posterURL) {
        this.posterURL = posterURL;
    }

    public String getPOIURL() {
        return POIURL;
    }

    public void setPOIURL(String POIURL) {
        this.POIURL = POIURL;
    }

    public String getProfileURL() {
        return profileURL;
    }

    public void setProfileURL(String profileURL) {
        this.profileURL = profileURL;
    }
}

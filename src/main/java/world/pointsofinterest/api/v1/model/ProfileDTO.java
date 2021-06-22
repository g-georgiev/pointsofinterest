package world.pointsofinterest.api.v1.model;

import java.util.List;

public class ProfileDTO {
    private Long id;
    private String username;
    private Boolean banned;
    private String description;
    private Double rating;
    private List<CommentDTO> receivedComments;

    public ProfileDTO(Long id, String username, Boolean banned, String description, Double rating, List<CommentDTO> receivedComments) {
        this.id = id;
        this.username = username;
        this.banned = banned;
        this.description = description;
        this.rating = rating;
        this.receivedComments = receivedComments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public List<CommentDTO> getReceivedComments() {
        return receivedComments;
    }

    public void setReceivedComments(List<CommentDTO> receivedComments) {
        this.receivedComments = receivedComments;
    }
}

package world.pointsofinterest.api.v1.model;

import java.util.List;
import java.util.Set;

public class ProfileDTO {
    private Long id;
    private String username;
    private Boolean banned;
    private String description;
    private Double rating;
    private Set<ImageDTO> images;
    private List<CommentDTO> receivedComments;

    public ProfileDTO(Long id, String username, Boolean banned, String description, Double rating, Set<ImageDTO> images, List<CommentDTO> receivedComments) {
        this.id = id;
        this.username = username;
        this.banned = banned;
        this.description = description;
        this.rating = rating;
        this.images = images;
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

    public Set<ImageDTO> getImages() {
        return images;
    }

    public void setImages(Set<ImageDTO> images) {
        this.images = images;
    }
}

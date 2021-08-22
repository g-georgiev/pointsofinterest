package world.pointsofinterest.api.v1.model;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Set;

public class ProfileDTO {
    @Positive(message = "ID must be positive")
    private Long id;

    @NotNull(message = "Username is a required property")
    @NotEmpty(message = "Username cannot be an empty string")
    private String username;

    @NotNull(message = "Password is a required property")
    @NotEmpty(message = "Password cannot be an empty string")
    private String password;

    @NotNull(message = "User authorities are a required property")
    private Set<@Pattern(regexp = "USER|MODERATOR|ADMIN",
                         message = "User authority accepted values are USER, MODERATOR or ADMIN")
                String> authorities;

    private Boolean banned;

    @NotNull(message = "Description is a required property")
    @NotEmpty(message = "Description cannot be an empty string")
    private String description;

    @NotNull(message = "Rating is a required property")
    @Range(min = 0, max = 10, message = "Rating must be between 0 and 10")
    private Double rating;

    private Set<ImageDTO> images;

    private List<CommentDTO> receivedComments;

    public ProfileDTO(Long id, String username, String password, Set<String> authorities, Boolean banned, String description, Double rating, Set<ImageDTO> images, List<CommentDTO> receivedComments) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
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

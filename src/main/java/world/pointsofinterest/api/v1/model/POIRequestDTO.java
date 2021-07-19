package world.pointsofinterest.api.v1.model;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;
import java.util.List;

public class POIRequestDTO {
    @Positive(message = "ID must be positive")
    private Long id;

    @NotNull(message = "Latitude is a required property")
    @Range(min = -90, max = 90, message = "Latitude must be between -90 and 90 degrees")
    private Double latitude;

    @NotNull(message = "Longitude is a required property")
    @Range(min = -180, max = 180, message = "Longitude must be between -180 and 180 degrees")
    private Double longitude;

    @NotNull(message = "Description is a required property")
    @NotEmpty(message = "Description cannot be an empty string")
    private String description;

    @NotNull(message = "Rating is a required property")
    @Range(min = 0, max = 10, message = "Rating must be between 0 and 10")
    private Double rating;

    @NotNull(message = "Every point of interest must have a category")
    @NotEmpty(message = "Every point of interest must have a category")
    private List<Long> categories;

    @NotNull(message = "Every point of interest must have a poster profile")
    @NotEmpty(message = "Every point of interest must have a poster profile")
    private List<Long> posters;

    public POIRequestDTO(Long id, Double latitude, Double longitude, String description,
                         Double rating, List<Long> categories, List<Long> posters) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.rating = rating;
        this.categories = categories;
        this.posters = posters;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
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

    public List<Long> getCategories() {
        return categories;
    }

    public void setCategories(List<Long> categories) {
        this.categories = categories;
    }

    public List<Long> getPosters() {
        return posters;
    }

    public void setPosters(List<Long> posters) {
        this.posters = posters;
    }
}

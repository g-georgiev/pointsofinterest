package world.pointsofinterest.api.v1.model;

import java.util.List;

public class POIRequestDTO {
    private Long id;
    private Double latitude;
    private Double longitude;
    private String description;
    private Double rating;
    private List<Long> categories;
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

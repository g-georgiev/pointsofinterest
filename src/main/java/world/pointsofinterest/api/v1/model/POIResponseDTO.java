package world.pointsofinterest.api.v1.model;

import java.util.Map;

public class POIResponseDTO {
    private Long id;
    private Double latitude;
    private Double longitude;
    private String description;
    private Double rating;
    private Boolean hasComments;
    //TODO: thumbnails
    //TODO: firstImage
    private Map<Long, String> images;
    private Map<Long, String> videos;
    private Map<Long, String> categories;
    private Map<Long, String> profiles;
    private Map<String, String> links;

    public POIResponseDTO(Long id, Double latitude, Double longitude, String description,
                          Double rating, Boolean hasComments, Map<Long, String> images,
                          Map<Long, String> videos, Map<Long, String> categories, Map<Long, String> profiles) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.rating = rating;
        this.hasComments = hasComments;
        this.images = images;
        this.videos = videos;
        this.categories = categories;
        this.profiles = profiles;
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

    public Boolean getHasComments() {
        return hasComments;
    }

    public void setHasComments(Boolean hasComments) {
        this.hasComments = hasComments;
    }

    public Map<Long, String> getImages() {
        return images;
    }

    public void setImages(Map<Long, String> images) {
        this.images = images;
    }

    public Map<Long, String> getVideos() {
        return videos;
    }

    public void setVideos(Map<Long, String> videos) {
        this.videos = videos;
    }

    public Map<Long, String> getCategories() {
        return categories;
    }

    public void setCategories(Map<Long, String> categories) {
        this.categories = categories;
    }

    public Map<Long, String> getProfiles() {
        return profiles;
    }

    public void setProfiles(Map<Long, String> profiles) {
        this.profiles = profiles;
    }

    public Map<String, String> getLinks() {
        return links;
    }

    public void setLinks(Map<String, String> links) {
        this.links = links;
    }
}

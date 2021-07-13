package world.pointsofinterest.api.v1.model;

import java.util.Set;

public class POIResponseDTO {
    private Long id;
    private Double latitude;
    private Double longitude;
    private String description;
    private Double rating;
    private Boolean hasComments;
    //TODO: thumbnails
    //TODO: firstImage
    private Set<ImageDTO> images;
    private Set<VideoDTO> videos;
    private Set<CategoryDTO> categories;
    private Set<ProfileDTO> posters;
    private Set<ProfileDTO> checkIns;
    private Set<CommentDTO> comments;

    public POIResponseDTO(Long id, Double latitude, Double longitude, String description, Double rating,
                          Boolean hasComments, Set<ImageDTO> images, Set<VideoDTO> videos,
                          Set<CategoryDTO> categories, Set<ProfileDTO> posters, Set<ProfileDTO> checkIns, Set<CommentDTO> comments) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.description = description;
        this.rating = rating;
        this.hasComments = hasComments;
        this.images = images;
        this.videos = videos;
        this.categories = categories;
        this.posters = posters;
        this.checkIns = checkIns;
        this.comments = comments;
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

    public Set<ImageDTO> getImages() {
        return images;
    }

    public void setImages(Set<ImageDTO> images) {
        this.images = images;
    }

    public Set<VideoDTO> getVideos() {
        return videos;
    }

    public void setVideos(Set<VideoDTO> videos) {
        this.videos = videos;
    }

    public Set<CategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryDTO> categories) {
        this.categories = categories;
    }

    public Set<ProfileDTO> getPosters() {
        return posters;
    }

    public void setPosters(Set<ProfileDTO> posters) {
        this.posters = posters;
    }

    public Set<ProfileDTO> getCheckIns() {
        return checkIns;
    }

    public void setCheckIns(Set<ProfileDTO> checkIns) {
        this.checkIns = checkIns;
    }

    public Set<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(Set<CommentDTO> comments) {
        this.comments = comments;
    }
}

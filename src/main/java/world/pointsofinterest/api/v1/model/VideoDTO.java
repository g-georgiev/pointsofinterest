package world.pointsofinterest.api.v1.model;

public class VideoDTO {
    private Long id;
    private String description;
    private Double rating;
    private String videoURL;

    public VideoDTO(Long id, String description, Double rating, String videoURL) {
        this.id = id;
        this.description = description;
        this.rating = rating;
        this.videoURL = videoURL;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }
}

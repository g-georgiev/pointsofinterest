package world.pointsofinterest.api.v1.model;

public class ImageDTO {
    private Long id;
    private String description;
    private Double rating;
    private String imageURL;

    public ImageDTO(Long id, String description, Double rating, String imageURL) {
        this.id = id;
        this.description = description;
        this.rating = rating;
        this.imageURL = imageURL;
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

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }
}

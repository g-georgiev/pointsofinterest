package world.pointsofinterest.api.v1.model;

public class ImageDTO {
    private Long id;
    private String description;
    private Double rating;

    public ImageDTO(Long id, String description, Double rating) {
        this.id = id;
        this.description = description;
        this.rating = rating;
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
}

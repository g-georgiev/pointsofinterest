package world.pointsofinterest.model.superclasses;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Post extends BaseEntity {

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "rating", nullable = false)
    private Double rating = 0.0;

    public Post(Long id, String description, Double rating) {
        super(id);
        this.description = description;
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public Double getRating() {
        return rating;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addRating(Double rating) {
        this.rating = (this.rating + rating) / 2;
    }
}

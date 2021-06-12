package world.pointsofinterest.model;

import world.pointsofinterest.model.superclasses.Post;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "poi")
public class POI extends Post {

    @Column(name = "latitude", nullable = false)
    private final Double latitude;

    @Column(name = "longitude", nullable = false)
    private final Double longitude;

    @OneToMany(cascade = CascadeType.DETACH, mappedBy = "poi")
    private Set<Video> videoSet = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "poi")
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "poi_categories", joinColumns = @JoinColumn(name = "poi_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "poi_profiles", joinColumns = @JoinColumn(name = "poi_id"),
            inverseJoinColumns = @JoinColumn(name = "profile_id"))
    private Set<Profile> profiles;

    public POI(Long id, String description, Double rating, Double latitude, Double longitude,
               Set<Category> categories, Set<Profile> profiles) {
        super(id, description, rating);
        this.latitude = latitude;
        this.longitude = longitude;
        this.categories = categories;
        this.profiles = profiles;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

//    public String getDescription() {
//        return super.getDescription();
//    }
//
//    public Double getRating() {
//        return super.getRating();
//    }

    public Set<Category> getCategories() {
        return categories;
    }

    public Set<Video> getVideoSet() {
        return videoSet;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public Set<Profile> getProfiles() {
        return profiles;
    }

//    public void setDescription(String description) {
//        super.setDescription(description);
//    }
//
//    public void addRating(Double rating) {
//        super.addRating(rating);
//    }

    public void addVideo(Video video) {
        videoSet.add(video);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }
}

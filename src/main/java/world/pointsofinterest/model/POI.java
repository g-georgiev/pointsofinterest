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
    private Double latitude;

    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "poi")
    private Set<Video> videoSet = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "poi")
    private Set<Image> imageSet = new HashSet<>();

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

    public POI() {
    }

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

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Set<Video> getVideoSet() {
        return videoSet;
    }

    public void setVideoSet(Set<Video> videoSet) {
        this.videoSet = videoSet;
    }

    public Set<Image> getImageSet() {
        return imageSet;
    }

    public void setImageSet(Set<Image> imageSet) {
        this.imageSet = imageSet;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(Set<Profile> profiles) {
        this.profiles = profiles;
    }

    public void addImage(Image Image) {
        imageSet.add(Image);
    }

    public void addVideo(Video video) {
        videoSet.add(video);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    @Override
    public String toString() {
        return "POI{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                ", videoSet=" + videoSet +
                ", imageSet=" + imageSet +
                ", comments=" + comments +
                ", categories=" + categories +
                ", profiles=" + profiles +
                '}';
    }
}

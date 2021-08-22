package world.pointsofinterest.model;

import world.pointsofinterest.model.superclasses.Post;

import javax.persistence.*;
import java.security.InvalidParameterException;
import java.util.*;

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

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "poi_profiles", joinColumns = @JoinColumn(name = "poi_id"),
//            inverseJoinColumns = @JoinColumn(name = "profile_id"))
//    private Set<Profile> profiles;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "poi")
    private Set<ProfilePOI> profilePOIS = new HashSet<>();

    @Column(name = "is_approved", nullable = false)
    private Boolean isApproved = false;

    public POI() {
    }

    public POI(Long id, String description, Double rating, Double latitude, Double longitude,
               Set<Category> categories, Set<ProfilePOI> profilePOIS) {
        super(id, description, rating);

        validateCoordinates(latitude, longitude);
        this.latitude = latitude;
        this.longitude = longitude;
        this.categories = categories;
        this.profilePOIS = profilePOIS;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        validateCoordinates(latitude, null);
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        validateCoordinates(null, longitude);
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

    public Set<ProfilePOI> getProfilePOIS() {
        return profilePOIS;
    }

    public void setProfilePOIS(Set<ProfilePOI> profilePOIS) {
        this.profilePOIS = profilePOIS;
    }

    public void addProfilePOIS(ProfilePOI profilePOI) {
        this.profilePOIS.add(profilePOI);
    }

    public Set<UserProfile> getProfiles(Boolean checkIn) {
        Objects.requireNonNull(checkIn);
        Set<UserProfile> userProfiles = new HashSet<>();

        profilePOIS.forEach(profilePOI -> {
            if(checkIn == profilePOI.getCheckIn()) {
                userProfiles.add(profilePOI.getProfile());}
        });

        return userProfiles;
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
                '}';
    }

    public Boolean getApproved() {
        return isApproved;
    }

    public void setApproved(Boolean approved) {
        isApproved = approved;
    }

    public static void validateCoordinates(Double latitude, Double longitude){
        if(latitude != null && (latitude < -90 || latitude > 90)) {
            throw new InvalidParameterException("Latitude must be between -90 and 90 degrees");
        }

        if(longitude!= null && (longitude < -180 || longitude > 180)) {
            throw new InvalidParameterException("Longitude must be between -90 and 90 degrees");
        }
    }
}

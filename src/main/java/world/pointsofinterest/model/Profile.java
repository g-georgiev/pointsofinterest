package world.pointsofinterest.model;

import world.pointsofinterest.model.superclasses.Post;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "profiles")
public class Profile extends Post {

    @Column(name = "banned", nullable = false)
    private Boolean banned = false;

    @JoinColumn(name = "user_id", nullable = false)
    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "poster")
    private List<Comment> postedComments = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "profile")
    private List<Comment> receivedComments = new ArrayList<>();

//    @ManyToMany(mappedBy = "profiles")
//    private Set<POI> postedPOIs = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "profile")
    private Set<ProfilePOI> profilePOIS = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "profile")
    private Set<Image> imageSet = new HashSet<>();

    public Profile() {
    }

    public Profile(Long id, String description, Double rating, User user) {
        super(id, description, rating);
        this.user = user;
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    public User getUser() {
        return user;
    }

    public List<Comment> getReceivedComments() {
        return receivedComments;
    }

    public List<Comment> getPostedComments() {
        return postedComments;
    }

    public Set<Image> getImageSet() {
        return imageSet;
    }

    public void setImageSet(Set<Image> imageSet) {
        this.imageSet = imageSet;
    }

    public void addImage(Image image) {
        this.imageSet.add(image);
    }

    public void setPostedComments(List<Comment> postedComments) {
        this.postedComments = postedComments;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setReceivedComments(List<Comment> receivedComments) {
        this.receivedComments = receivedComments;
    }

    public void addComment(Comment comment) {
        receivedComments.add(comment);
    }

    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public String toString() {
        return "Profile{" +
                "banned=" + banned +
                ", user=" + user +
                '}';
    }

    public Set<POI> getProfilePOIs(Boolean checkIn) {
        Objects.requireNonNull(checkIn);
        Set<POI> pois = new HashSet<>();

        profilePOIS.forEach(profilePOI -> {
            if(checkIn == profilePOI.getCheckIn()) { pois.add(profilePOI.getPoi()); }
        });

        return pois;
    }

    public void addProfilePOI(ProfilePOI profilePOI) {
        this.profilePOIS.add(profilePOI);
    }
}

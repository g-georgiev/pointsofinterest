package world.pointsofinterest.model;

import world.pointsofinterest.model.superclasses.Post;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @ManyToMany(mappedBy = "profiles")
    private Set<POI> postedPOIs = new HashSet<>();

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

    public Set<POI> getPostedPOIs() {
        return postedPOIs;
    }

    public List<Comment> getReceivedComments() {
        return receivedComments;
    }

    public List<Comment> getPostedComments() {
        return postedComments;
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

    public void setPostedPOIs(Set<POI> postedPOIs) {
        this.postedPOIs = postedPOIs;
    }

    public void addPOI(POI POI) {
        postedPOIs.add(POI);
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
}

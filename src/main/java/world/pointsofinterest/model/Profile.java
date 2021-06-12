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
    private final User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "profile")
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany(mappedBy = "profiles")
    private Set<POI> POIs = new HashSet<>();

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

    public Set<POI> getPOIs() {
        return POIs;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void addPOI(POI POI) {
        POIs.add(POI);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }
}

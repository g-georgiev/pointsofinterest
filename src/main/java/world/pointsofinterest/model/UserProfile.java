package world.pointsofinterest.model;

import org.springframework.security.core.userdetails.UserDetails;
import world.pointsofinterest.model.superclasses.Post;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "profiles")
public class UserProfile extends Post implements UserDetails {

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @Column(name = "banned", nullable = false)
    private Boolean banned = false;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "poster")
    private List<Comment> postedComments = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userProfile")
    private List<Comment> receivedComments = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userProfile")
    private Set<ProfilePOI> profilePOIS = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userProfile")
    private Set<Image> imageSet = new HashSet<>();

    public UserProfile() {
    }

    public UserProfile(Long id,
                       String description,
                       Double rating,
                       String username,
                       String password,
                       Set<Role> roles,
                       Boolean banned) {
        super(id, description, rating);
        this.username = username;
        this.password = password;
        this.roles = roles;
        this.banned = banned;
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
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

    public void setReceivedComments(List<Comment> receivedComments) {
        this.receivedComments = receivedComments;
    }

    public void addComment(Comment comment) {
        receivedComments.add(comment);
    }

    @Override
    public String toString() {
        return "Profile{" +
                "banned=" + banned +
                ", username=" + username +
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



    @Override
    public Set<Role> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !banned;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !banned;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
}

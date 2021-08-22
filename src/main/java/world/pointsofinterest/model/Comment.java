package world.pointsofinterest.model;

import world.pointsofinterest.model.superclasses.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "comments")
public class Comment extends BaseEntity {

    @Column(name = "comment", nullable = false)
    private String comment;

    @ManyToOne
    private UserProfile poster;

    @ManyToOne
    private POI poi;

    @ManyToOne
    private UserProfile userProfile;

    public Comment() {
    }

    public Comment(Long id, String comment, UserProfile poster, POI poi, UserProfile userProfile) {
        super(id);
        this.comment = comment;
        this.poster = poster;
        this.poi = poi;
        this.userProfile = userProfile;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public UserProfile getPoster() {
        return poster;
    }

    public void setPoster(UserProfile poster) {
        this.poster = poster;
    }

    public POI getPoi() {
        return poi;
    }

    public void setPoi(POI poi) {
        this.poi = poi;
    }

    public UserProfile getProfile() {
        return userProfile;
    }

    public void setProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "comment='" + comment + '\'' +
                ", poster=" + poster.getUsername() +
                ", poi=" + poi.getId() +
                ", userProfile=" + userProfile.getUsername() +
                '}';
    }
}

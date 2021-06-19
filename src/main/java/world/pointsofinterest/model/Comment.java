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
    private Profile poster;

    @ManyToOne
    private POI poi;

    @ManyToOne
    private Profile profile;

    public Comment() {
    }

    public Comment(Long id, String comment, Profile poster, POI poi, Profile profile) {
        super(id);
        this.comment = comment;
        this.poster = poster;
        this.poi = poi;
        this.profile = profile;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Profile getPoster() {
        return poster;
    }

    public void setPoster(Profile poster) {
        this.poster = poster;
    }

    public POI getPoi() {
        return poi;
    }

    public void setPoi(POI poi) {
        this.poi = poi;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}

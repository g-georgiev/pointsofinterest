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
    private final Profile poster;

    @ManyToOne
    private final POI poi;

    @ManyToOne
    private final Profile profile;

    public Comment(Long id, String comment, Profile poster, POI poi, Profile profile) {
        super(id);
        this.comment = comment;
        this.poster = poster;
        this.poi = poi;
        this.profile = profile;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public POI getPoi() {
        return poi;
    }

    public Profile getProfile() {
        return profile;
    }

    public Profile getPoster() {
        return poster;
    }
}

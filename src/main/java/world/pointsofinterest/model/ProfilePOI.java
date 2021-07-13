package world.pointsofinterest.model;

import world.pointsofinterest.model.superclasses.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class ProfilePOI extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "poi_id")
    private POI poi;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "check_in", nullable = false)
    private Boolean checkIn;

    public ProfilePOI() {
        this.createdAt = LocalDateTime.now();
        this.checkIn = false;
    }

    public ProfilePOI(POI poi, Profile profile, Boolean checkIn) {
        this.poi = poi;
        this.profile = profile;
        this.checkIn = checkIn;
        this.createdAt = LocalDateTime.now();
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Boolean getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Boolean checkIn) {
        this.checkIn = checkIn;
    }


}

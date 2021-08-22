package world.pointsofinterest.model;

import world.pointsofinterest.model.superclasses.BaseEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "profiles_pois")
public class ProfilePOI extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "poi_id")
    private POI poi;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private UserProfile userProfile;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "check_in", nullable = false)
    private Boolean checkIn;

    public ProfilePOI() {
        this.createdAt = LocalDateTime.now();
        this.checkIn = false;
    }

    public ProfilePOI(POI poi, UserProfile userProfile, Boolean checkIn) {
        this.poi = poi;
        this.userProfile = userProfile;
        this.checkIn = checkIn;
        this.createdAt = LocalDateTime.now();
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

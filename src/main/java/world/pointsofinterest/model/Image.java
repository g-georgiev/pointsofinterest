package world.pointsofinterest.model;

import world.pointsofinterest.model.superclasses.Multimedia;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.net.URL;

@Entity
@Table(name = "images")
public class Image extends Multimedia {

    @ManyToOne
    private POI poi;

    @ManyToOne
    private UserProfile userProfile;

    @OneToOne(mappedBy = "thumbnail")
    private Video video;

    public Image() {
    }

    public Image(Long id, String description, Double rating, URL url, Byte[] bin, POI poi,
                 UserProfile userProfile, Video video) {
        super(id, description, rating, url, bin);
        this.poi = poi;
        this.userProfile = userProfile;
        this.video = video;
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

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    @Override
    public String toString() {
        return "Image{" +
                "poi=" + poi.getId() +
                ", userProfile=" + userProfile.getUsername() +
                ", video=" + video.getId() +
                '}';
    }
}

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
    private Profile profile;

    @OneToOne(mappedBy = "thumbnail")
    private Video video;

    public Image() {
    }

    public Image(Long id, String description, Double rating, URL url, Byte[] bin, POI poi, Profile profile, Video video) {
        super(id, description, rating, url, bin);
        this.poi = poi;
        this.profile = profile;
        this.video = video;
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
                ", profile=" + profile.getUser().getUsername() +
                ", video=" + video.getId() +
                '}';
    }
}

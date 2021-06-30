package world.pointsofinterest.model;

import world.pointsofinterest.model.superclasses.Multimedia;

import javax.persistence.*;
import java.net.URL;

@Entity
@Table(name = "videos")
public class Video extends Multimedia {

    @ManyToOne
    private POI poi;

    @JoinColumn(name = "thumbnail_id")
    @OneToOne
    private Image thumbnail;

    public Video() {
    }

    public Video(Long id, String description, Double rating, URL url, Byte[] bin, POI poi, Image thumbnail) {
        super(id, description, rating, url, bin);
        this.poi = poi;
        this.thumbnail = thumbnail;
    }

    public void setPoi(POI poi) {
        this.poi = poi;
    }

    public Image getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Image thumbnail) {
        this.thumbnail = thumbnail;
    }

    public POI getPoi() {
        return poi;
    }

    @Override
    public String toString() {
        return "Video{" +
                "poi=" + poi.getId() +
                ", thumbnail=" + thumbnail.getId() +
                '}';
    }
}

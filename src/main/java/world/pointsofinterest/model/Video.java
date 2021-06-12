package world.pointsofinterest.model;

import world.pointsofinterest.model.superclasses.Multimedia;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.net.URL;

@Entity
@Table(name = "videos")
public class Video extends Multimedia {

    @ManyToOne
    private final POI poi;

    public Video(Long id, String description, Double rating, URL url, Byte[] bin, POI poi) {
        super(id, description, rating, url, bin);
        this.poi = poi;
    }

    public POI getPoi() {
        return poi;
    }
}

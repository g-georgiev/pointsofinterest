package world.pointsofinterest.model;

import world.pointsofinterest.model.superclasses.Multimedia;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.net.URL;

@Entity
@Table(name = "images")
public class Image extends Multimedia {

    @ManyToOne
    private final POI poi;

    @ManyToOne
    private final Profile profile;

    public Image(Long id, String description, Double rating, URL url, Byte[] bin, POI poi, Profile profile) {
        super(id, description, rating, url, bin);
        this.poi = poi;
        this.profile = profile;
    }

    public POI getPoi() {
        return poi;
    }

    public Profile getProfile() {
        return profile;
    }
}

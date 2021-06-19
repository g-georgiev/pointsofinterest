package world.pointsofinterest.model.superclasses;

import world.pointsofinterest.model.superclasses.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import java.net.URL;

@MappedSuperclass
public class Multimedia extends Post {

    @Column(name = "url")
    private URL url;

    @Column(name = "bin")
    @Lob
    private Byte[] bin;

    public Multimedia() {
    }

    public Multimedia(Long id, String description, Double rating, URL url, Byte[] bin) {
        super(id, description, rating);
        this.url = url;
        this.bin = bin;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public Byte[] getBin() {
        return bin;
    }

    public void setBin(Byte[] bin) {
        this.bin = bin;
    }
}

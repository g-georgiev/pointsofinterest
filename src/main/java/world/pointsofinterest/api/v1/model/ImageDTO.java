package world.pointsofinterest.api.v1.model;

import java.net.URL;

public class ImageDTO {
    private Long id;
    private byte[] data;
    private URL url;
    private String description;
    private Double rating;
    private Long poiId;
    private Long profileId;

    public ImageDTO(Long id, byte[] data, URL url, String description, Double rating, Long poiId, Long profileId) {
        this.id = id;
        this.data = data;
        this.url = url;
        this.description = description;
        this.rating = rating;
        this.poiId = poiId;
        this.profileId = profileId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Long getPoiId() {
        return poiId;
    }

    public void setPoiId(Long poiId) {
        this.poiId = poiId;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }
}

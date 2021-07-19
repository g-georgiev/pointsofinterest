package world.pointsofinterest.api.v1.model;

import org.hibernate.validator.constraints.Range;
import world.pointsofinterest.api.v1.constraints.CheckAtLeastOneNotNull;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.net.URL;

@CheckAtLeastOneNotNull(fieldNames = {"data", "url"}, message = "Either image data or URL must be provided")
public class ImageDTO {
    @Positive(message = "ID must be positive")
    private Long id;

    private byte[] data;

    private URL url;

    @NotNull(message = "Description is a required property")
    @NotEmpty(message = "Description cannot be an empty string")
    private String description;

    @NotNull(message = "Rating is a required property")
    @Range(min = 0, max = 10, message = "Rating must be between 0 and 10")
    private Double rating;

    @Positive(message = "ID must be positive")
    private Long poiId;

    @Positive(message = "ID must be positive")
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

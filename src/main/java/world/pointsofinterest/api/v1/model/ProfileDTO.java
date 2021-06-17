package world.pointsofinterest.api.v1.model;

public class ProfileDTO {
    private String username;
    private Boolean banned;
    private String profileURL;

    public ProfileDTO(String username, Boolean banned, String profileURL) {
        this.username = username;
        this.banned = banned;
        this.profileURL = profileURL;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }

    public String getProfileURL() {
        return profileURL;
    }

    public void setProfileURL(String profileURL) {
        this.profileURL = profileURL;
    }
}

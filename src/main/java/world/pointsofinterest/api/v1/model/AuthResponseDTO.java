package world.pointsofinterest.api.v1.model;

import java.io.Serializable;

public class AuthResponseDTO implements Serializable {

    private static final long serialVersionUID = -2956578301157569249L;

    private String accessToken;

    public AuthResponseDTO() {
    }

    public AuthResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}

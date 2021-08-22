package world.pointsofinterest.model;

public enum Authority {
    USER, MODERATOR, ADMIN;

    public static Authority findByString(String value) {
        for(Authority authority: Authority.values()) {
            if(authority.name().equalsIgnoreCase(value)){
                return authority;
            }
        }
        return USER;
    }
}

package world.pointsofinterest.model;

import org.springframework.security.core.GrantedAuthority;
import world.pointsofinterest.model.superclasses.BaseEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity implements GrantedAuthority {

    @Column(name = "authority", nullable = false, insertable = false, updatable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private Authority authority;

    @ManyToMany(mappedBy = "roles")
    private Set<UserProfile> userProfiles = new HashSet<>();

    public Role() {
    }

    @Override
    public String getAuthority() {
        return authority.name();
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

    public Set<UserProfile> getUserProfiles() {
        return userProfiles;
    }

    public void setUserProfiles(Set<UserProfile> userProfiles) {
        this.userProfiles = userProfiles;
    }
}

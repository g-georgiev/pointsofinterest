package world.pointsofinterest.services.interfaces;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import world.pointsofinterest.api.v1.model.ProfileDTO;

@Transactional
public interface UserProfileService extends CommonService<ProfileDTO, ProfileDTO, Long>, UserDetailsService {
    void setPasswordEncoder(PasswordEncoder passwordEncoder);
}

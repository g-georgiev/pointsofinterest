package world.pointsofinterest.services.interfaces;

import org.springframework.transaction.annotation.Transactional;
import world.pointsofinterest.api.v1.model.ProfileDTO;

@Transactional
public interface UserProfileService extends CommonService<ProfileDTO, ProfileDTO, Long> {
}

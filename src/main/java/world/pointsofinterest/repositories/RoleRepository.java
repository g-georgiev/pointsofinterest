package world.pointsofinterest.repositories;

import world.pointsofinterest.model.Authority;
import world.pointsofinterest.model.Role;

import java.util.List;

public interface RoleRepository extends ReadOnlyRepository<Role, Long>{
    List<Role> findByAuthorityIn(List<Authority> authority);
}

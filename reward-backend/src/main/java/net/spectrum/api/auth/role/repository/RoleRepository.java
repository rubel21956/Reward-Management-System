package net.spectrum.api.auth.role.repository;

import net.spectrum.api.auth.role.entity.RoleEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, String> {

    @NotFound(action= NotFoundAction.IGNORE)
    List<RoleEntity> findAll();

    @NotFound(action= NotFoundAction.IGNORE)
    RoleEntity findByRoleName(String roleName);

    @NotFound(action= NotFoundAction.IGNORE)
    RoleEntity findByRoleOid(String roleOid);

}

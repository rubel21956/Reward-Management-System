package net.spectrum.api.users.userrole.repository;

import net.spectrum.api.users.userrole.entity.UserRoleEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

// A repository for the UserRoleEntity.
@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleEntity, String> {
    @NotFound(action= NotFoundAction.IGNORE)
    List<UserRoleEntity> findAll();

    @NotFound(action= NotFoundAction.IGNORE)
    UserRoleEntity findByLoginOid(String loginOid);
}

package net.spectrum.api.users.userprofile.repository;

import java.util.List;
import net.spectrum.api.users.userprofile.entity.UserProfileEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfileEntity, String> {
    @NotFound(action = NotFoundAction.IGNORE)
    List<UserProfileEntity> findAll();

    @NotFound(action = NotFoundAction.IGNORE)
    UserProfileEntity findByOid(String oid);

    @NotFound(action = NotFoundAction.IGNORE)
    UserProfileEntity findByLoginOid(String loginOid);

//    UserProfileEntity findByUserId(String userId);
}


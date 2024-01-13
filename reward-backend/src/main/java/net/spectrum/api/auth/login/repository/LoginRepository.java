package net.spectrum.api.auth.login.repository;

import net.spectrum.api.auth.login.entity.LoginEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Creating a repository for the LoginEntity.
@Repository
public interface LoginRepository extends JpaRepository<LoginEntity, String> {

    @NotFound(action = NotFoundAction.IGNORE)
    LoginEntity findByUserId(String userId);

    @NotFound(action = NotFoundAction.IGNORE)
    LoginEntity findByLoginOid(String loginOid);

}

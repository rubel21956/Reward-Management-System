package net.spectrum.oauth2;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfileEntity, String> {

    @NotFound(action = NotFoundAction.IGNORE)
    UserProfileEntity findByLoginOid(String loginOid);

}

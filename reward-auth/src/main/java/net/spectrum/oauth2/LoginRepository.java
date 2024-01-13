package net.spectrum.oauth2;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<LoginEntity, String> {

    @NotFound(action = NotFoundAction.IGNORE)
    LoginEntity findByUserId(String userId);


}

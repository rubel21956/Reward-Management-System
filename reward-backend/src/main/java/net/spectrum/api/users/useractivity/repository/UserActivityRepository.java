package net.spectrum.api.users.useractivity.repository;

import java.util.List;
import net.spectrum.api.users.useractivity.entity.UserActivityEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserActivityRepository extends JpaRepository<UserActivityEntity, String> {
    @NotFound(action = NotFoundAction.IGNORE)
    List<UserActivityEntity> findAll();

    @NotFound(action = NotFoundAction.IGNORE)
    UserActivityEntity findByOid(String oid);
}

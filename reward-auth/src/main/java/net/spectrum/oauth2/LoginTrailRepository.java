package net.spectrum.oauth2;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginTrailRepository extends JpaRepository<LoginTrailEntity, String> {

    @NotFound(action = NotFoundAction.IGNORE)
    List<LoginTrailEntity> findByUserIdAndStatusOrderBySignInTimeDesc(String userId, String status);

    @NotFound(action = NotFoundAction.IGNORE)
    List<LoginTrailEntity> findByStatusAndUserIdContainingIgnoreCaseAndUserIdNotInOrderBySignInTimeDesc(String status, String searchText, List<String> userId , Pageable pageable);

    @NotFound(action = NotFoundAction.IGNORE)
    List<LoginTrailEntity> findByLoginTrailOidInOrderBySignInTimeDesc(List<String> userId);

    @NotFound(action = NotFoundAction.IGNORE)
    int countByStatusAndUserIdContainingIgnoreCaseAndUserIdNotInOrderBySignInTimeDesc(String status, String searchText, List<String> userId);

    @NotFound(action = NotFoundAction.IGNORE)
    List<LoginTrailEntity> findByUserId(String userId, Pageable pageable);
}

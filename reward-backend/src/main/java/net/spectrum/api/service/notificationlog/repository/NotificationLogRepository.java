package net.spectrum.api.service.notificationlog.repository;

import java.util.List;
import net.spectrum.api.service.notificationlog.entity.NotificationLogEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationLogRepository extends JpaRepository<NotificationLogEntity, String> {
    @NotFound(action = NotFoundAction.IGNORE)
    List<NotificationLogEntity> findAll();

    @NotFound(action = NotFoundAction.IGNORE)
    NotificationLogEntity findByOid(String oid);

    @NotFound(action = NotFoundAction.IGNORE)
    List<NotificationLogEntity> findByLoginOid(String userId);

    @NotFound(action = NotFoundAction.IGNORE)
    List<NotificationLogEntity> findByEmail(String email);

    @NotFound(action = NotFoundAction.IGNORE)
    List<NotificationLogEntity> findByMobileNo(String mobileNo);
}

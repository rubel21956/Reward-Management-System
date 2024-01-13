package net.spectrum.api.office.repository;

import net.spectrum.api.office.entity.OfficeEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfficeRepository extends JpaRepository<OfficeEntity, String> {
    @NotFound(action= NotFoundAction.IGNORE)
    OfficeEntity findByOid(String oid);
}

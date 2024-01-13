package net.spectrum.api.officesection.repository;


import net.spectrum.api.officesection.entity.OfficeSectionEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfficeSectionRepository extends JpaRepository<OfficeSectionEntity, String> {
    @NotFound(action = NotFoundAction.IGNORE)
    OfficeSectionEntity findByOid(String oid);
}

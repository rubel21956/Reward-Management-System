package net.spectrum.api.applicationattachment.repository;

import net.spectrum.api.application.entity.ApplicationEntity;
import net.spectrum.api.applicationattachment.entity.ApplicationAttachmentEntity;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data SQL repository for the ApplicationAttachment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicationAttachmentRepository extends JpaRepository<ApplicationAttachmentEntity, String> {
    List<ApplicationAttachmentEntity> findByOid(String oid);
    ApplicationAttachmentEntity findByfileName(String name);

}


package net.spectrum.api.application.repository;

import io.lettuce.core.dynamic.annotation.Param;
import net.spectrum.api.application.entity.ApplicationEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.lang.annotation.Native;
import java.sql.Timestamp;
import java.util.List;

/**
 * Spring Data SQL repository for the Application entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ApplicationRepository extends JpaRepository<ApplicationEntity, String> {

    @NotFound(action = NotFoundAction.IGNORE)
    ApplicationEntity findByOid(String id);

    List<ApplicationEntity> findByApplicationStatusOrderByCreatedOnDesc(String applicationStatus);

    List<ApplicationEntity> findByCreatedByOrderByCreatedOnDesc(String createdBy);

    @Query("SELECT t FROM ApplicationEntity t WHERE t.applicationStatus='Submitted' or t.applicationStatus= 'Rejected' or t.applicationStatus= 'Returned' or t.applicationStatus= 'Accepted' ORDER BY createdOn DESC")
    List<ApplicationEntity> findForNbrAdminByOrderByCreatedOnDesc();

    ApplicationEntity findByBillOfEntryNo(String billOfEntryNo);
    @Query(value = "select count(*)>0 from application where bill_of_entry_no = :bill_of_entry_no and office_oid = :office_oid and bill_of_entry_date = :bill_of_entry_date  and (application_status = 'Submitted' or application_status = 'Accepted');", nativeQuery = true)
    Boolean findBillofEntryNameDateIsExits(
            @Param("bill_of_entry_no") String bill_of_entry_no,
            @Param("office_oid") String office_oid,
            @Param("bill_of_entry_date") Timestamp bill_of_entry_date);



}

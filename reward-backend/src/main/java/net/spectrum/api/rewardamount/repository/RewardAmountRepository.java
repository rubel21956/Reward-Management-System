package net.spectrum.api.rewardamount.repository;

import net.spectrum.api.applicationattachment.entity.ApplicationAttachmentEntity;
import net.spectrum.api.rewardamount.entity.RewardAmountEntity;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data SQL repository for the RewardAmount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RewardAmountRepository extends JpaRepository<RewardAmountEntity, String> {
    List<RewardAmountEntity> findByOid(String oid);
    void deleteByOid(String oid);
}
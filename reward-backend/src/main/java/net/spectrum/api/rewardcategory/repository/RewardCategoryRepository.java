package net.spectrum.api.rewardcategory.repository;

import net.spectrum.api.rewardcategory.entity.RewardCategoryEntity;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the RewardCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RewardCategoryRepository extends JpaRepository<RewardCategoryEntity, String> {}

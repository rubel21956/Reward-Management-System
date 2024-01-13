package net.spectrum.api.users.operator.repository;

import java.util.List;

import io.lettuce.core.dynamic.annotation.Param;
import net.spectrum.api.users.operator.entity.OperatorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OperatorRepository extends JpaRepository<OperatorEntity,String> {
    OperatorEntity findByOid(String id);


    OperatorEntity findByUserId(String userId);
}

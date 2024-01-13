package net.spectrum.api.auth.permission.repository;


import net.spectrum.api.auth.permission.entity.PermissionEntity;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionEntity, String> {
    @NotFound(action= NotFoundAction.IGNORE)
    List<PermissionEntity> findAll();

    @NotFound(action= NotFoundAction.IGNORE)
    PermissionEntity findByPermissionOid(String permissionOid);

    @NotFound(action= NotFoundAction.IGNORE)
    PermissionEntity findByPermissionName(String permissionName);

    @NotFound(action= NotFoundAction.IGNORE)
    PermissionEntity findByTopMenuId(String topMenuId);

    @NotFound(action= NotFoundAction.IGNORE)
    PermissionEntity findByLeftMenuId(String leftMenuId);
}

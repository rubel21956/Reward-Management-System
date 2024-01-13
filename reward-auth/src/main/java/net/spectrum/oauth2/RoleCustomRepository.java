package net.spectrum.oauth2;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
public class RoleCustomRepository {

    @Autowired
    EntityManagerFactory entityManagerFactory;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public String getUserId(String userId) {
        String applicationStatusOid =null;
        List<RoleDTO> roleDTOList = new ArrayList<RoleDTO>();
        Query q = entityManager.createNativeQuery(
                "select uh.applicationStatusOid as applicationStatus, l.oid as loginOid, uh.userProfileOid as userProfileOid, l.userId as userId, l.roleOid as roleOid from login l, userprofile up, userhistory uh\n" +
                "where l.oid = up.loginoid\n" +
                "and up.oid = uh.userprofileoid\n" +
                "and l.userid = 'mobusshar'\n" +
                "and uh.requesttype = 'CREATION'");

        roleDTOList = q.getResultList();

        for (RoleDTO roleDto : roleDTOList) {
            applicationStatusOid = roleDto.getApplicationStatus();
        }

        return applicationStatusOid;

    }
}

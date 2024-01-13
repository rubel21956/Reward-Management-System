package net.spectrum.api.service.reports.repository;

import net.spectrum.api.application.entity.ApplicationEntity;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

@Repository
public class ApplicationReportRepository {
    @Autowired
    EntityManagerFactory entityManagerFactory;

    public List<ApplicationEntity> getApplicationByOid(String userId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        String sql = " ";
        sql += " ";

        Query query = entityManager.createQuery(sql);
        List<ApplicationEntity> list = query.unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(new AliasToBeanResultTransformer(ApplicationEntity.class)).list();
        entityManager.close();
        return list;
    }
}

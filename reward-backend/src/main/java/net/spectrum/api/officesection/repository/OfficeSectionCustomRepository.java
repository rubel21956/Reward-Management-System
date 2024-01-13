package net.spectrum.api.officesection.repository;

import com.google.common.base.Strings;
import net.spectrum.api.officesection.dto.OfficeSectionResponseDto;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.List;

@Repository
public class OfficeSectionCustomRepository {
    @Autowired
    EntityManagerFactory entityManagerFactory;

    public List<OfficeSectionResponseDto> getOfficeSections(int limit, int offset, String searchText) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        String sql = " select o.name as officeName, os.name as officeSectionName, os.oid as oid from OfficeEntity o, OfficeSectionEntity os where o.oid = os.officeOid ";

        if (!Strings.isNullOrEmpty(searchText)
                && !searchText.equalsIgnoreCase("undefined")) {
            sql += " AND (LOWER(o.name) like '%" + searchText.trim().toLowerCase()
                    + "%' OR" + " LOWER(os.name) like '%" + searchText.trim().toLowerCase()
                    + "%')";
        }

        Query query = entityManager.createQuery(sql);
        query.setFirstResult(offset * limit);
        query.setMaxResults(limit);

        List<OfficeSectionResponseDto> list = query.unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(new AliasToBeanResultTransformer(OfficeSectionResponseDto.class)).list();
        entityManager.close();
        return list;
    }
}

package com.rahul.project.gateway.dao;

import com.rahul.project.gateway.configuration.annotations.RepositoryDao;
import com.rahul.project.gateway.model.Country;
import com.rahul.project.gateway.model.Language;
import com.rahul.project.gateway.model.OtpM;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Calendar;
import java.util.List;

/**
 * @author Rahul Malhotra
 * @since 1.0
 * Date 2019-05-21
 */
@RepositoryDao
public class CommonDao {

    @Autowired
    AbstractDao abstractDao;

    public List<OtpM> getOtpEntries(String mobileNumber, Calendar generationTime) throws Exception {
        CriteriaBuilder builder = abstractDao.getSession().getCriteriaBuilder();
        CriteriaQuery<OtpM> criteria = builder.createQuery(OtpM.class);
        Root<OtpM> root = criteria.from(OtpM.class);
        criteria.select(root)
                .where(builder.and(builder.equal(root.get("mobileNumber"), mobileNumber),
                        builder.equal(root.get("status"), "OG"),
                        builder.greaterThanOrEqualTo(root.get("expirationTime"), generationTime)))
                .orderBy(builder.desc(root.get("id")));
        Query<OtpM> otpMQuery = abstractDao.getSession().createQuery(criteria);
        return otpMQuery.getResultList();
    }

    public <T> List<T> getMasterData(Class<T> type, String tableName, String language) throws Exception {
        CriteriaBuilder builder = abstractDao.getSession().getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(type);
        Root<T> root = criteria.from(type);
        Root<Country> countryRoot = criteria.from(Country.class);
        Root<Language> languageRoot = criteria.from(Language.class);
        criteria.select(root).where(builder.and(
                builder.equal(countryRoot.get("status"), true),
                builder.equal(languageRoot.get("code"), language),
                builder.equal(countryRoot.get("id"), root.get(""))
        ));
        return abstractDao.getSession().createQuery(criteria).getResultList();
    }
}

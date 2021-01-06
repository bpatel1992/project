package com.rahul.project.gateway.dao;

import com.rahul.project.gateway.configuration.annotations.RepositoryDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Rahul Malhotra
 * @since 1.0
 * Date 2019-05-21
 */
@RepositoryDao
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = {NullPointerException.class, Exception.class, Throwable.class}
        , isolation = Isolation.READ_COMMITTED
/* , noRollbackFor= {CustomException.class} */)
public class AbstractDao {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private HibernateTemplate hibernateTemplate;

    public Session getSession() {
        return entityManagerFactory.unwrap(SessionFactory.class).getCurrentSession();
    }

    public void persist(Object entity) {
        getSession().persist(entity);
    }

    public <T> void delete(T t) {
        if (t instanceof Set) {
            ((Set) t).forEach(o -> {
                getSession().delete(o);
            });
        } else {
            getSession().delete(t);
        }
    }

    public <T> T saveOrUpdateEntity(T t) {
        if (t instanceof Set) {
            ((Set) t).forEach(o -> {
                getSession().saveOrUpdate(o);
            });
        } else {
            getSession().saveOrUpdate(t);
        }
        return t;
    }

    public <T> T getEntityById(Class<T> c, Serializable serializable) {
        return getSession().get(c, serializable);
    }

    public <T> List<T> getEntityListByParamMap(Class<T> clazz, HashMap<String, Object> parameterMap) {
        TypedQuery<T> query = getTypedQuery(clazz);
        if (parameterMap != null && parameterMap.size() > 0) {
            for (Map.Entry<String, Object> entry : parameterMap.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
        return query.getResultList();
    }

    public <T> List<T> getEntityList(Class<T> clazz) {
        TypedQuery<T> query = getTypedQuery(clazz);
        return query.getResultList();
    }

    public <T> TypedQuery<T> getTypedQuery(Class<T> clazz) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(clazz);
        Root<T> myObjectRoot = criteria.from(clazz);
        criteria.select(myObjectRoot);
        return getSession().createQuery(criteria);
    }

    public <T> CriteriaQuery<T> getCriteriaQuery(Class<T> clazz) {
        CriteriaBuilder builder = getSession().getCriteriaBuilder();
        CriteriaQuery<T> criteria = builder.createQuery(clazz);
        Root<T> myObjectRoot = criteria.from(clazz);
        criteria.select(myObjectRoot);
        return criteria;
    }

    public HibernateTemplate getHibernateTemplate() {
        return hibernateTemplate;
    }

    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    public void setEntityManagerFactory(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public List getQueryForListSQL(String pQuery) {

        List returnList = null;

        Query query = getSession().createNativeQuery(pQuery);
        returnList = query.list();

        return returnList;
    }

    public int executeSQLQuery(String pQuery) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        int i = entityManager.createNativeQuery(pQuery).executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
        return i;
    }


}

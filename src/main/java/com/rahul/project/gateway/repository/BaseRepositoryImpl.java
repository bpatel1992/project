package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.configuration.BusinessException;
import com.rahul.project.gateway.crud.core.ApplicationMap;
import com.rahul.project.gateway.crud.util.ObjectUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

public class BaseRepositoryImpl<T, ID> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {

    private static final String UNDERSCORE = "_";
    private static final String FK = "FK";
    private static final String FK_FK_FK_FK = "FKFKFKFK";
    private static final String FK_FK_FK = "FKFKFK";
    private static final String EQ = "EQ";
    private static final String NOTEQ = "NOTEQ";
    private static final String LT = "LT";
    private static final String GT = "GT";
    private static final String LTEQ = "LTEQ";
    private static final String GTEQ = "GTEQ";
    private static final String LIKE = "LIKE";
    private static final String ILIKE = "ILIKE";
    private static final String IN = "IN";
    private static final String NOTIN = "NOTIN";
    private static final String BETWEEN = "BETWEEN";
    private static final String ISNULL = "ISNULL";
    private static final String ISNOTNULL = "ISNOTNULL";
    private static final String EID = "EID";
    private static final String GET_ALL_ROWS_QUERY = "SELECT e from %s e";

    protected EntityManager entityManager;
    private JpaEntityInformation<T, ?> entityInformation;
//    private ObjectUtil objectUtil;

    public BaseRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager/*,ObjectUtil objectUtil*/) {
        super(entityInformation, entityManager);
        this.entityInformation = entityInformation;
        this.entityManager = entityManager;
//        this.objectUtil=objectUtil;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public Page<?> executeQuery(String jpqlQuery, Map<String, Object> qualMap, Class<?> clz, Pageable pageable) {
        Query query = getJpqlQuery(jpqlQuery, clz);
        setQueryParams(query, qualMap);
        applyPagination(query, pageable);
        List<?> content = query.getResultList();
        return new PageImpl(content, pageable, content.size());
    }

    private Query getJpqlQuery(String jpqlQuery, Class<?> clz) {
        return clz == null ? this.entityManager.createQuery(jpqlQuery) : this.entityManager.createQuery(jpqlQuery, clz);

    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public Page<?> executeQuery(String jpqlQuery, Map<String, Object> qualMap, Pageable pageable) {
        Query query = getJpqlQuery(jpqlQuery, null);
        setQueryParams(query, qualMap);
        applyPagination(query, pageable);
        List<?> content = query.getResultList();
        return new PageImpl(content, pageable, content.size());
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public Page<?> executeNativeQuery(String nativeQuery, Map<String, Object> qualMap, Class<?> clz,
                                      Pageable pageable) {
        Query query = getNativeQuery(nativeQuery, clz);
        setQueryParams(query, qualMap);
        applyPagination(query, pageable);
        List<?> content = query.getResultList();
        return new PageImpl(content, pageable, content.size());
    }

    private Query getNativeQuery(String nativeQuery, Class<?> clz) {
        return clz == null ? this.entityManager.createNativeQuery(nativeQuery)
                : this.entityManager.createNativeQuery(nativeQuery, clz);

    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public Page<?> executeNativeQuery(String nativeQuery, Map<String, Object> qualMap, Pageable pageable) {
        Query query = getNativeQuery(nativeQuery, null);
        setQueryParams(query, qualMap);
        applyPagination(query, pageable);
        List<?> content = query.getResultList();
        return new PageImpl(content, pageable, content.size());
    }

    private void applyPagination(Query query, Pageable pageable) {
        if (pageable != null && pageable.isPaged()) {
            query.setFirstResult((int) pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());
        }

    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public Page<?> executeNamedQuery(String namedQ, Map<String, Object> qualMap, Class<?> clz, Pageable pageable) {
        Query query = getNamedQuery(namedQ, clz);
        setQueryParams(query, qualMap);
        applyPagination(query, pageable);
        List<?> content = query.getResultList();
        return new PageImpl(content, pageable, content.size());
    }

    private Query getNamedQuery(String namedQuery, Class<?> clz) {
        return clz == null ? this.entityManager.createNamedQuery(namedQuery)
                : this.entityManager.createNamedQuery(namedQuery, clz);

    }

    private void setQueryParams(Query query, Map<String, Object> qualMap) {
        if (qualMap != null && !qualMap.isEmpty()) {
            for (Entry<String, Object> entry : qualMap.entrySet()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    @SuppressWarnings({"unchecked", "rawtypes"})
    public Page<?> executeNamedQuery(String namedQ, Map<String, Object> qualMap, Pageable pageable) {
        Query query = getNamedQuery(namedQ, null);
        setQueryParams(query, qualMap);
        applyPagination(query, pageable);
        List<?> content = query.getResultList();
        return new PageImpl(content, pageable, content.size());
    }

    public Page<?> getAllRowsForEntity(Class<?> clz, Pageable pageable) {
        return this.executeQuery(getAllRowsQueryForEntity(clz.getSimpleName()), null, clz, pageable);
    }

    public List<?> getAllRowsForEntity(Class<?> clz) {
        return getAllRowsForEntity(clz, Pageable.unpaged()).getContent();
    }

    private String getAllRowsQueryForEntity(String entityName) {
        return String.format(GET_ALL_ROWS_QUERY, entityName);
    }

    @Override
    public Page<?> evaluateQual(ApplicationMap<String, Object> qualifierMap, Class<?> clz, Pageable pageable) throws BusinessException {
        return evaluateQual(qualifierMap, null, clz, pageable);
    }

    private Predicate[] calculatePredicates(ApplicationMap<String, Object> qualifierMap, CriteriaBuilder cb, Root root) throws BusinessException {
        Predicate[] predicates = new Predicate[qualifierMap.size()];
        int i = 0;
        for (Entry<String, Object> entry : qualifierMap.entrySet()) {
            String key = entry.getKey();
            predicates[i++] = predicateForKey(cb, root, key, entry.getValue());
        }
        return predicates;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public Page<?> evaluateQual(ApplicationMap<String, Object> qualifierMap, ApplicationMap<String, Object> conditionalMap
            , Class<?> clz, Pageable pageable) throws BusinessException {
        ObjectUtil objectUtil = new ObjectUtil();
        if (objectUtil.isNonEmptyMap(qualifierMap) && objectUtil.isNonEmptyMap(conditionalMap)) {
            return null;
        }
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery cQ = cb.createQuery(clz);
        Root root = cQ.distinct(true).from(clz);
        cQ = cQ.select(root);
        Predicate[] predicates = calculatePredicates(qualifierMap, cb, root);

        if (conditionalMap != null && conditionalMap.size() > 0) {
            Predicate[] predicatesConditional = calculatePredicates(conditionalMap, cb, root);
            cQ.where(cb.and(predicates), cb.or(predicatesConditional));
        } else {
            cQ.where(cb.and(predicates));
        }
        Sort sort = pageable.getSort();
        if (sort != null && sort.isSorted()) {
            Iterator<Sort.Order> itr = sort.iterator();
            List<Order> orderList = new ArrayList<>();
            while (itr.hasNext()) {
                Sort.Order order = itr.next();
                orderList.add(getOrderForDirection(order.getDirection(), cb, root, order.getProperty()));
            }
            cQ.orderBy(orderList);
        }
        TypedQuery<?> tQ = this.entityManager.createQuery(cQ);
        if (pageable.isPaged()) {
            tQ.setFirstResult((int) pageable.getOffset());
            tQ.setMaxResults(pageable.getPageSize());
        }
        List<?> content = tQ.getResultList();
        return new PageImpl(content, pageable, null == content ? 0l : content.size());

    }

    @Override
    public Page<?> evaluateQual(ApplicationMap<String, Object> qualifierMap, ApplicationMap<String, Object> conditionalMap
            , Pageable pageable) throws BusinessException {
        return evaluateQual(qualifierMap, conditionalMap, this.getDomainClass(), pageable);
    }

    @Override
    public Page<?> evaluateQual(ApplicationMap<String, Object> qualifierMap, Pageable pageable) throws BusinessException {
        return evaluateQual(qualifierMap, this.getDomainClass(), pageable);
        /**
         * if (qualifierMap == null || qualifierMap.isEmpty()) { return null; }
         * CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
         * CriteriaQuery<T> cQ = cb.createQuery(this.getDomainClass()); Root<T> root =
         * cQ.from(this.getDomainClass()); cQ = cQ.select(root); Predicate[] predicates
         * = new Predicate[qualifierMap.size()]; int i = 0; for (Entry<String, Object>
         * entry : qualifierMap.entrySet()) { String key = entry.getKey();
         * predicates[i++] = predicateForKey(cb, root, key, entry.getValue()); }
         * cQ.where(cb.and(predicates)); Sort sort = pageable.getSort(); if (sort !=
         * null && sort.isSorted()) {
         * Iterator<org.springframework.data.domain.Sort.Order> itr = sort.iterator();
         * List<Order> orderList = new ArrayList<>(); while (itr.hasNext()) { Sort.Order
         * order = itr.next(); orderList.add(getOrderForDirection(order.getDirection(),
         * cb, root, order.getProperty())); } cQ.orderBy(orderList); } TypedQuery<T> tQ
         * = this.entityManager.createQuery(cQ); if (pageable.isPaged()) {
         * tQ.setFirstResult((int) pageable.getOffset());
         * tQ.setMaxResults(pageable.getPageSize()); } List<T> content =
         * tQ.getResultList(); return new PageImpl<T>(content, pageable, null == content
         * ? 0l : content.size());
         */
    }

    public Order getOrderForDirection(Direction direction, CriteriaBuilder cb, Root<T> root, String key) {
        return direction.isAscending() ? cb.asc(root.get(key)) : cb.desc(root.get(key));
    }

    @SuppressWarnings("unchecked")
    private Predicate predicateForKey(CriteriaBuilder cb, Root<T> root, String key, Object... value) throws BusinessException {
        Predicate predicate = null;
        String hashKey = key;
        String predicateType = EQ;// default type
        if (key.contains(UNDERSCORE)) {
            StringTokenizer tokenizer = new StringTokenizer(key, UNDERSCORE);
            hashKey = tokenizer.nextToken();
            predicateType = tokenizer.nextToken();
        }
        switch (predicateType) {

            case EID:// Foreign Reference
                Map<String, Object> foreignKeyHash1 = (Map<String, Object>) value[0];
                String foreignKey1 = foreignKeyHash1.keySet().iterator().next();
                StringTokenizer tokenizer = new StringTokenizer(foreignKey1, UNDERSCORE);
                Object foreignKeyValue1 = foreignKeyHash1.get(foreignKey1);
               /* if (foreignKeyValue.getClass() == ArrayList.class) {
                    List<String> str = (List<String>) foreignKeyValue;
                    Expression<String> parentExpression = root.join(hashKey).get(foreignKey);
                    predicate = parentExpression.in(str);
                } else*/
                predicate = cb.equal(root.get(tokenizer.nextToken()).get(hashKey).get(tokenizer.nextToken()), foreignKeyValue1);
                break;
            case FK_FK_FK:// Foreign Reference inside foreign key
                Map<String, Object> map = (Map<String, Object>) value[0];
                String frKey = map.keySet().iterator().next();
                StringTokenizer frTokenizer = new StringTokenizer(frKey, UNDERSCORE);
                String key1 = frTokenizer.nextToken();
                String key2 = frTokenizer.nextToken();
                String key3 = frTokenizer.nextToken();
                Object frKeyValue = map.get(frKey);
                if (frKeyValue.getClass() == ArrayList.class) {
                    List<String> str = (List<String>) frKeyValue;
                    Expression<String> parentExpression = root.join(hashKey).join(key1).join(key2).get(key3);
                    predicate = parentExpression.in(str);
                } else
                    predicate = cb.equal(root.join(hashKey).join(key1).join(key2).get(key3), frKeyValue);
                break;

            case FK_FK_FK_FK:// Foreign Reference inside foreign key
                Map<String, Object> mapFr = (Map<String, Object>) value[0];
                String frrKey = mapFr.keySet().iterator().next();
                StringTokenizer frrTokenizer = new StringTokenizer(frrKey, UNDERSCORE);
                String key4 = frrTokenizer.nextToken();
                String key5 = frrTokenizer.nextToken();
                String key6 = frrTokenizer.nextToken();
                String key7 = frrTokenizer.nextToken();
                Object frrKeyValue = mapFr.get(frrKey);
                if (frrKeyValue.getClass() == ArrayList.class) {
                    List<String> str = (List<String>) frrKeyValue;
                    Expression<String> parentExpression = root.join(hashKey).join(key4).join(key5).join(key6).get(key7);
                    predicate = parentExpression.in(str);
                } else
                    predicate = cb.equal(root.join(hashKey).join(key4).join(key5).join(key6).get(key7), frrKeyValue);
                break;
            case FK:// Foreign Reference
                Map<String, Object> foreignKeyHash = (Map<String, Object>) value[0];
                String foreignKey = foreignKeyHash.keySet().iterator().next();
                Object foreignKeyValue = foreignKeyHash.get(foreignKey);
                if (foreignKeyValue.getClass() == ArrayList.class) {
                    List<String> str = (List<String>) foreignKeyValue;
                    Expression<String> parentExpression = root.join(hashKey).get(foreignKey);
                    predicate = parentExpression.in(str);
                } else {
                    if (foreignKey.contains("_LIKE")) {
                        StringTokenizer tokenizer1 = new StringTokenizer(foreignKey, UNDERSCORE);
                        predicate = cb.like(cb.lower(root.join(hashKey).get(tokenizer1.nextToken()).as(String.class)), "%" + foreignKeyValue.toString().toLowerCase() + "%");
                    } else
                        predicate = cb.equal(root.join(hashKey).get(foreignKey), foreignKeyValue);
                }
                break;
            case EQ:// equal
                predicate = cb.equal(root.<String>get(hashKey), value[0]);
                break;
            case NOTEQ:// equal
                predicate = cb.notEqual(root.<String>get(hashKey), value[0]);
                break;
            case LT:// Less Than <
                predicate = cb.lt(root.get(hashKey), (Number) value[0]);
                break;
            case GT:// Greater Than >
                predicate = cb.gt(root.get(hashKey), (Number) value[0]);
                break;
            case LTEQ:// Less Than Equal <=
                // predicate = cb.<Number>lessThanOrEqualTo(root.get(hashKey), (Number) value);
                break;
            case GTEQ:// Greater Than Equal >=
                predicate = cb.equal(root.<String>get(hashKey), value[0]);
                break;
            case LIKE:// Like
                predicate = cb.like(cb.lower(root.get(hashKey)), "%" + value[0].toString().toLowerCase() + "%");
                break;
            case ILIKE:// Ignore Case Like
                // predicate = cb.like(root.<String>get(hashKey), value);
                break;
            case IN:// In ()
                ArrayList<Object> listArrayList = (ArrayList<Object>) value[0];
                CriteriaBuilder.In<String> inClause = cb.in(root.get(hashKey));
                for (Object o : listArrayList) {
                    inClause.value(o.toString());
                }
                predicate = inClause;
                break;
            case NOTIN:// Not IN ()
                // predicate = cb.not(root.<String>get(hashKey), value);
                break;
            case BETWEEN:// Between
                int min = 0, max = 0;
                Date startDate = null, endDate = null;
                try {
                    ArrayList<Object> listArray = (ArrayList<Object>) value[0];
                    if (listArray.get(0) instanceof String) {
                        startDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse((String) listArray.get(0));
                        endDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse((String) listArray.get(1));
                        predicate = cb.between(root.get(hashKey), startDate, endDate);
                    } else if (listArray.get(0) instanceof Integer) {
                        min = (Integer) listArray.get(0);
                        max = (Integer) listArray.get(1);
                        predicate = cb.between(root.get(hashKey), min, max);
                    } else if (listArray.get(0) instanceof BigDecimal) {
                        BigDecimal minBigDecimal = new BigDecimal(listArray.get(0).toString());
                        BigDecimal maxBigDecimal = new BigDecimal(listArray.get(1).toString());
                        predicate = cb.between(root.get(hashKey), minBigDecimal, maxBigDecimal);
                    } else if (listArray.get(0) instanceof Double) {
                        Double minBigDecimal = new Double(listArray.get(0).toString());
                        Double maxBigDecimal = new Double(listArray.get(1).toString());
                        predicate = cb.between(root.get(hashKey), minBigDecimal, maxBigDecimal);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                break;
            case ISNULL:// Is Null
                predicate = cb.isNull(root.<String>get(hashKey));
                break;
            case ISNOTNULL:// Is Not Null
                predicate = cb.isNotNull(root.<String>get(hashKey));
                break;
            default:
                throw new BusinessException("Invalid Key for Query Evaluation : " + key);
        }
        return predicate;
    }

    protected boolean isEmpty(String val) {
        return !isNonEmpty(val);
    }

    protected boolean isNonEmpty(String val) {
        return val != null && !val.isEmpty() && !val.equalsIgnoreCase("");
    }

    @SuppressWarnings("hiding")
    protected <T> boolean isNonEmptyCollection(Collection<T> collection) {
        return collection != null && !collection.isEmpty();
    }

    protected <K, V> boolean isNonEmptyMap(Map<K, V> map) {
        return map != null && !map.isEmpty();
    }

    @Override
    public JpaEntityInformation<T, ?> getJpaEntityInformation() {
        return this.entityInformation;
    }
}

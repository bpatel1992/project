package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.configuration.BusinessException;
import com.rahul.project.gateway.crud.core.ApplicationMap;
import com.rahul.project.gateway.crud.uiBeans.BNEBase;
import com.rahul.project.gateway.crud.util.ObjectUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.*;
import java.util.Map.Entry;

public class CommonRepoOperation extends BNEBase {


    private static final String UNDERSCORE = "_";
    private static final String FK = "FK";
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
    private static final String GET_ALL_ROWS_QUERY = "SELECT e from %s e";

    @PersistenceContext
    public EntityManager entityManager;
    private ObjectUtil objectUtil;

    protected ObjectUtil getObjectUtil() {
        return objectUtil != null ? objectUtil : new ObjectUtil();
    }

    public List<?> executeNativeQuery(String nativeQ, Map<String, Object> qualMap, Class<?> clz) {
        List<?> resultList = null;
        Page<?> page = executeNativeQuery(nativeQ, qualMap, clz, Pageable.unpaged());
        if (page != null && page.getContent() != null) {
            resultList = page.getContent();
        }
        return resultList;
    }

    public <T> List<T> executeQuery(String jpqlQ, Map<String, Object> qualMap, Class<T> clz) {
        List<T> resultList = null;
        Page<T> page = executeQuery(jpqlQ, qualMap, clz, Pageable.unpaged());
        if (page != null && page.getContent() != null) {
            resultList = page.getContent();
        }
        return resultList;
    }

    public <T> List<T> executeNamedQuery(String namedQ, Map<String, Object> qualMap, Class<T> clz) {
        List<T> resultList = null;
        Page<T> page = executeQuery(namedQ, qualMap, clz, Pageable.unpaged());
        if (page != null && page.getContent() != null) {
            resultList = page.getContent();
        }
        return resultList;
    }

    @SuppressWarnings("unchecked")
    public <T> Page<T> executeQuery(String jpqlQuery, Map<String, Object> qualMap, Class<T> clz, Pageable pageable) {
        Query query = getJpqlQuery(jpqlQuery, clz);
        setQueryParams(query, qualMap);
        applyPagination(query, pageable);
        List<T> content = query.getResultList();
        return new PageImpl<T>(content, pageable, content.size());
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

    @SuppressWarnings("unchecked")
    public <T> Page<T> executeNamedQuery(String namedQ, Map<String, Object> qualMap, Class<T> clz, Pageable pageable) {
        Query query = getNamedQuery(namedQ, clz);
        setQueryParams(query, qualMap);
        applyPagination(query, pageable);
        List<T> content = query.getResultList();
        return new PageImpl<T>(content, pageable, content.size());
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

    @SuppressWarnings({"unchecked", "rawtypes"})
    public Page<?> executeNamedQuery(String namedQ, Map<String, Object> qualMap, Pageable pageable) {
        Query query = getNamedQuery(namedQ, null);
        setQueryParams(query, qualMap);
        applyPagination(query, pageable);
        List<?> content = query.getResultList();
        return new PageImpl(content, pageable, content.size());
    }

    public <T> Page<T> getAllRowsForEntity(Class<T> clz, Pageable pageable) {
        return this.executeQuery(getAllRowsQueryForEntity(clz.getSimpleName()), null, clz, pageable);
    }

    public <T> List<T> getAllRowsForEntity(Class<T> clz) {
        return getAllRowsForEntity(clz, Pageable.unpaged()).getContent();
    }

    private String getAllRowsQueryForEntity(String entityName) {
        return String.format(GET_ALL_ROWS_QUERY, entityName);
    }

    @SuppressWarnings("unchecked")
    protected <T> List<T> evaluateQual(Class<T> clz, String keys, Object... values) throws BusinessException {
        Page<T> page = evaluateQual(clz, Pageable.unpaged(), keys, values);
        return page != null && page.getContent() != null ? page.getContent() : Collections.EMPTY_LIST;
    }


    protected <T> Page<T> evaluateQual(Class<T> clz, Pageable pageable, String keys, Object... values) throws BusinessException {
        ApplicationMap<String, Object> map = new ApplicationMap<>();
        String[] keysList = keys.split("~");
        for (int i = 0; i < keysList.length; i++) {
            map.put(keysList[i], values[i]);
        }
        return this.evaluateQual(map, clz, pageable);
    }

    protected <T> T evaluateQualToObject(Class<T> clz, String keys, Object... values) throws BusinessException {
        Page<T> page = evaluateQual(clz, Pageable.unpaged(), keys, values);
        if (page == null || page.getContent() == null || page.getContent().isEmpty()) {
            throw new BusinessException("No Object Found for " + keys + " with values " + valueString(values)
                    + " in class " + clz.getName());
        } else if (page.getContent().size() > 1) {
            throw new BusinessException("Non Unique result found for keys " + keys + " with values "
                    + valueString(values) + " in class " + clz.getName());
        }
        return page.getContent().get(0);
    }

    private String valueString(Object... values) {
        return Arrays.asList(values).toString();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public <T> Page<T> evaluateQual(ApplicationMap<String, Object> qualifierMap, Class<T> clz, Pageable pageable) throws BusinessException {
        if (qualifierMap == null || qualifierMap.isEmpty()) {
            return null;
        }
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery cQ = cb.createQuery(clz);
        Root root = cQ.from(clz);
        cQ = cQ.select(root);
        Predicate[] predicates = new Predicate[qualifierMap.size()];
        int i = 0;
        for (Entry<String, Object> entry : qualifierMap.entrySet()) {
            String key = entry.getKey();
            predicates[i++] = predicateForKey(cb, root, key, entry.getValue());
        }
        cQ.where(cb.and(predicates));
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

    public Order getOrderForDirection(Direction direction, CriteriaBuilder cb, Root<?> root, String key) {
        return direction.isAscending() ? cb.asc(root.get(key)) : cb.desc(root.get(key));
    }

    @SuppressWarnings("unchecked")
    private Predicate predicateForKey(CriteriaBuilder cb, Root<?> root, String key, Object... value) throws BusinessException {
        Predicate predicate = null;
        String hashKey = key;
        String predicateType = EQ;// default type
        if (key.contains(UNDERSCORE)) {
            StringTokenizer tokenizer = new StringTokenizer(key, UNDERSCORE);
            hashKey = tokenizer.nextToken();
            predicateType = tokenizer.nextToken();
        }
        switch (predicateType) {
            case FK:// Foreign Reference
                Map<String, Object> foreignKeyHash = (Map<String, Object>) value[0];
                String foreignKey = foreignKeyHash.keySet().iterator().next();
                Object foreignKeyValue = foreignKeyHash.get(foreignKey);
                predicate = cb.equal(root.join(hashKey).get(foreignKey), foreignKeyValue);
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
                predicate = cb.like(root.get(hashKey), (String) value[0]);
                break;
            case ILIKE:// Ignore Case Like
                // predicate = cb.like(root.<String>get(hashKey), value);
                break;
            case IN:// In ()
                // predicate = cb.in(root.get(hashKey), (Object[]) value);
                break;
            case NOTIN:// Not IN ()
                // predicate = cb.not(root.<String>get(hashKey), value);
                break;
            case BETWEEN:// Between
                predicate = cb.between(root.get(hashKey), 100, 200);
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

    protected <T> boolean isNonEmptyCollection(Collection<T> collection) {
        return collection != null && !collection.isEmpty();
    }

    protected <K, V> boolean isNonEmptyMap(Map<K, V> map) {
        return map != null && !map.isEmpty();
    }

    protected Map<String, Object> qualHashForKeyValues(String keys, Object... values) {
        Map<String, Object> qualMap = new HashMap<>();
        if (keys != null) {
            StringTokenizer tokenizer = new StringTokenizer(keys, "~");
            int i = 0;
            while (tokenizer.hasMoreElements()) {
                String keyToken = (String) tokenizer.nextElement();
                qualMap.put(keyToken, values[i++]);
            }
        }
        return qualMap;
    }
}

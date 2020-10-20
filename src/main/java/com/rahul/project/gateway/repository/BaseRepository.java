package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.configuration.BusinessException;
import com.rahul.project.gateway.crud.core.ApplicationMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {

    JpaEntityInformation<T, ?> getJpaEntityInformation();

    default List<?> executeNativeQuery(String nativeQ, Map<String, Object> qualMap) {
        List<?> resultList = null;
        Page<?> page = executeNativeQuery(nativeQ, qualMap, Pageable.unpaged());
        if (page != null && page.getContent() != null) {
            resultList = page.getContent();
        }
        return resultList;
    }

    Page<?> executeNativeQuery(String nativeQ, Map<String, Object> qualMap, Pageable pageable);

    default List<?> executeQuery(String jpqlQ, Map<String, Object> qualMap) {
        List<?> resultList = null;
        Page<?> page = executeQuery(jpqlQ, qualMap, Pageable.unpaged());
        if (page != null && page.getContent() != null) {
            resultList = page.getContent();
        }
        return resultList;
    }

    Page<?> executeQuery(String jpqlQ, Map<String, Object> qualMap, Pageable pageable);

    default List<?> executeNamedQuery(String namedQ, Map<String, Object> qualMap) {
        List<?> resultList = null;
        Page<?> page = executeQuery(namedQ, qualMap, Pageable.unpaged());
        if (page != null && page.getContent() != null) {
            resultList = page.getContent();
        }
        return resultList;
    }

    Page<?> executeNamedQuery(String namedQ, Map<String, Object> qualMap, Pageable pageable);

    default Page<?> evaluateQual(String keys, Object... values) throws BusinessException {
        return this.evaluateQual(mapFromQualKeys(keys, values));
    }

    default Page<?> evaluateQual(ApplicationMap<String, Object> map) throws BusinessException {
        return evaluateQual(map, Pageable.unpaged());
    }

    Page<?> evaluateQual(ApplicationMap<String, Object> qualifierMap, Pageable pageable) throws BusinessException;

    Page<?> evaluateQual(ApplicationMap<String, Object> qualifierMap,
                         ApplicationMap<String, Object> conditionalMap, Pageable pageable) throws BusinessException;

    Page<?> evaluateQual(ApplicationMap<String, Object> qualifierMap,
                         ApplicationMap<String, Object> conditionalMap, Class<?> clz, Pageable pageable) throws BusinessException;

    Page<?> evaluateQual(ApplicationMap<String, Object> qualifierMap, Class<?> clz, Pageable pageable) throws BusinessException;

    default ApplicationMap<String, Object> mapFromQualKeys(String keys, Object... values) {
        ApplicationMap<String, Object> map = new ApplicationMap<>();
        StringTokenizer tokenizer = new StringTokenizer(keys, "~");
        int i = 0;
        while (tokenizer.hasMoreElements()) {
            String key = (String) tokenizer.nextElement();
            map.put(key, values[i++]);
        }
        return map;
    }

    default Page<?> evaluateQual(Class<?> clz, Pageable pageable, String keys, Object... values) throws BusinessException {
        ApplicationMap<String, Object> map = new ApplicationMap<>();
        String[] keysList = keys.split("~");
        for (int i = 0; i < keysList.length; i++) {
            map.put(keysList[i], values[i]);
        }
        return this.evaluateQual(mapFromQualKeys(keys, values), clz, pageable);
    }

    default Object evaluateQualToObject(Class<?> clz, String keys, Object... values) throws BusinessException {
        Page<?> page = evaluateQual(clz, Pageable.unpaged(), keys, values);
        if (page == null || page.getContent() == null || page.getContent().isEmpty()) {
            throw new BusinessException(
                    "No Object Found for " + keys + " with values " + values + " in class " + clz.getName());
        } else if (page.getContent().size() > 1) {
            throw new BusinessException("Non Unique result found for keys " + keys + " with values "
                    + values + " in class " + clz.getName());
        }
        return page.getContent().get(0);
    }
}

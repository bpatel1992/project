package com.rahul.project.gateway.crud.core;

import com.rahul.project.gateway.configuration.annotations.SystemComponent;
import com.rahul.project.gateway.crud.uiBeans.BNEBase;
import com.rahul.project.gateway.repository.BaseRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;

/**
 * This is Generic Request Object :
 * <p>
 * request : { commonParamHash : { //generic parame }, objectHash : { //having
 * crud resource object graph } } Example : { "commonParamHash": { "entityName":
 * "ProductUser", "operation": "CREATE"//UPDATE/DELETE/READ }, "objectHash": {
 * "firstName": "Pranshu", "lastName": "Gupta", "isActive": true, "isLocked":
 * false, "emailAddress": "pranshu.gupta@mann-india.com", "password":
 * "Pranshu@1234", "privilege": { "primaryKey": 5 }, "role": { "primaryKey": 21
 * } } }
 *
 * @author Rahul Malhotra
 */
@SystemComponent
public class CRUDRequest extends BNEBase implements CastValue {

    //public final String modelPackage =
    // "com.rahul.project.gateway.model";// Need this to configure dynamically
    public static final String modelPackage = "com.rahul.project.gateway.model";// Need this to configure dynamically

    public static final String DOT = ".";
    public static final String ENTITY_NAME = "entityName";
    public static final String HEADER_ID = "headerId";
    public static final String HEADER_ID_ARRAY = "headerIdArray";
    public static final String NAMED_QUERY = "namedQ";
    private static final String PAGINATION = "pagination";
    private static final String SORT = "sort";
    private static final String PAGE_Number = "pageNumber";
    private static final String PAGE_SIZE = "pageSize";
    private static String uiBeanId = "uiBeanId";
    private static String uiBean = "uiBean";
    private ApplicationMap<String, Object> commonParamHash;
    private ApplicationMap<String, Object> objectHash;
    private ApplicationMap<String, Object> conditionalHash;

    public ApplicationMap<String, Object> getConditionalHash() {
        return conditionalHash == null ? new ApplicationMap<>() : conditionalHash;
    }

    public void setConditionalHash(ApplicationMap<String, Object> conditionalHash) {
        this.conditionalHash = new ApplicationMap<>(conditionalHash);
    }

    public ApplicationMap<String, Object> getCommonParamHash() {
        return commonParamHash == null ? new ApplicationMap<>() : commonParamHash;
    }

    public void setCommonParamHash(Map<String, Object> commonParamHash) {
        this.commonParamHash = new ApplicationMap<>(commonParamHash);
    }

    public ApplicationMap<String, Object> getObjectHash() {
        return objectHash == null ? new ApplicationMap<>() : objectHash;
    }

    public void setObjectHash(Map<String, Object> objectHash) {
        this.objectHash = new ApplicationMap<>(objectHash);
    }


    @SuppressWarnings("unchecked")
    public Serializable entityObject() {
        Serializable entityObject = null;
        String entityName = modelPackage + DOT + this.entityName();
        try {
            Class<? extends Serializable> clz = (Class<? extends Serializable>) Class.forName(entityName);
            entityObject = this.objectHash.objectForClass(clz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return entityObject;
    }

    public String entityName() {
        return this.commonParamHash.stringValue(ENTITY_NAME);
    }

    /**
     * key : the key whose string value will be returned key can be as follows
     * <p>
     * if key = "C_name" that means the related map from where key is to be hashed
     * is CommonParamHash
     * <p>
     * else if key = "name" that means the related map from where the key is to be
     * hashed is ObjectHash
     */
    @Override
    public <T> T castObject(String key, Class<T> clz) {
        ApplicationMap<String, Object> hashMap;
        String valueKey = key;
        StringTokenizer tokenizer = new StringTokenizer(key, "_");
        if (tokenizer.countTokens() > 1) {
            String hashKey = tokenizer.nextToken();
            hashMap = hashKey.equals("C") ? this.commonParamHash : this.objectHash;
            valueKey = tokenizer.nextToken();
        } else {
            hashMap = this.objectHash;
        }
        return hashMap.castObject(valueKey, clz);
    }

    public String uiBeanId() {
        return this.getCommonParamHash().stringValue(uiBeanId);
    }

    public String uiBean() {
        return this.getCommonParamHash().stringValue(uiBean);
    }

    public Object getHeaderId(BaseRepository<?, ?> repository) {
        return this.commonParamHash.castObject(HEADER_ID, this.getEntityPkTypeClass(repository));
    }

    private Class<?> getEntityPkTypeClass(BaseRepository<?, ?> repository) {
        return repository.getJpaEntityInformation().getIdType();
    }

    public List<?> getHeaderIdArray() {
        return this.getCommonParamHash().listValue(HEADER_ID_ARRAY);
    }

    public String getNamedQuery() {
        return this.getCommonParamHash().stringValue(NAMED_QUERY);
    }

    public Pageable pageRequest() {
        Pageable pageRequest;
        ApplicationMap<String, Object> paginationHash = this.getCommonParamHash().hashForKey(PAGINATION);
        if (paginationHash != null) {
            pageRequest = PageRequest.of(paginationHash.intValue(PAGE_Number), paginationHash.intValue(PAGE_SIZE),
                    this.sortRequest());
        } else {
            pageRequest = PageRequest.of(0, Integer.MAX_VALUE, this.sortRequest());
        }
        return pageRequest;
    }

    @SuppressWarnings("unchecked")
    public Sort sortRequest() {
        Sort sort = Sort.unsorted();
        ApplicationMap<String, Object> sortHash = this.getCommonParamHash().hashForKey(SORT);
        if (sortHash != null) {
            for (Entry<String, Object> entry : sortHash.entrySet()) {
                Direction direction = Direction.fromString(entry.getKey());
                List<String> sortingkeysList = (List<String>) entry.getValue();
                if (isNonEmptyCollection(sortingkeysList)) {
                    String[] strKeysArray = new String[sortingkeysList.size()];
                    sortingkeysList.toArray(strKeysArray);
                    sort = sort.isUnsorted() ? Sort.by(direction, strKeysArray)
                            : sort.and(Sort.by(direction, strKeysArray));
                }
            }
        }
        return sort;
    }

    public boolean isNonEmptyCollection(Collection<?> collection) {
        return collection != null && !collection.isEmpty();
    }
}

package com.rahul.project.gateway.crud.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rahul.project.gateway.configuration.BusinessException;
import com.rahul.project.gateway.configuration.annotations.SystemComponent;
import com.rahul.project.gateway.crud.annotation.loader.UIBeanMapper;
import com.rahul.project.gateway.crud.core.ApplicationMap;
import com.rahul.project.gateway.crud.core.CRUDRequest;
import com.rahul.project.gateway.crud.core.CRUDResponse;
import com.rahul.project.gateway.crud.core.EntityServiceManager;
import com.rahul.project.gateway.crud.uiBeans.BNEBase;
import com.rahul.project.gateway.crud.util.ObjectUtil;
import com.rahul.project.gateway.model.ProductUser;
import com.rahul.project.gateway.repository.BaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.lang.invoke.MethodHandles;
import java.util.*;

/**
 * This controller holds the basic Crud (Create, Read, update, delete)
 * operations which are generic. That is a required repository and request data
 * are the only requirement to operate on methods of this class.
 *
 * @author Rahul Malhotra
 */
@SystemComponent
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CrudCtrlBase extends BNEBase {

    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final String CREATE = "CREATE";
    private final String READ = "READ";
    private final String UPDATE = "UPDATE";
    private final String DELETE = "DELETE";
    private final String SAVE_ALL = "SAVE_ALL";
    private final String FIND_BY_ID = "FIND_BY_ID";
    private final String FIND_ALL_BY_ID = "FIND_ALL_BY_ID";
    private final String SEARCH = "SEARCH";
    private final String SEARCH_MERGED = "SEARCH_MERGED";
    private final String NAMED_Q = "NAMED_Q";
    private final String DELETE_BY_ID = "DELETE_BY_ID";
    private final String DELETE_ALL_BY_ID = "DELETE_ALL_BY_ID";
    private final String OPERATION_UNDEFINED = "OPERATION_UNDEFINED";
    @Autowired
    EntityServiceManager entityServiceManager;
    @Autowired
    ObjectUtil objectUtil;
    private CRUDRequest CRUDRequest;
    @Autowired
    private UIBeanMapper uiBeanMapper;

//    private boolean checkPrivilege = false;

    //    private Map<String, Privilege> privilegeHash = new HashMap<>();
    private BaseRepository repository = null;

    public static void copyNonNullProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    private static String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    public BaseRepository getRepository() {
        return repository;
    }

    public void setRepository(BaseRepository repository) {
        this.repository = repository;
    }

    private boolean canTakeAction(String privilege) {
//        if (!checkPrivilege) {
        return true;
//        }
        /*Privilege otherPrivilege = this.privilegeHash.get(privilege);
        if (otherPrivilege == null) {
            PrivilegeRepository privilegeRepository = entityServiceManager.getBean(PrivilegeRepository.class);
            otherPrivilege = privilegeRepository.findByPrivilege(privilege);
            privilegeHash.put(privilege, otherPrivilege);
        }
        return sessionUser().canTakeAction(otherPrivilege);*/
    }

    private ProductUser sessionUser() {
        return null;
    }

    public <T extends Serializable, ID> T save(T entity, JpaRepository<T, ID> repo) {
        T obj = null;
        if (canTakeAction(CREATE)) {
            obj = repo.save(entity);
        }
        return obj;
    }

    public <T, ID> T update(T entity, JpaRepository<T, ID> repo, ID id) {
        T obj = null;
        if (canTakeAction(UPDATE)) {
            if (id != null) {
                T existing = repo.getOne(id);
                copyNonNullProperties(entity, existing);
                obj = repo.save(existing);
            } else {
                obj = repo.save(entity);
            }
        }
        return obj;
    }

    public <T, ID> Iterable<T> saveAll(Iterable<T> entities, JpaRepository<T, ID> repo) {
        Iterable<T> list = null;
        if (canTakeAction(UPDATE)) {
            list = repo.saveAll(entities);
        }
        return list;
    }

    public <T, ID> T findById(ID oid, JpaRepository<T, ID> repo) {
        Optional<T> obj = null;
        T result = null;
        if (canTakeAction(READ)) {
            obj = repo.findById(oid);
            if (obj != null && obj.isPresent()) {
                result = obj.get();
            }
        }
        return result;
    }

    public <T, ID> boolean existsById(ID oid, JpaRepository<T, ID> repo) {
        boolean isExist = false;
        if (canTakeAction(READ)) {
            isExist = repo.existsById(oid);
        }
        return isExist;
    }

    public <T, ID> Page<T> findAll(BaseRepository<T, ID> repo) {
        Page<T> page = null;
        if (canTakeAction(READ)) {
            page = repo.findAll(this.request().pageRequest());
            //Sort sort= Sort.by(Sort.Order.asc(""));
//			PageRequest pageRequest=PageRequest.of(0,10,Sort.unsorted());
        }
        return page;
    }

    public <T, ID> Iterable<T> findAllById(Iterable<ID> oids, BaseRepository<T, ID> repo) {
        Iterable<T> list = null;
        if (canTakeAction(READ)) {
            list = repo.findAllById(oids);
        }
        return list;
    }

    public <T, ID> Page<?> search(BaseRepository<T, ID> repo, String namedQ) {
        Page<?> result = null;
        if (canTakeAction(READ)) {
            result = repo.executeNamedQuery(namedQ, this.request().getObjectHash(), this.request().pageRequest());
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public <T, ID> Page<T> search(BaseRepository<T, ID> repo, ApplicationMap<String, Object> objHash) throws BusinessException {
        Page<T> list = null;
        if (canTakeAction(READ)) {
            list = (Page<T>) repo.evaluateQual(objHash, this.request().pageRequest());
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    public <T, ID> Page<T> searchMerged(BaseRepository<T, ID> repo, ApplicationMap<String, Object> objHash,
                                        ApplicationMap<String, Object> conditionalHash) throws BusinessException {
        Page<T> list = null;
        if (canTakeAction(READ)) {
            list = (Page<T>) repo.evaluateQual(objHash, conditionalHash, this.request().pageRequest());
        }
        return list;
    }

    public <T, ID> void deleteById(ID id, JpaRepository<T, ID> repo) {
        if (canTakeAction(DELETE)) {
            repo.deleteById(id);
        }
    }

    public <T, ID> void delete(T entity, JpaRepository<T, ID> repo) {
        if (canTakeAction(DELETE)) {
            repo.delete(entity);
        }
    }

    public <T, ID> void deleteAll(Iterable<? extends T> entities, JpaRepository<T, ID> repo) {
        if (canTakeAction(DELETE)) {
            repo.deleteAll(entities);
        }
    }

    public <T, ID> void deleteAll(JpaRepository<T, ID> repo) {
        if (canTakeAction(DELETE)) {
            repo.deleteAll();
        }
    }

    @SuppressWarnings("unchecked")
    public Map<String, Object> executeOperation() {
        Map<String, Object> response = null;
        CRUDResponse CRUDResponse = null;
        String crudOperation = this.request().stringValue("C_operation");
        try {
            CRUDResponse = invokeCrudOperation(crudOperation);
            response = new ObjectMapper().convertValue(CRUDResponse, Map.class);
        } catch (Exception e) {
            logger.error("Exception in CRUD operation ", e);
            response = new ObjectMapper().convertValue(new CRUDResponse(true, e.getMessage()), Map.class);
        }
        return response;
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private CRUDResponse invokeCrudOperation(String operation) throws BusinessException {
        Object result = null;
        switch (operation) {
            case CREATE:
                result = this.save(this.request().entityObject(), repository);
                break;
            case SAVE_ALL:
                result = this.save(this.request().entityObject(), repository);
                break;
            case READ:
                result = this.findAll(repository);
                break;
            case FIND_BY_ID:
                result = this.findById(this.request().getHeaderId(repository), repository);
                break;
            case FIND_ALL_BY_ID:
                result = this.findAllById(this.request().getHeaderIdArray(), repository);
                break;
            case SEARCH:
                result = searchResult(this.request().getObjectHash(), repository);
                break;
            case SEARCH_MERGED:
                result = searchResult(this.request().getObjectHash(), this.request().getConditionalHash(), repository);
                break;
            case NAMED_Q:
                result = repository.executeNamedQuery(this.request().getNamedQuery(), this.request().getObjectHash(),
                        this.request().pageRequest());
                break;
            case UPDATE:
                result = this.update(this.request().entityObject(), repository, this.request().getHeaderId(repository));
                break;
            case DELETE:
                this.delete(this.request().entityObject(), repository);
                break;
            case DELETE_BY_ID:
                this.deleteById(this.request().getHeaderId(repository), repository);
                break;
            case DELETE_ALL_BY_ID:
                this.delete(this.request().getHeaderIdArray(), repository);
                break;
            default:
                return new CRUDResponse(true, OPERATION_UNDEFINED);
        }
        return apiResult(result);
    }

    private Object searchResult(ApplicationMap<String, Object> objHash, BaseRepository<?, ?> repository) throws BusinessException {
        return objectUtil.isNonEmptyMap(objHash) ? this.search(repository, objHash) : this.findAll(repository);
    }

    private Object searchResult(ApplicationMap<String, Object> objHash, ApplicationMap<String, Object> conditionalHash, BaseRepository<?, ?> repository) throws BusinessException {
        return objectUtil.isNonEmptyMap(objHash) ? this.searchMerged(repository, objHash, conditionalHash) : this.findAll(repository);
    }

    private CRUDResponse apiResult(Object result) throws BusinessException {
        String uiBeanId = this.request().uiBeanId();
        if (uiBeanId != null) {
//             list= ((PageImpl) result).getContent();
            List<?> list = (List) uiBeanMapper.beanForId(uiBeanId, result);
            return new CRUDResponse(new PageImpl<>(list, ((PageImpl) result).getPageable(), ((PageImpl) result).getTotalElements()));
        }
        String uiBean = this.request().uiBean();
        if (uiBean != null) {
            String crudOperation = this.request().stringValue("C_operation");
            if (UPDATE.equalsIgnoreCase(crudOperation)) {
                result = uiBeanMapper.updateObj(uiBean, result);
            } else if (CREATE.equalsIgnoreCase(crudOperation)) {
                result = uiBeanMapper.beanForObj(uiBean, result);
            } else {
                List<?> list = (List) uiBeanMapper.bean(uiBean, result);
                return new CRUDResponse(new PageImpl<>(list, ((PageImpl) result).getPageable(), ((PageImpl) result).getTotalElements()));
            }
        }
        return new CRUDResponse(result);
    }


    /**
     * This must be implemented by the respective controller that extends this
     * BaseCrudCtrl class
     *
     * @return the generalized User Request
     */
    public CRUDRequest request() {
        return this.CRUDRequest;
    }

    public void setCRUDRequest(CRUDRequest CRUDRequest) {
        this.CRUDRequest = CRUDRequest;
    }
}

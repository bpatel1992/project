package com.rahul.project.gateway.crud.controller;

import com.rahul.project.gateway.configuration.annotations.RESTController;
import com.rahul.project.gateway.crud.core.CRUDRequest;
import com.rahul.project.gateway.crud.core.EntityServiceManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.invoke.MethodHandles;
import java.util.Map;

/**
 * This is standard and Stand alone Controller to perform CRUD i.e Create, Read,
 * Update and Delete operations on any of the Entity in the application just
 * needs to follow the Request Pattern i.e CRUDRequest instance.
 * <p>
 * So all the CRUD operations in the application can be handled by this
 * Controller only and we don't have to define Entity and CRUD operation
 * specific rest end Points in the application.
 * <p>
 * A Request can be of the form :
 * <p>
 * { "commonParamHash": { "entityName": "ProductUser", "operation": "CREATE"//It
 * can be READ/UPDATE/DELETE/FIND_BY_ID as well }, "objectHash": { // This must
 * holds the object graph of the entity corresponding to the operation
 * "firstName": "Arun", "lastName": "Gupta", "isActive": true, "isLocked":
 * false, "emailAddress": "arun.gupta@mann-india.com", "password": "Arun@1234",
 * "privilege": { "primaryKey": 5 }, "role": { "primaryKey": 21 } } }
 *
 * @author Rahul Malhotra
 */
@RESTController
public class CrudCtrl {

    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private CrudCtrlBase crudCtrlBase;

    @Autowired
    private EntityServiceManager entityServiceManager;

    /**
     * This request handles all the CRUD Operations URI Must be : admin/crud
     *
     * @param request .
     * @return CRUDResponse
     */
    @RequestMapping(method = RequestMethod.POST, value = {"/api/crud", "/oauth2/api/crud"})
    public Map<String, Object> genericOperation(@RequestBody CRUDRequest request) {
//        logger.info("request is {}.", request);
        crudCtrlBase.setCRUDRequest(request);
        crudCtrlBase.setRepository(entityServiceManager.serviceForEntity(request.entityName()));
        return crudCtrlBase.executeOperation();
    }

}

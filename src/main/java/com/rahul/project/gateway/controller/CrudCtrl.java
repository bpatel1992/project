package com.rahul.project.gateway.controller;

import com.rahul.project.gateway.configuration.BusinessException;
import com.rahul.project.gateway.configuration.annotations.RESTController;
import com.rahul.project.gateway.crud.controller.CrudCtrlBase;
import com.rahul.project.gateway.crud.core.CRUDRequest;
import com.rahul.project.gateway.crud.core.EntityServiceManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
@Api(value = "API provide product basic functionalities",
        description = "This API provides all the functionalities like search, update, etc for the given criteria",tags = { "Crud services" })
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
    @ApiOperation(value = "This API provides all the functionalities like search, update, etc for the given criteria. " +
             "Sample request payload : " +
            "{\n" +
            "    \"commonParamHash\": {\n" +
            "        \"entityName\": \"User\" - Entity name for which the criteria is to be applied,\n" +
            "        \"uiBean\": \"BNECustomer\" - Response DTO bean name which will be sen in response,\n" +
            "        \"operation\": \"SEARCH\" - operation to be performed ,\n" +
            "        \"pagination\": {\n" +
            "            \"pageNumber\": 0,\n" +
            "            \"pageSize\": 10\n" +
            "        }  - Pagination details to apply pagination in request ,\n" +
            "        \"sort\": {\n" +
            "            \"DESC\": [\n" +
            "                \"lastVisit\" - column name on which sorting to be applied \n" +
            "            ]\n" +
            "        } - to apply sorting order of data \n" +
            "    },\n" +
            "    \"objectHash\": {\n" +
            "        \"fullName_LIKE\": \"shri\", - column name and value applied in criteria appended with Like operation in criteria \n" +
            "        \"country_FK\": {\n" +
            "            \"id\": 1\n" +
            "        }  - foreign key mapping name and value applied in criteria,\n" +
            "        \"mobile_LIKE\": \"7867654567\",\n" +
            "        \"email_LIKE\": \"ram\",\n" +
            "        \"authorities_FK\": {\n" +
            "            \"name\": \"ROLE_CUSTOMER\"\n" +
            "        }\n" +
            "    }\n" +
            "} ", produces = MediaType.APPLICATION_JSON_VALUE)
    /*@ApiImplicitParams({
            @ApiImplicitParam(name = "bearer token", value = "Bearer token required to access this service"
                    , required = true, dataType = "String", paramType = "header")})*/
    @RequestMapping(method = RequestMethod.POST, value = {"/api/crud", "/oauth2/api/crud"})
    public Map<String, Object> genericOperation(@RequestBody CRUDRequest request) throws BusinessException {
//        logger.info("request is {}.", request);
        crudCtrlBase.setCRUDRequest(request);
        crudCtrlBase.setRepository(entityServiceManager.serviceForEntity(request.entityName()));
        return crudCtrlBase.executeOperation();
    }

}

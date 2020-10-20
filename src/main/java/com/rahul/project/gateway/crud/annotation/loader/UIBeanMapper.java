package com.rahul.project.gateway.crud.annotation.loader;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rahul.project.gateway.configuration.annotations.SystemComponent;
import com.rahul.project.gateway.crud.uiBeans.BNE;
import com.rahul.project.gateway.crud.uiBeans.BNEBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.PageImpl;

import java.io.Serializable;
import java.util.List;

@SystemComponent
public class UIBeanMapper extends BNEBase {

    @Autowired
    ApplicationContext applicationContext;

    @SuppressWarnings("unchecked")
    private Serializable entityObject(String uiBeanId) {
        Serializable entityObject = null;
        String entityName = "com.rahul.project.gateway.crud.uiBeans." + uiBeanId;
        try {
            Class<? extends Serializable> clz = (Class<? extends Serializable>) Class.forName(entityName);
            entityObject = new ObjectMapper().convertValue(this, clz);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return entityObject;
    }

    public Object beanForId(String uiBeanId, Object result) {
        return this.objectForClassFromList(entityObject(uiBeanId).getClass(), ((PageImpl) result).getContent());
    }


    public List bean(String uiBean, Object result) {
        BNE bneBase = (BNE) getBean(uiBean);
        return bneBase.process(((PageImpl) result).getContent());
    }

    public Object beanForObj(String uiBean, Object result) {
        BNE bneBase = (BNE) getBean(uiBean);
        return bneBase.process(result);
    }

    public Object updateObj(String uiBean, Object result) {
        BNE bneBase = (BNE) getBean(uiBean);
        return bneBase.updateProcess(result);
    }

    private Object getBean(String bean) {
        return applicationContext.getBean(bean);
    }

}

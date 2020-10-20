package com.rahul.project.gateway.dao;

import com.rahul.project.gateway.configuration.annotations.RepositoryDao;
import com.rahul.project.gateway.model.PartnerServices;
import com.rahul.project.gateway.model.Services;
import com.rahul.project.gateway.utility.CommonUtility;
import com.rahul.project.gateway.utility.Translator;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RepositoryDao
public class ServiceDao {


    @Autowired
    AbstractDao abstractDao;

    @Autowired
    CommonUtility commonUtility;

    @Autowired
    Translator translator;


    public List<PartnerServices> getPartnerServiceListByPartnerId(Long partnerId) throws Exception {
        Session session = abstractDao.getSession();
        String query = "from PartnerServices where partnerId.id =" + partnerId + " AND status =" + true;
        List<PartnerServices> partnerServicesList = session.createQuery(query).getResultList();
        return partnerServicesList;
    }

    public List<Services> getServiceListByServiceType(Long serviceTypeId) throws Exception {
        Session session = abstractDao.getSession();
        String query = "from ServicesM where serviceTypeId.id =" + serviceTypeId;
        List<Services> servicesList = session.createQuery(query).getResultList();
        return servicesList;
    }
}

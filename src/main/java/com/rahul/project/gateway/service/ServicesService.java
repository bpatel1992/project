package com.rahul.project.gateway.service;

import com.rahul.project.gateway.configuration.BusinessException;
import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.dao.AbstractDao;
import com.rahul.project.gateway.dao.ServiceDao;
import com.rahul.project.gateway.dto.PartnerServiceResponseDTO;
import com.rahul.project.gateway.dto.ServiceDTO;
import com.rahul.project.gateway.dto.services.PartnerServiceListDTO;
import com.rahul.project.gateway.model.Partner;
import com.rahul.project.gateway.model.PartnerServices;
import com.rahul.project.gateway.model.Services;
import com.rahul.project.gateway.utility.Translator;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

@TransactionalService
public class ServicesService {
    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    AbstractDao abstractDao;

    @Autowired
    ServiceDao serviceDao;

    @Autowired
    Translator translator;

    @Autowired
    ModelMapper modelMapper;


    public void addUpdateService(Services services) throws Exception {
        abstractDao.saveOrUpdateEntity(services);
    }

    public void addUpdatePartnerService(PartnerServiceListDTO partnerServiceListDTO) throws Exception {

        if (!partnerServiceListDTO.getPartnerServiceDTOList().isEmpty()) {
            partnerServiceListDTO.getPartnerServiceDTOList().stream().forEach(partnerServiceDTO -> {
                PartnerServices partnerServices = new PartnerServices();
                partnerServices.setDiscount(partnerServiceDTO.getDiscount());
                partnerServices.setFinalPrice(partnerServiceDTO.getFinalPrice());
                partnerServices.setOfferedPrice(partnerServiceDTO.getOfferedPrice());
                Partner partner = new Partner();
                partner.setId(partnerServiceDTO.getPartnerId());
                partnerServices.setPartnerId(partner);
                Services services = new Services();
                services.setId(partnerServiceDTO.getServiceId());
                partnerServices.setServiceId(services);
                abstractDao.saveOrUpdateEntity(partnerServices);
            });
        } else throw new BusinessException(translator.toLocale("partner.service.list.empty"));
    }

    public List<PartnerServiceResponseDTO> getPartnerServices(Long partnerId) throws Exception {
        List<PartnerServices> partnerServicesList = serviceDao.getPartnerServiceListByPartnerId(partnerId);
        List<PartnerServiceResponseDTO> partnerServiceResponseDTOList = new ArrayList<>();
        if (!partnerServicesList.isEmpty()) {
            partnerServicesList.stream().forEach(partnerServices -> {
                partnerServiceResponseDTOList.add(modelMapper.map(partnerServices, PartnerServiceResponseDTO.class));
            });
        }
        return partnerServiceResponseDTOList;
    }

    public List<ServiceDTO> getServicesByServiceType(Long serviceTypeId) throws Exception {
        List<Services> servicesList = serviceDao.getServiceListByServiceType(serviceTypeId);
        List<ServiceDTO> serviceDTOArrayList = new ArrayList<>();
        if (servicesList.isEmpty()) {
            servicesList.stream().forEach(servicesM -> {
                serviceDTOArrayList.add(modelMapper.map(servicesM, ServiceDTO.class));
            });
        }
        return serviceDTOArrayList;
    }

    public void deleteServiceById(Long serviceId) {
        Services services = new Services();
        services.setId(serviceId);
        abstractDao.delete(services);
    }
}

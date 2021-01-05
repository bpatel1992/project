package com.rahul.project.gateway.service;

import com.rahul.project.gateway.configuration.BusinessException;
import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.crud.controller.CrudCtrlBase;
import com.rahul.project.gateway.dao.AbstractDao;
import com.rahul.project.gateway.dto.*;
import com.rahul.project.gateway.model.Partner;
import com.rahul.project.gateway.model.*;
import com.rahul.project.gateway.model.Pet;
import com.rahul.project.gateway.repository.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;

import java.lang.invoke.MethodHandles;
import java.util.*;
import java.util.stream.Collectors;

@TransactionalService
public class PartnerClientManagementService {

    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    AbstractDao abstractDao;

    @Autowired
    private Environment environment;

    @Autowired
    private UserPartnerRelationMPRepository userPartnerRelationMPRepository;

    @Autowired
    private RelationMRepository relationMRepository;


    public boolean saveClientMapping(AddClientDto addClientDto) {
        UserPartnerRelationMP userPartnerRelationMP = new UserPartnerRelationMP();
        User user = abstractDao.getEntityById(User.class, addClientDto.getId());
        Partner partner = abstractDao.getEntityById(Partner.class, addClientDto.getCreatedByPartnerId());
        Pet pet = abstractDao.getEntityById(Pet.class, addClientDto.getPetId());
        if (Objects.isNull(user) || Objects.isNull(partner) || Objects.isNull(pet)) {
            new BusinessException("User, partner or pet not found!");
        }
        userPartnerRelationMP.setPartner(partner);
        userPartnerRelationMP.setUser(user);
        userPartnerRelationMP.setPet(pet);
        userPartnerRelationMP.setCreated(new Date());
        userPartnerRelationMP.setRelation(relationMRepository.getByRelationName("Owner"));
        return userPartnerRelationMPRepository.save(userPartnerRelationMP) != null ? Boolean.TRUE : Boolean.FALSE;
    }

    public boolean saveClientMapping(User user, Partner partner, Pet pet) {
        UserPartnerRelationMP userPartnerRelationMP = new UserPartnerRelationMP();
        userPartnerRelationMP.setPartner(partner);
        userPartnerRelationMP.setUser(user);
        userPartnerRelationMP.setPet(pet);
        return userPartnerRelationMPRepository.save(userPartnerRelationMP) != null ? Boolean.TRUE : Boolean.FALSE;
    }

    public UserDTO fetchClientDetails(AddClientDto addClientDto) throws Exception {
        UserDTO userDTO = new UserDTO();
        if (Objects.nonNull(addClientDto.getId())) {
            User user = abstractDao.getEntityById(User.class, addClientDto.getId());
            if (Objects.isNull(user)) {
                new BusinessException("User not found!");
            }
            if (!CollectionUtils.isEmpty(user.getAuthorities())) {
                boolean isClient = user.getAuthorities().stream()
                        .anyMatch(authority -> StringUtils.equalsAnyIgnoreCase(authority.getName(), "ROLE_CUSTOMER"));
                if (isClient) {
                    CrudCtrlBase.copyNonNullProperties(user, userDTO);
                    setUserDetails(user, userDTO);
                }
            }
        }
        return userDTO;
    }

    private UserDTO setUserDetails(User user, UserDTO userDTO) {
        userDTO.setPhone("+" + user.getCountry().getCode() + "-" + user.getMobile());
        Set<PartnerAddressDTO> partnerAddresses = user.getPartnerAddresses().stream()
                .filter(address -> address.getDisplayOrder().equals(1))
                .map(address ->
                {
                    PartnerAddressDTO partnerAddressDTO = new PartnerAddressDTO();
                    partnerAddressDTO.setAddress(address.getAddress());
                    return partnerAddressDTO;
                }).collect(Collectors.toSet());
        userDTO.setPartnerAddresses(partnerAddresses);
        userDTO.setImage(user.getImageName());
        List<PetDTO> petDTOS = user.getPets()
                .stream()
                .map(pet -> {
                    PetDTO petDTO = new PetDTO();
                    petDTO.setGender(petDTO.getGender());
                    petDTO.setImageURL(pet.getImageName());
                    PetBreedDTO petBreedDTO = new PetBreedDTO();
                    petBreedDTO.setName(pet.getName());
                    petDTO.setPetBreed(petBreedDTO);
                    petDTO.setYearOld(pet.getYearOld());
                    return petDTO;
                }).collect(Collectors.toList());
        userDTO.setPets(petDTOS);
        return userDTO;
    }

    public List<AddClientDto> fetchClientListByPartnerId(AddClientDto addClientDto) throws Exception {
        List<UserPartnerRelationMP> userPartnerRelationMPList = userPartnerRelationMPRepository.fetchCustomersByPartnerId(addClientDto.getCreatedByPartnerId(),
                PageRequest.of(addClientDto.getPageRequestDTO().getPageNo(), addClientDto.getPageRequestDTO().getPageSize()));
        List<AddClientDto> addClientDtoList = userPartnerRelationMPList.stream().map(
                userPartnerRelationMP -> {
                    AddClientDto addClientDto1 = new AddClientDto();
                    User user = userPartnerRelationMP.getUser();
                    addClientDto1.setFullName(user.getFullName());
                    addClientDto1.setClientImageName(user.getImageName());
                    addClientDto1.setPetId(addClientDto.getPetId());
                    addClientDto1.setCreatedByPartnerId(addClientDto.getCreatedByPartnerId());
                    addClientDto1.setEmail(user.getEmail());
                    addClientDto1.setMobile("+" + user.getCountry().getCode() + "-" + user.getMobile());
                    addClientDto1.setLastVisit(user.getLastVisit());
                    return addClientDto1;
                }).collect(Collectors.toList());
        return addClientDtoList;
    }


}

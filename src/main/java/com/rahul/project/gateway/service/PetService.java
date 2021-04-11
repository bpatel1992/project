package com.rahul.project.gateway.service;

import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.dao.AbstractDao;
import com.rahul.project.gateway.dto.ResponseHandlerDTO;
import com.rahul.project.gateway.dto.pet.CreatePetDTO;
import com.rahul.project.gateway.dto.pet.PetDTO;
import com.rahul.project.gateway.model.*;
import com.rahul.project.gateway.repository.UserPetRelationMPRepository;
import com.rahul.project.gateway.utility.CommonUtility;
import org.hibernate.query.Query;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @author rahul malhotra
 * @version 1.0
 * @Date 2019-04-30
 */

@TransactionalService
public class PetService {

    private final AbstractDao abstractDao;
    private final UserPetRelationMPRepository userPetRelationMPRepository;
    private final ModelMapper modelMapper;
    private final CommonUtility commonUtility;
    PropertyMap<CreatePetDTO, Pet> petMapping = new PropertyMap<CreatePetDTO, Pet>() {
        protected void configure() {
            map().getWeightUnit().setId(source.getWeightUnitId());
            map().getGender().setId(source.getGenderId());
            map().getPetType().setId(source.getPetTypeId());
            map().getPetBreed().setId(source.getPetBreedId());
            map().getCreatedByPartner().setId(source.getPartnerId());
        }
    };
    PropertyMap<Pet, CreatePetDTO> petFieldMapping = new PropertyMap<Pet, CreatePetDTO>() {
        protected void configure() {
            map().setWeightUnitId(source.getWeightUnit().getId());
            map().setGenderId(source.getGender().getId());
            map().setPetTypeId(source.getPetType().getId());
            map().setPetBreedId(source.getPetBreed().getId());
            map().setPartnerId(source.getCreatedByPartner().getId());
        }
    };

    public PetService(ModelMapper modelMapper, AbstractDao abstractDao, UserPetRelationMPRepository userPetRelationMPRepository, CommonUtility commonUtility) {
        this.modelMapper = modelMapper;
        this.modelMapper.addMappings(petFieldMapping);
        this.modelMapper.addMappings(petMapping);
        this.abstractDao = abstractDao;
        this.userPetRelationMPRepository = userPetRelationMPRepository;
        this.commonUtility = commonUtility;
    }

    public ResponseHandlerDTO<List<PetDTO>> getPetList() throws Exception {
        Long loggedInUser = commonUtility.getLoggedInUser();
        return new ResponseHandlerDTO();
    }

    public CreatePetDTO registerPetAdmin(CreatePetDTO createPetDTO) throws Exception {
        Pet pet = modelMapper.map(createPetDTO, Pet.class);
        if (createPetDTO.getBirthDay() != null)
            pet.setDob(commonUtility.getDateConvertedDay(createPetDTO.getBirthDay()));
        pet.setRandomKey(commonUtility.generatePetRandomKey());
        abstractDao.saveOrUpdateEntity(pet);

        UserPetRelationMP userPetRelation = userPetRelationMPRepository.getUserPetRelation(createPetDTO.getCustomerId(), pet.getId());
        if (userPetRelation == null) {
            userPetRelation = new UserPetRelationMP();
            UserPetRelationMpPK userPetRelationMpPK = new UserPetRelationMpPK();
            userPetRelationMpPK.setUser(new User(createPetDTO.getCustomerId()));
            userPetRelationMpPK.setPet(new Pet(pet.getId()));
            userPetRelation.setId(userPetRelationMpPK);
            userPetRelation.setRelationM(new RelationM(1L));
            abstractDao.saveOrUpdateEntity(userPetRelation);
        }
        return modelMapper.map(pet, CreatePetDTO.class);
    }

    public List<PetBreed> getPetData(List<String> labels) {
        CriteriaBuilder builder = abstractDao.getSession().getCriteriaBuilder();
        CriteriaQuery<PetBreed> criteria = builder.createQuery(PetBreed.class);
        Root<PetBreed> root = criteria.from(PetBreed.class);
        criteria.select(root)
                .where(root.get("name").in(labels));
        Query<PetBreed> petBreedQuery = abstractDao.getSession().createQuery(criteria);
        return petBreedQuery.getResultList();
    }

    public List<PetBreed> getPetBreeds() {
        CriteriaBuilder builder = abstractDao.getSession().getCriteriaBuilder();
        CriteriaQuery<PetBreed> criteria = builder.createQuery(PetBreed.class);
        Root<PetBreed> root = criteria.from(PetBreed.class);
        criteria.select(root);
        Query<PetBreed> petBreedQuery = abstractDao.getSession().createQuery(criteria);
        List<PetBreed> petBreeds = petBreedQuery.getResultList();
        List<PetBreed> petBreedss = new ArrayList<>();
        if (petBreeds != null) {
            for (PetBreed petBreed : petBreeds) {
                PetBreed petBreed1 = new PetBreed();
                modelMapper.map(petBreed, petBreed1);
                petBreedss.add(petBreed1);
            }
        }
        return petBreedss;
    }

}

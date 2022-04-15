package com.rahul.project.gateway.service;

import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.dto.VaccinationDTO;
import com.rahul.project.gateway.dto.pet.CreatePetDTO;
import com.rahul.project.gateway.model.Pet;
import com.rahul.project.gateway.model.Vaccination;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

@TransactionalService
public class VaccinationService {

    private final ModelMapper modelMapper;

    @Autowired
    Environment environment;
    PropertyMap<VaccinationDTO, Vaccination> petMapping = new PropertyMap<CreatePetDTO, Pet>() {
        protected void configure() {
            map().get().setId(source.getWeightUnitId());
            map().getGender().setId(source.getGenderId());
            map().getPetType().setId(source.getPetTypeId());
            map().getPetBreed().setId(source.getPetBreedId());
            map().getCreatedByPartner().setId(source.getPartnerId());
        }
    };

    public VaccinationService(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
        modelMapper.addMappings(petMapping);

    }

    public Vaccination addVaccinationDetails(Vaccination vaccination){
        Vaccination vaccinationDetails = modelMapper.map(vaccination, Vaccination.class);
        return null;
    }
}

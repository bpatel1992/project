package com.rahul.project.gateway.crud.uiBeans;

import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.crud.annotation.UIBeanSpecifier;
import com.rahul.project.gateway.dto.Client;
import com.rahul.project.gateway.dto.CustomerReportDTO;
import com.rahul.project.gateway.dto.Pet;
import com.rahul.project.gateway.model.User;
import com.rahul.project.gateway.utility.CommonUtility;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@UIBeanSpecifier(id = "1", beanClass = BNEPet.class)
@TransactionalService(value = "BNEPet")
public class BNEPet implements BNE {

    @Autowired
    CommonUtility commonUtility;
    @Autowired
    Environment environment;

    @Override
    public List process(List list) {
        List<CustomerReportDTO> reportDTOS = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (com.rahul.project.gateway.model.Pet pet1 : (List<com.rahul.project.gateway.model.Pet>) list) {

                CustomerReportDTO customerReportDTO = new CustomerReportDTO();

                List<Client> clients = new ArrayList<>();
                if (pet1.getUsers() != null && pet1.getUsers().size() > 0) {
                    for (User user : pet1.getUsers()) {
                        Client client = new Client(user.getId());
                        client.setName((user.getTitle() != null ?
                                (user.getTitle().getLabel() != null ? user.getTitle().getLabel() : user.getTitle().getTitle() + " ") : "")
                                + user.getFullName());
                        client.setImage(user.getImageName() != null ?
                                environment.getRequiredProperty("gateway.api.url") + "assets/user/profile?randomKey=" + user.getRandomKey() : null);
                        clients.add(client);
                    }
                    customerReportDTO.setClients(clients);
                }

                List<Pet> pets = new ArrayList<>();
                Pet pet = new Pet(pet1.getId());
                pet.setName(pet1.getName());
                pet.setImage(pet1.getImageName() != null ?
                        environment.getRequiredProperty("gateway.api.url") + "assets/pet/profile?randomKey=" + pet1.getRandomKey() : null);
                pets.add(pet);
                customerReportDTO.setPets(pets);

                customerReportDTO.setPetBreed(pet1.getPetBreed().getName());
                customerReportDTO.setPetType(pet1.getPetBreed().getPetType().getLabel() != null ? pet1.getPetBreed().getPetType().getLabel()
                        : pet1.getPetBreed().getPetType().getName());
                customerReportDTO.setYearOld(pet1.getYearOld());
                customerReportDTO.setLastVisit(commonUtility.getDateFormatReport(pet1.getModificationDate()));

                reportDTOS.add(customerReportDTO);
            }
        }
        return reportDTOS;
    }

    @Override
    public Object process(Object o) {
        return null;
    }
}

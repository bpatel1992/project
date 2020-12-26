package com.rahul.project.gateway.crud.uiBeans;

import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.crud.annotation.UIBeanSpecifier;
import com.rahul.project.gateway.dto.Client;
import com.rahul.project.gateway.dto.CustomerReportDTO;
import com.rahul.project.gateway.dto.Pet;
import com.rahul.project.gateway.model.User;
import com.rahul.project.gateway.repository.UserRepository;
import com.rahul.project.gateway.utility.CommonUtility;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@UIBeanSpecifier(id = "1", beanClass = BNECustomer.class)
@TransactionalService(value = "BNECustomer")
public class BNECustomer implements BNE {

    @Autowired
    CommonUtility commonUtility;

    @Autowired
    Environment environment;

    @Autowired
    UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    private Long id;

    public BNECustomer(User user) {
        this.id = user.getId();
    }

    private CustomerReportDTO processData(User user) {
        CustomerReportDTO customerReportDTO = modelMapper.map(user, CustomerReportDTO.class);

        List<Client> clients = new ArrayList<>();
        Client client = new Client(user.getId());
        client.setName((user.getTitle() != null ? user.getTitle().getTitle() : "") + " " + user.getFirstName() + " " + user.getLastName());
        client.setImage(user.getImageName() != null ?
                environment.getRequiredProperty("gateway.api.url") + "assets/user/profile?randomKey=" + user.getRandomKey() : null);
        clients.add(client);
        customerReportDTO.setClients(clients);
        customerReportDTO.setGender(user.getGender().getName());

        if (user.getPets() != null && user.getPets().size() > 0) {
            List<Pet> pets = new ArrayList<>();
            for (com.rahul.project.gateway.model.Pet pet : user.getPets()) {
                Pet pet1 = new Pet(pet.getId());
                pet1.setName(pet.getName());
                pet1.setImage(pet.getImageName() != null ?
                        environment.getRequiredProperty("gateway.api.url") + "assets/pet/profile?randomKey=" + pet.getRandomKey() : null);
                pets.add(pet1);
            }
            customerReportDTO.setPets(pets);
        }

        customerReportDTO.setMobile((user.getMobile() != null) ?
                (((user.getCountry() != null && user.getCountry().getCode() != null) ? ("+" + user.getCountry().getCode() + "-") : "") + user.getMobile()) : null);
        customerReportDTO.setEmail(user.getEmail());
        customerReportDTO.setLastVisit(commonUtility.getDateFormatReport(user.getLastVisit()));
        customerReportDTO.setRandomKey(user.getRandomKey());
        return customerReportDTO;
    }

    @Override
    public List process(List list) {
        List<CustomerReportDTO> customerReportDTOS = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (User user : (List<User>) list) {
                customerReportDTOS.add(processData(user));
            }
        }
        return customerReportDTOS;
    }

    @Override
    public Object process(Object o) {
        User user = (User) o;
        user.setRandomKey(commonUtility.generateUserRandomKey());
        user.setFullName(user.getFirstName() + " " + user.getLastName());
        userRepository.save(user);
        return processData(user);
    }
}

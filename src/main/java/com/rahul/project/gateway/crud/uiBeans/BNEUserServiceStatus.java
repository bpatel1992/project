package com.rahul.project.gateway.crud.uiBeans;

import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.crud.annotation.UIBeanSpecifier;
import com.rahul.project.gateway.dto.UserServiceStatusDTO;
import com.rahul.project.gateway.model.UserServiceStatus;
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
@UIBeanSpecifier(id = "1", beanClass = BNEUserServiceStatus.class)
@TransactionalService(value = "BNEUserServiceStatus")
public class BNEUserServiceStatus implements BNE {

    @Autowired
    private Environment environment;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List process(List list) {
        List<UserServiceStatusDTO> statusDTOS = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (UserServiceStatus serviceStatus : (List<UserServiceStatus>) list) {
                UserServiceStatusDTO serviceStatusDTO = modelMapper.map(serviceStatus, UserServiceStatusDTO.class);
                serviceStatusDTO.setUserId(serviceStatus.getUserId().getId());
                serviceStatusDTO.setServiceId(serviceStatus.getServiceId().getId());
                statusDTOS.add(serviceStatusDTO);
            }
        }
        return statusDTOS;
    }

    @Override
    public Object process(Object o) {
        return modelMapper.map(o, UserServiceStatusDTO.class);
    }
}

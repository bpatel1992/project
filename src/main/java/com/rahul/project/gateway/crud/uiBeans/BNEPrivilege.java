package com.rahul.project.gateway.crud.uiBeans;


import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.crud.annotation.UIBeanSpecifier;
import com.rahul.project.gateway.dto.FunctionalityDTO;
import com.rahul.project.gateway.dto.PrivilegeDTO;
import com.rahul.project.gateway.model.Functionality;
import com.rahul.project.gateway.model.Privilege;
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
@UIBeanSpecifier(id = "1", beanClass = BNEPrivilege.class)
@TransactionalService(value = "BNEPrivilege")
public class BNEPrivilege implements BNE {

    @Autowired
    Environment environment;

    private ModelMapper modelMapper;

    @Override
    public List process(List list) {
        List<PrivilegeDTO> masterDataDTOS = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (Privilege privilege : (List<Privilege>) list) {
                masterDataDTOS.add(processEntity(privilege));
            }
        }
        return masterDataDTOS;
    }

    @Override
    public Object process(Object o) {
        return processEntity((Privilege) o);
    }

    private PrivilegeDTO processEntity(Privilege privilege) {
        PrivilegeDTO privilegeDTO = modelMapper.map(privilege, PrivilegeDTO.class);
        return privilegeDTO;
    }

    @Autowired
    public BNEPrivilege(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
        // modelMapper.addMappings(roleMapping);
        //modelMapper.addMappings(roleFieldMapping);
    }


//    PropertyMap<RoleDTO, Role> roleMapping = new PropertyMap<RoleDTO, Role>() {
//        protected void configure() {
////            businessTimings = RoleDTO.class
////                    .stream().map(tim -> modelMapper.map(tim, BusinessTiming.class))
////                    .collect(Collectors.toSet());
////            businessTimings.forEach(businessTiming -> abstractDao.saveOrUpdateEntity(businessTiming.getTimeRange()));
////            map.
////            map().getServices().setId(source.getServiceId());
//        }
//    };
//    PropertyMap<Role, RoleDTO> roleFieldMapping = new PropertyMap<Role, RoleDTO>() {
//        protected void configure() {
//            // map().setServiceId(source.getServices().getId());
//
//        }
//    };

}

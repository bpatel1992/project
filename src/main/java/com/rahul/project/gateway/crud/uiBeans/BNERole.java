package com.rahul.project.gateway.crud.uiBeans;

import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.crud.annotation.UIBeanSpecifier;
import com.rahul.project.gateway.dto.RoleDTO;
import com.rahul.project.gateway.model.Role;
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
@UIBeanSpecifier(id = "1", beanClass = BNERole.class)
@TransactionalService(value = "BNERole")
public class BNERole implements BNE {
    @Autowired
    Environment environment;

    private ModelMapper modelMapper;

    @Override
    public List process(List list) {
        List<RoleDTO> masterDataDTOS = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (Role role : (List<Role>) list) {
                masterDataDTOS.add(processEntity(role));
            }
        }
        return masterDataDTOS;
    }

    @Override
    public Object process(Object o) {
        return processEntity((Role) o);
    }

    private RoleDTO processEntity(Role role) {
       // RoleDTO roleDTO = modelMapper.map(role, RoleDTO.class);
        RoleDTO roleDTO=new RoleDTO();
//        if (role.getRoleFunctionality() != null) {
//            List<Functionality> collect = role.getRoleFunctionality().stream().map(roleFunctionality -> {
//
//                Functionality functionality = new Functionality();
//                functionality.setName(roleFunctionality.getFunctionality().getName());
//                return functionality;
//            }).collect(Collectors.toList());
//            collect.sort(Comparator.comparing(Functionality::getDisplayOrder));
//            roleDTO.setFunctionalities(collect);

    //    }
        return roleDTO;
    }

//    @Autowired
//    public BNERole(ModelMapper modelMapper){
//        this.modelMapper = modelMapper;
//        modelMapper.addMappings(roleMapping);
//        modelMapper.addMappings(roleFieldMapping);
//    }
//
//
//    PropertyMap<RoleDTO, Role> roleMapping = new PropertyMap<RoleDTO, Role>() {
//        protected void configure() {
////            businessTimings = .RoleDTOclass
////                    .stream().map(tim -> modelMapper.map(tim, BusinessTiming.class))
////                    .collect(Collectors.toSet());
////            businessTimings.forEach(businessTiming -> abstractDao.saveOrUpdateEntity(businessTiming.getTimeRange()));
////            map.
////            map().getServices().setId(source.getServiceId());
//        }
//    };
//    PropertyMap<Role, RoleDTO> roleFieldMapping = new PropertyMap<Role, RoleDTO>() {
//        protected void configure() {
//
//            Set<RoleFunctionality> roleFunctionality = source.getRoleFunctionality();
//            map().setRoleFunctionality(roleFunctionality.stream().map(rf ->
//                    modelMapper.map(rf, com.rahul.project.gateway.dto.RoleFunctionality.class))
//                    .collect(Collectors.toList()));
//            // map().setServiceId(source.getServices().getId());
//
//        }
//    };

}

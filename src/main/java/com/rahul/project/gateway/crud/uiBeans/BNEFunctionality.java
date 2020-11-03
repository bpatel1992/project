package com.rahul.project.gateway.crud.uiBeans;


import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.crud.annotation.UIBeanSpecifier;
import com.rahul.project.gateway.dto.FunctionalityDTO;
import com.rahul.project.gateway.model.Functionality;
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
@UIBeanSpecifier(id = "1", beanClass = BNEFunctionality.class)
@TransactionalService(value = "BNEFunctionality")
public class BNEFunctionality implements BNE{
    @Autowired
    Environment environment;

    private ModelMapper modelMapper;

    @Override
    public List process(List list) {
        List<FunctionalityDTO> masterDataDTOS = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (Functionality functionality : (List<Functionality>) list) {
                masterDataDTOS.add(processEntity(functionality));
            }
        }
        return masterDataDTOS;
    }

    @Override
    public Object process(Object o) {
        return processEntity((Functionality) o);
    }

    private FunctionalityDTO processEntity(Functionality functionality) {
        FunctionalityDTO functionalityDTO = modelMapper.map(functionality, FunctionalityDTO.class);
        return functionalityDTO;
    }

    @Autowired
    public BNEFunctionality(ModelMapper modelMapper){
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

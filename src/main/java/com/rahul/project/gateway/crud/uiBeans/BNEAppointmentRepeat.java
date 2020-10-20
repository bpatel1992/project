package com.rahul.project.gateway.crud.uiBeans;

import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.crud.annotation.UIBeanSpecifier;
import com.rahul.project.gateway.dto.MasterDataDTO;
import com.rahul.project.gateway.model.AppointmentRepeat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@UIBeanSpecifier(id = "1", beanClass = BNEAppointmentRepeat.class)
@TransactionalService(value = "BNEAppointmentRepeat")
public class BNEAppointmentRepeat implements BNE {

    @Override
    public List process(List list) {
        List<MasterDataDTO> masterDataDTOS = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (AppointmentRepeat appointmentType : (List<AppointmentRepeat>) list) {
                MasterDataDTO masterDataDTO = new MasterDataDTO();

                masterDataDTO.setId(appointmentType.getId());
                masterDataDTO.setName(appointmentType.getLabel() != null ? appointmentType.getLabel() : appointmentType.getAppointmentRepeat());

                masterDataDTOS.add(masterDataDTO);
            }
        }
        return masterDataDTOS;
    }

    @Override
    public Object process(Object o) {
        return null;
    }
}

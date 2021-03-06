package com.rahul.project.gateway.crud.uiBeans;

import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.crud.annotation.UIBeanSpecifier;
import com.rahul.project.gateway.dto.EnquiryDTO;
import com.rahul.project.gateway.model.Enquiry;
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
@UIBeanSpecifier(id = "1", beanClass = BNEEnquiry.class)
@TransactionalService(value = "BNEEnquiry")
public class BNEEnquiry implements BNE {

    @Autowired
    CommonUtility commonUtility;
    @Autowired
    Environment environment;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public List process(List list) {
        List<EnquiryDTO> enquiryDTOS = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (Enquiry enquiry : (List<Enquiry>) list) {
                EnquiryDTO enquiryDTO;
                enquiryDTO = modelMapper.map(enquiry, EnquiryDTO.class);
                enquiryDTO.setNameFormatted(commonUtility.getTitle(enquiry.getTitle()) + enquiry.getName());
                String cId = enquiry.getCountry() != null ? "+" + enquiry.getCountry().getCode().toString() + "-" : null;
                enquiryDTO.setPhone(cId != null ? cId + enquiry.getMobile() : (enquiry.getMobile() != null ? enquiry.getMobile().toString() : null));

                enquiryDTOS.add(enquiryDTO);
            }
        }
        return enquiryDTOS;
    }

    @Override
    public Object process(Object o) {
        return null;
    }
}

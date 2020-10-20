package com.rahul.project.gateway.crud.uiBeans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.crud.annotation.UIBeanSpecifier;
import com.rahul.project.gateway.dto.PartnerDTO;
import com.rahul.project.gateway.model.Partner;
import com.rahul.project.gateway.model.UserPartnerRelationMP;
import com.rahul.project.gateway.repository.RelationMRepository;
import com.rahul.project.gateway.repository.UserPartnerRelationMPRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@UIBeanSpecifier(id = "1", beanClass = BNEPartnerCard.class)
@TransactionalService(value = "BNEPartnerCard")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BNEPartnerCard implements BNE {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserPartnerRelationMPRepository userPartnerRelationMPRepository;

    @Autowired
    private RelationMRepository relationMRepository;

    private Long id;

    public BNEPartnerCard(Partner partner) {
        this.id = partner.getId();
    }

    @Override
    public List process(List list) {
        List<PartnerDTO> partnerDTOS = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (Partner partner : (List<Partner>) list) {
                partnerDTOS.add(processData(partner));
            }
        }
        return partnerDTOS;
    }

    @Override
    public Object process(Object o) {
        Partner partner = (Partner) o;
        UserPartnerRelationMP byUserAndAndPartner = userPartnerRelationMPRepository.getByUserAndAndPartner
                (partner.getUsers().iterator().next(), partner);
        byUserAndAndPartner.setRelation(relationMRepository.getByRelationName("Owner"));
        userPartnerRelationMPRepository.save(byUserAndAndPartner);
        return processData(partner);
    }

    @Override
    public Object updateProcess(Object o) {
        return processData((Partner) o);
    }

    private PartnerDTO processData(Partner partner) {
        PartnerDTO partnerDTO = modelMapper.map(partner, PartnerDTO.class);
        return partnerDTO;
    }
}

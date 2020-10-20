package com.rahul.project.gateway.crud.service;

import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.crud.uiBeans.BNE;
import com.rahul.project.gateway.dto.ECardDTO;
import com.rahul.project.gateway.model.Partner;
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
@TransactionalService(value = "CULPartner")
public class EcardService implements BNE {

    @Autowired
    ModelMapper modelMapper;

    @Override
    public List process(List list) {
        List<ECardDTO> customerReportDTOS = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (Partner partner : (List<Partner>) list) {
                customerReportDTOS.add(processData(partner));
            }
        }
        return customerReportDTOS;
    }

    @Override
    public Object process(Object o) {

        return processData((Partner) o);
    }

    @Override
    public Object updateProcess(Object o) {
        return null;
    }

    private ECardDTO processData(Partner partner) {
        ECardDTO eCardDTO = modelMapper.map(partner, ECardDTO.class);
        return eCardDTO;
       /* {
            eCardDTO.phone = "+" + partner.getCountry().getCode() + "-" + partner.getMobile();
            eCardDTO.whatsAppPhone = eCardDTO.phone;
            eCardDTO.address = partner.getAddress();
            eCardDTO.addressLink = "https://www.google.com/maps/search/" + partner.getAddress();
            eCardDTO.phoneLink = "tel:" + partner.getCountry().getCode() + partner.getMobile();
            eCardDTO.email = partner.getEmail();
            eCardDTO.emailLink = "mailto:" + eCardDTO.email;
            eCardDTO.name =
                    ((partner.getTitle().getLabel() != null && !"".equalsIgnoreCase(partner.getTitle().getLabel()))
                            ? partner.getTitle().getLabel() : partner.getTitle().getTitle())
                            + " " + partner.getName();
            eCardDTO.profession =
                    (partner.getProfession().getLabel() != null && !"".equalsIgnoreCase(partner.getProfession().getLabel()))
                            ? partner.getProfession().getLabel() : partner.getProfession().getProfession();
            eCardDTO.youtubeLink = partner.getYoutubeLink();
            eCardDTO.twitterLink = partner.getTwitterLink();
            eCardDTO.instagramLink = partner.getInstagramLink();
            String text = TextSource.getText("user.ecard.contact", new String[]{eCardDTO.name});
            eCardDTO.whatsAppLink = "https://api.whatsapp.com/send?phone=" + "+" + partner.getCountry().getCode() +
                    partner.getMobile() + "&text=" + URLEncoder.encode(text, "UTF-8");
            eCardDTO.whatsShareLink = "https://api.whatsapp.com/send?text=" +
                    URLEncoder.encode(TextSource.getText("user.ecard.share",
                            new String[]{PropertySource.getRequiredProperty("application.url") + "ecard/" + partner.getUserName()}), "UTF-8");
            eCardDTO.fbLink = partner.getFbLink();
            if (partner.getPartnerAddresses() != null) {
                List<BusinessAddress> businessAddresses = new ArrayList<>();
                for (PartnerAddress partnerAddress : partner.getPartnerAddresses()) {
                    BusinessAddress businessAddress = new BusinessAddress();
                    businessAddress.setBusinessAddress(partnerAddress.getAddress());
                    businessAddress.setBusinessAddressLink("https://www.google.com/maps/search/" + partnerAddress.getAddress());
                    businessAddress.setTitle(partnerAddress.getName());
                    businessAddress.setDisplayOrder(partnerAddress.getDisplayOrder());
//                if (partnerAddress.getBusinessTimings() != null) {
//                    Set<BusinessTimingFlow> businessTimingFlows = new HashSet<>();
//                    for (BusinessTiming businessTiming : partnerAddress.getBusinessTimings()) {
//                        BusinessTimingFlow businessTimingFlow = new BusinessTimingFlow();
//                        businessTimingFlow.setDays(businessTiming.getDayRange());
//                        if (businessTiming.getTimeRange() != null) {
//                            List<TimeRangeFlow> timeRangeFlows = new ArrayList<>();
//                            for (TimeRange timeRange : businessTiming.getTimeRange()) {
//                                TimeRangeFlow timeRangeFlow = new TimeRangeFlow();
//                                timeRangeFlow.setTimeRange(timeRange.getTimeRange());
//                                timeRangeFlow.setDisplayOrder(timeRange.getDisplayOrder());
//                                timeRangeFlows.add(timeRangeFlow);
//                            }
//                            if (timeRangeFlows.size() > 1)
//                                timeRangeFlows.sort(Comparator.comparing(TimeRangeFlow::getDisplayOrder));
//                            List<String> strings = new ArrayList<>();
//                            timeRangeFlows.forEach(timeRangeFlow -> strings.add(timeRangeFlow.getTimeRange()));
//                            businessTimingFlow.setTime(strings.toArray(new String[0]));
//                        }
//                        businessTimingFlows.add(businessTimingFlow);
//                        businessAddress.setTimings(businessTimingFlows);
//                    }
//                }
                    businessAddresses.add(businessAddress);
                }
                if (businessAddresses.size() > 1)
                    businessAddresses.sort(Comparator.comparing(BusinessAddress::getDisplayOrder));
                eCardDTO.businessAddress = businessAddresses;
            }
        }

        return eCardDTO;
    }*/
    }
}

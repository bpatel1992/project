package com.rahul.project.gateway.crud.uiBeans;

import com.rahul.project.gateway.crud.annotation.UIBeanSpecifier;
import com.rahul.project.gateway.model.Partner;
import com.rahul.project.gateway.utility.PropertySource;
import com.rahul.project.gateway.utility.TextSource;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.net.URLEncoder;
import java.util.List;

@Getter
@Setter
@UIBeanSpecifier(id = "1", beanClass = ECardFlow.class)
@NoArgsConstructor
public class ECardFlow implements Serializable {

    private String businessName;//": "Pet & Vet Clinic",
    private List<BusinessAddress> businessAddress;
    private String phone;//": "7827836303",
    private String address;//": "K-2, Brahmaputra Shopping Complex, Sector 29, Noida",
    private String addressLink;
    private String whatsAppPhone;//": "+91-9711405054",
    private String email;//": "rishi14sood@yahoo.co.in",
    private String name;//": "Dr. Rishi Sood",
    private String profession;//": "Veterinarian",
    private String fbLink;//": "",
    private String youtubeLink;//": "",
    private String instagramLink;//": "",
    private String twitterLink;//": "",
    private String whatsAppLink;//": "",
    private String whatsShareLink;//": "",
    private String emailLink;//": "",
    private String phoneLink;//": "",

    public ECardFlow(Partner partner) throws Exception {
        this.businessName = partner.getBusinessName();
        this.phone = "+" + partner.getCountry().getCode() + "-" + partner.getMobile();
        this.whatsAppPhone = this.phone;
        this.address = partner.getAddress();
        this.addressLink = "https://www.google.com/maps/search/" + partner.getAddress();
        this.phoneLink = "tel:" + partner.getCountry().getCode() + partner.getMobile();
        this.email = partner.getEmail();
        this.emailLink = "mailto:" + this.email;
        this.name =
                partner.getTitle() != null ? ((partner.getTitle().getLabel() != null && !"".equalsIgnoreCase(partner.getTitle().getLabel()))
                        ? partner.getTitle().getLabel() + " " : partner.getTitle().getTitle() + " ") : ""
                        + partner.getName();
        this.profession =
                partner.getProfession() != null ? (partner.getProfession().getLabel() != null && !"".equalsIgnoreCase(partner.getProfession().getLabel())
                        ? partner.getProfession().getLabel() : partner.getProfession().getProfession()) : "";
        this.youtubeLink = partner.getYoutubeLink();
        this.twitterLink = partner.getTwitterLink();
        this.instagramLink = partner.getInstagramLink();
        String text = TextSource.getText("user.ecard.contact", new String[]{this.name});
        this.whatsAppLink = "https://api.whatsapp.com/send?phone=" + "+" + partner.getCountry().getCode() +
                partner.getMobile() + "&text=" + URLEncoder.encode(text, "UTF-8");
        this.whatsShareLink = "https://api.whatsapp.com/send?text=" +
                URLEncoder.encode(TextSource.getText("user.ecard.share",
                        new String[]{PropertySource.getRequiredProperty("application.url") + "ecard/" + partner.getUserName()}), "UTF-8");
        this.fbLink = partner.getFbLink();
    }

}

package com.rahul.project.gateway.crud.service;

import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.crud.uiBeans.BNE;
import com.rahul.project.gateway.dao.AbstractDao;
import com.rahul.project.gateway.dto.DocumentDTO;
import com.rahul.project.gateway.model.PartnerDocument;
import com.rahul.project.gateway.service.PartnerService;
import com.rahul.project.gateway.utility.CommonUtility;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@TransactionalService(value = "BNEPartnerDocument")
public class BNEPartnerDocument implements BNE {

    @Autowired
    PartnerService partnerService;
    @Autowired
    private Environment environment;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CommonUtility commonUtility;
    @Autowired
    private AbstractDao abstractDao;

    @Override
    public List process(List list) {
        List<DocumentDTO> documentDTOS = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (PartnerDocument document : (List<PartnerDocument>) list) {
                DocumentDTO masterDataDTO = modelMapper.map(document, DocumentDTO.class);
                try {
                    File userFile = partnerService.getPartnerFile(document.getDocumentNumber());
                    if (userFile.exists())
                        masterDataDTO.setImage(environment.getRequiredProperty("gateway.api.url") + "assets/partner/e/card?key=" + document.getDocumentNumber());

                } catch (Exception e) {
                    e.printStackTrace();
                }
                documentDTOS.add(masterDataDTO);
            }
        }
        return documentDTOS;
    }

    @Override
    public Object process(Object o) {
        PartnerDocument partnerDocument = (PartnerDocument) o;
        partnerDocument.setDocumentNumber(commonUtility.get20DigitRandomKey());
        abstractDao.saveOrUpdateEntity(partnerDocument);
        return modelMapper.map(partnerDocument, DocumentDTO.class);
    }

    @Override
    public Object updateProcess(Object o) {
        return modelMapper.map(o, DocumentDTO.class);
    }
}

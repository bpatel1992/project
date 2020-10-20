package com.rahul.project.gateway.crud.service;

import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.crud.uiBeans.BNE;
import com.rahul.project.gateway.dto.DocumentDTO;
import com.rahul.project.gateway.model.UserDocument;
import com.rahul.project.gateway.service.PartnerService;
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
@TransactionalService(value = "BNEUserDocument")
public class BNEUserDocument implements BNE {

    @Autowired
    PartnerService partnerService;
    @Autowired
    private Environment environment;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List process(List list) {
        List<DocumentDTO> documentDTOS = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (UserDocument document : (List<UserDocument>) list) {
                DocumentDTO documentDTO = modelMapper.map(document, DocumentDTO.class);
                documentDTO.setDocTypeId(document.getDocumentType().getId());
                try {
                    File userFile = partnerService.getUserFile(document.getDocumentNumber());
                    if (userFile.exists())
                        documentDTO.setImage(environment.getRequiredProperty("gateway.api.url") + "assets/user/e/card?key=" + document.getDocumentNumber());

                } catch (Exception e) {
                    e.printStackTrace();
                }
                documentDTOS.add(documentDTO);
            }
        }
        return documentDTOS;
    }

    @Override
    public Object process(Object o) {
        return modelMapper.map(o, DocumentDTO.class);
    }
}

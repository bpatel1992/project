package com.rahul.project.gateway.crud.uiBeans;

import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.crud.annotation.UIBeanSpecifier;
import com.rahul.project.gateway.dto.CountryDTO;
import com.rahul.project.gateway.model.Country;
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
@UIBeanSpecifier(id = "1", beanClass = BNECountry.class)
@TransactionalService(value = "BNECountry")
public class BNECountry implements BNE {

    @Autowired
    private Environment environment;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List process(List list) {
        List<CountryDTO> countryDTOS = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (Country country : (List<Country>) list) {
                CountryDTO countryDTO = modelMapper.map(country, CountryDTO.class);
                countryDTO.setName(country.getLabel() != null ? country.getLabel() : country.getName());
                countryDTO.setImage(country.getImageName() != null ?
                        environment.getRequiredProperty("gateway.api.url") + "assets/file/fetch?fileName=" + country.getImageName() : null);
                countryDTOS.add(countryDTO);
            }
        }
        return countryDTOS;
    }

    @Override
    public Object process(Object o) {
        return modelMapper.map(o, CountryDTO.class);
    }
}

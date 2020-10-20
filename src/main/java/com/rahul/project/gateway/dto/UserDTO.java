package com.rahul.project.gateway.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rahul.project.gateway.utility.Patterns;
import lombok.Data;

import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {


    private CountryDTO countryDTO;
    private List<Department> departments;

    @Pattern(regexp = Patterns.Validator_EmailRegEx, message = "Validator.EmailRegMsg")
    private String email;

    private String fullName;
    private Partner partner;

    private String userType;
    private String phone;
    private List<RoleDTO> roles;
    private String title;
    private Long id;

}

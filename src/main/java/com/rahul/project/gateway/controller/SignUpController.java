package com.rahul.project.gateway.controller;

import com.rahul.project.gateway.configuration.annotations.RESTController;
import com.rahul.project.gateway.dto.*;
import com.rahul.project.gateway.dto.services.OauthResponse;
import com.rahul.project.gateway.model.User;
import com.rahul.project.gateway.service.SignUpService;
import com.rahul.project.gateway.utility.CommonUtility;
import io.swagger.annotations.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;
import java.text.ParseException;

/**
 * @author Rahul Malhotra
 * @since 1.0
 * Date 2019-05-21
 */
@RESTController
@Api(value = "API provide product basic functionalities",
        description = "This API provides below functionalities : " + "\n" +
                "1. To check identity of the user, " + "\n" +
                "2. Sign Up stage one, " + "\n" +
                "3. Sign Up stage two",tags = { "Sign-Up services" })
public class SignUpController {

    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    SignUpService signUpService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    CommonUtility commonUtility;

    @RequestMapping(value = "/api/sign-up", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public OauthResponse signUp(@Valid @RequestBody SignUpDto signUpDto) throws Exception {
        logger.info("inside register user !!");

        User user = signUpService.signUp(convertToEntity(signUpDto), signUpDto);
        return signUpService.loginAfterSignUp(user.getUserName(), signUpDto.getPassword());

    }

    @ApiOperation(value = "To check identity of the user", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/api/check/identifier", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseDTO checkIdentifier(@Valid @RequestBody CheckIdentifierDTO checkIdentifierDTO) throws Exception {
        return signUpService.checkIdentifier(checkIdentifierDTO, new ResponseDTO());
    }

    @ApiOperation(value = "Sign Up stage one", response = SignUpCustomerStage1Dto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully performed update"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PostMapping(value = "/api/sign-up/user-stage1", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public SignUpResponseDTO signUpUserStageOne(@ApiParam(value = "Sign Up DTO object", required = true)
                                                @Valid @RequestBody SignUpCustomerStage1Dto signUpCustomerDto) throws Exception {
        logger.info("inside register user !!");
        return signUpService.signUpCustomerStageOne(signUpCustomerDto);
    }

    @ApiOperation(value = "Sign Up stage two", response = SignUpCustomerStage2Dto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully performed update"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PostMapping(value = "/api/sign-up/user-stage2", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public OauthResponse signUpUserStageTwo(@ApiParam(value = "Sign Up DTO object", required = true)
                                            @Valid @RequestBody SignUpCustomerStage2Dto signUpCustomerDto) throws Exception {
        logger.info("inside register user !!");
        User user = signUpService.signUpCustomerStageTwo(signUpCustomerDto);
        return signUpService.loginAfterSignUp(user.getEmail(), user.getRandomKey());
    }

/*    @ApiOperation(value = "Sign Up stage three", response = SignUpCustomerStage3Dto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully performed update"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    @PostMapping(value = "/api/sign-up/user-stage3", produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public SignUpResponseDTO signUpUserStageThree(@ApiParam(value = "Sign Up DTO object", required = true)
                                                  @Valid @RequestBody SignUpCustomerStage3Dto signUpCustomerDto) throws Exception {
        logger.info("inside register user i.e. customer !!");
        return signUpService.signUpCustomerStageThree(signUpCustomerDto);
    }*/

    private User convertToEntity(SignUpDto signUpDto) throws ParseException {
        User user = modelMapper.map(signUpDto, User.class);
        if (user.getDob() != null) {
            user.setDob(commonUtility.getDateConverted(signUpDto.getDob()));
        }
        return user;
    }

    private void updateEmailStatus(){

    }
}

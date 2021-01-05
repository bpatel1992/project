package com.rahul.project.gateway.controller;

import com.rahul.project.gateway.configuration.annotations.RESTController;
import com.rahul.project.gateway.dto.AddClientDto;
import com.rahul.project.gateway.dto.UserDTO;
import com.rahul.project.gateway.dto.userreview.UserPartnerReviewDTO;
import com.rahul.project.gateway.service.UserPartnerReviewService;
import com.rahul.project.gateway.utility.CommonUtility;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;
import java.util.List;

/**
 * @author Rahul Malhotra
 * @since 1.0
 * Date 2019-05-21
 */
@RESTController
@Api(value = "API provide product basic functionalities",
        description = "This API provides below functionalities : " + "\n" +
                "1. Save user review mapping details " + "\n" +
                "2. Fetch user review list for partner id and status, pagination is applied for this api " + "\n" +
                "3. Approve review posted by user using review Id",tags = { "User review services" })
public class UserReviewController {

    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    UserPartnerReviewService userPartnerReviewService;

    @Autowired
    CommonUtility commonUtility;

    @ApiOperation(value = "Save user review mapping details", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(path = {"/oauth2/user/saveReview"}, method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean saveReview(@Valid @RequestBody UserPartnerReviewDTO userPartnerReviewDTO) throws Exception {
        return userPartnerReviewService.saveReview(userPartnerReviewDTO);
    }

    @ApiOperation(value = "Fetch user review list for partner id and status, pagination is applied for this api", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(path = {"/api/user/fetchReviewsByUser","/api/partner/fetchReviewsByUser"}, method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<UserPartnerReviewDTO> fetchReviewsByUser(@Valid @RequestBody UserPartnerReviewDTO userPartnerReviewDTO) throws Exception {
        return userPartnerReviewService.fetchReviewsByUser(userPartnerReviewDTO);
    }

    @ApiOperation(value = "Approve review posted by user using review Id", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(path = {"/api/user/approveRejectReview"}, method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean approveRejectReview(@Valid @RequestBody UserPartnerReviewDTO userPartnerReviewDTO) throws Exception {
        return userPartnerReviewService.updateReviewStatus(userPartnerReviewDTO);
    }

}

package com.rahul.project.gateway.service;

import com.google.api.client.repackaged.org.apache.commons.codec.binary.Base64;
import com.rahul.project.gateway.configuration.BusinessException;
import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.dao.AbstractDao;
import com.rahul.project.gateway.dao.UserDao;
import com.rahul.project.gateway.dto.*;
import com.rahul.project.gateway.dto.services.OauthResponse;
import com.rahul.project.gateway.model.Country;
import com.rahul.project.gateway.model.*;
import com.rahul.project.gateway.repository.AuthorityRepository;
import com.rahul.project.gateway.repository.UserLoginPasswordRepository;
import com.rahul.project.gateway.repository.UserLoginTypeRepository;
import com.rahul.project.gateway.repository.UserRepository;
import com.rahul.project.gateway.service.api.ApiServiceFactory;
import com.rahul.project.gateway.service.api.GatewayApi;
import com.rahul.project.gateway.utility.AESPasswordUtil;
import com.rahul.project.gateway.utility.CommonUtility;
import com.rahul.project.gateway.utility.Translator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;
import retrofit2.Call;
import retrofit2.Response;

import java.lang.invoke.MethodHandles;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Rahul Malhotra
 * @since 1.0
 * Date 2019-05-21
 */
@TransactionalService
public class SignUpService {

    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    @Autowired
    AbstractDao abstractDao;
    @Autowired
    Translator translator;
    @Autowired
    ApiServiceFactory apiServiceFactory;

    @Autowired
    CommonUtility commonUtility;

    @Autowired
    AESPasswordUtil passwordUtil;
    @Autowired
    Environment environment;
    @Autowired
    OTPService otpService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserLoginTypeRepository userLoginTypeRepository;
    @Autowired
    UserLoginPasswordRepository userLoginPasswordRepository;
    @Autowired
    AuthorityRepository authorityRepository;
    @Autowired
    private UserDao userDao;
    @Autowired
    SendMailService sendMailService;

    public SignUpResponseDTO signUpCustomerStageOne(SignUpCustomerStage1Dto signUpCustomerDto) throws Exception {
        logger.info("in customer sign up stage 1");
        SignUpResponseDTO signUpResponseDTO = new SignUpResponseDTO();
        User user = new User();
        if (signUpCustomerDto.getGenderId() != null) {
            Gender gender = abstractDao.getEntityById(Gender.class, signUpCustomerDto.getGenderId());
            if (gender == null)
                throw new BusinessException(translator.toLocale("gender.id.invalid"));
            else
                user.setGender(gender);
        } else
            throw new BusinessException(translator.toLocale("gender.id.required"));

        user.setFirstName(signUpCustomerDto.getFirstName());
        user.setFirstName(user.getFirstName().substring(0, 1).toUpperCase() + user.getFirstName().substring(1).toLowerCase());
        user.setLastName(signUpCustomerDto.getLastName());
        user.setLastName(user.getLastName().substring(0, 1).toUpperCase() + user.getLastName().substring(1).toLowerCase());
        user.setFullName(user.getFirstName() + " " + user.getLastName());
        if (signUpCustomerDto.getMobile() != null) {
            List<User> byMobile = userRepository.getByMobile(signUpCustomerDto.getMobile());
            if (byMobile != null && byMobile.size() > 0)
                throw new BusinessException(translator.toLocale("sign.up.mobile.exist"));
        }
        user.setRandomKey(commonUtility.generateUserRandomKey());
        if (signUpCustomerDto.getEmail() != null) {
            user.setEmail(signUpCustomerDto.getEmail());
            //sendMailService.sendEmailConfirmation();
//            sendMailService.sendOTPAdmin(user, otp, commonUtility.getDateConverted(expiryTime), userName);
        }
        user.setTitle(new Title(signUpCustomerDto.getTitleId()));

        // login type self
        String loginType = "self";
        /*if (signUpCustomerDto.getSignUpBy() != null) {
            if ("gmail".equalsIgnoreCase(signUpCustomerDto.getSignUpBy()))
                loginType = "gmail";
            else if ("facebook".equalsIgnoreCase(signUpCustomerDto.getSignUpBy()))
                loginType = "facebook";
        }*/
        UserLoginType loginTypeEntity = userLoginTypeRepository.getByLoginType(loginType);
        UserLoginPassword userLoginPassword;
        userLoginPassword = new UserLoginPassword();

        userLoginPassword.setPassword(passwordUtil.convertToAES(signUpCustomerDto.getPassword().trim()));
//        user.setActivated(true);
        Set<Authority> authorities = user.getAuthorities();
        if (authorities == null)
            authorities = new HashSet<>();
        //authority
        String authority = "ROLE_ADMIN";
        if (signUpCustomerDto.getUserType() != null) {
            if ("customer".equalsIgnoreCase(signUpCustomerDto.getUserType()))
                authority = "ROLE_CUSTOMER";
            else if ("partner".equalsIgnoreCase(signUpCustomerDto.getUserType()))
                authority = "ROLE_PARTNER";
        }
        authorities.add(authorityRepository.getByName(authority));
        user.setAuthorities(authorities);

        if (signUpCustomerDto.getMobile() != null) {
            user.setMobile(signUpCustomerDto.getMobile());
            Country country = verifyCountryId(user, signUpCustomerDto.getCountryId());
            user.setCountry(country);

            OtpDTO otpDTO = new OtpDTO();
            otpDTO.setMobileNo(signUpCustomerDto.getMobile());
            otpDTO.setCountryId(signUpCustomerDto.getCountryId());
            otpDTO.setUserName(signUpCustomerDto.getFirstName());
//        otpDTO.setTime(commonUtility.getDateByTimeZone(signUpCustomerDto.getTimeZone()));
            otpDTO.setTimeZone(signUpCustomerDto.getTimeZone());

            signUpResponseDTO.setResponseMessage(otpService.sendOtp(otpDTO, new ResponseDTO()).getResponseMessage());
        }
        user.setSignUpRandomKey(commonUtility.get20DigitRandomKey());
        user.setActivated(false);
        userRepository.save(user);

        if (!user.getEmail().isEmpty()) {
            sendMailService.sendEmailConfirmation(user);
        }
        CompositeId compositeId = new CompositeId(loginTypeEntity.getId(), user.getId());
        userLoginPassword.setCompositeId(compositeId);
        userLoginPassword.setLoginType(loginTypeEntity);
        userLoginPassword.setUser(user);
        userLoginPasswordRepository.save(userLoginPassword);


        signUpResponseDTO.setRandomKey(user.getSignUpRandomKey());
        return signUpResponseDTO;
    }

    public User signUpCustomerStageTwo(SignUpCustomerStage2Dto signUpCustomerDto) throws Exception {
        logger.info("in user sign up stage 2");
        SignUpResponseDTO signUpResponseDTO = new SignUpResponseDTO();
        User user = userRepository.getBySignUpRandomKey(signUpCustomerDto.getRandomKey());
        if (user == null)
            throw new BusinessException(translator.toLocale("user.not.found", new String[]{commonUtility.maskMobile(signUpCustomerDto.getRandomKey())}));
        VerifyOtpDTO verifyOtpDTO = new VerifyOtpDTO();
        verifyOtpDTO.setCountryId(user.getCountry().getId());
        verifyOtpDTO.setOtp(signUpCustomerDto.getOtp());
        verifyOtpDTO.setRandomKey(signUpCustomerDto.getRandomKey());
//        verifyOtpDTO.setTime(commonUtility.getDateByTimeZone(signUpCustomerDto.getTimeZone()));
        verifyOtpDTO.setTimeZone(signUpCustomerDto.getTimeZone());
        verifyOtpDTO.setMobileNo(user.getMobile());

        signUpResponseDTO.setResponseMessage(otpService.verifyOtp(verifyOtpDTO, new ResponseDTO()).getResponseMessage());
//        user.setRandomKey(commonUtility.generateUserRandomKey());
        user.setIsMobileVerified(true);
        user.setActivated(true);
        userRepository.save(user);
        signUpResponseDTO.setRandomKey(user.getRandomKey());

        return user;
    }

    private Country verifyCountryId(User user, Long contryId) throws BusinessException {
        Country country;
        if (contryId != null) {
            country = abstractDao.getEntityById(Country.class, contryId);
            if (country == null)
                throw new BusinessException(translator.toLocale("country.code.invalid"));
            else
                user.setCountry(country);
        } else {
            throw new BusinessException(translator.toLocale("country.code.required"));
        }
        return country;
    }

    public User signUp(User user, SignUpDto signUpDto) throws Exception {

        if (user.getEmail() != null && !"".equalsIgnoreCase(user.getEmail())) {
            if (userDao.findByUserName(user.getEmail()) != null) {
                throw new BusinessException(translator.toLocale("sign.up.email.exist"));
            }
        }

        if (!StringUtils.isEmpty(user.getUserName())) {
            if (userDao.findByUserName(user.getUserName()) != null) {
                throw new BusinessException(translator.toLocale("sign.up.user.name.exist"));
            }
        }
        if (user.getMobile() != null && !"".equalsIgnoreCase(user.getMobile())) {
            if (userDao.findByUserName(user.getMobile()) != null) {
                throw new BusinessException(translator.toLocale("sign.up.mobile.exist"));
            }
        }
        if (user.getIsEmailVerified()) {
            Country country = abstractDao.getEntityById(Country.class, signUpDto.getCountryId());
            if (country == null)
                throw new BusinessException(translator.toLocale("country.code.invalid"));
            user.setCountry(country);
        }
        if (signUpDto.getGenderId() != null) {
            Gender gender = abstractDao.getEntityById(Gender.class, signUpDto.getGenderId());
            if (gender == null)
                throw new BusinessException(translator.toLocale("gender.id.invalid"));
            user.setGender(gender);
        }
        UserLoginType userLoginType = abstractDao.getEntityById(UserLoginType.class, signUpDto.getUserLoginType());
        if (userLoginType == null)
            throw new BusinessException(translator.toLocale("user.login.type.invalid"));
//        user.setUserLoginType(userLoginType);

//        logger.info("is social sign up " + user.isSocialSignUp());
//        if (!user.isSocialSignUp()) {
//            if (!(user.isMobileVerified())) {
//                throw new BusinessException(translator.toLocale("sign.up.anyone.verified"));
//            }
//        }

        user.setActivated(true);
//        user.setPassword(passwordUtil.generateStorngPasswordHash(user.getPassword()));
        if (user.getUserName() == null)
            user.setUserName(String.valueOf(commonUtility.createOtp()));
        abstractDao.persist(user);

        for (AuthorityDTO authority : signUpDto.getAuthorities()) {
            UserAuthority userAuthority = new UserAuthority();
            UserAuthorityId userAuthorityId = new UserAuthorityId();
            userAuthorityId.setUser(user);
            Authority authority1 = new Authority();
            authority1.setAuthorityId(authority.getAuthorityId());
            userAuthorityId.setAuthority(authority1);
            userAuthority.setUserAuthorityId(userAuthorityId);
            abstractDao.persist(userAuthority);
        }
        return user;
    }

    public OauthResponse loginAfterSignUp(String userName, String password) throws Exception {
        String auth = environment.getRequiredProperty("spring.security.oauth2.client.registration.my-client.client-id")
                + ":" + environment.getRequiredProperty("spring.security.oauth2.client.registration.my-client.client-secret");
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
        String authHeaderValue = "Basic " + new String(encodedAuth);

        Call<OauthResponse> oauthResponseCall = apiServiceFactory.getRetrofitGateway().create(GatewayApi.class)
                .authenticate(authHeaderValue, userName, password, "password", password);
        Response<OauthResponse> oauthResponseResponse = oauthResponseCall.execute();
        if (oauthResponseResponse.isSuccessful()) {
            return oauthResponseResponse.body();
        } else {
            throw new BusinessException(translator.toLocale("internal.error"));
        }
    }

    public ResponseDTO checkIdentifier(CheckIdentifierDTO checkIdentifierDTO, ResponseDTO responseDTO) throws Exception {
        //authority
        String authority = null;
        if (checkIdentifierDTO.getUserType() != null) {
            if ("customer".equalsIgnoreCase(checkIdentifierDTO.getUserType()))
                authority = "ROLE_CUSTOMER";
            else if ("partner".equalsIgnoreCase(checkIdentifierDTO.getUserType()))
                authority = "ROLE_PARTNER";
            else if ("admin".equalsIgnoreCase(checkIdentifierDTO.getUserType()))
                authority = "ROLE_ADMIN";
        }

        User user = userDao.findByUserNameCountryId(checkIdentifierDTO.getIdentifier(), authority, checkIdentifierDTO.getCountryId());
        if (user == null) {
            responseDTO.setResponseMessage(translator.toLocale("user.not.found", new String[]{checkIdentifierDTO.getIdentifier()}));
        } else {
            user.setSignUpRandomKey(commonUtility.generateUserRandomKey());
            responseDTO.setRandomKey(user.getSignUpRandomKey());
            responseDTO.setActivated(user.getActivated());
            responseDTO.setResponseMessage(translator.toLocale("user.found", new String[]{checkIdentifierDTO.getIdentifier()}));
            abstractDao.saveOrUpdateEntity(user);
//            throw new BusinessException(translator.toLocale("user.found", new String[]{checkIdentifierDTO.getIdentifier()}));
        }
        return commonUtility.setResponseDTO(responseDTO);
    }


}

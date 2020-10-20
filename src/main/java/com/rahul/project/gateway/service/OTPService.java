package com.rahul.project.gateway.service;

import com.rahul.project.gateway.configuration.BusinessException;
import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.dao.AbstractDao;
import com.rahul.project.gateway.dao.CommonDao;
import com.rahul.project.gateway.dao.UserDao;
import com.rahul.project.gateway.dto.OtpDTO;
import com.rahul.project.gateway.dto.ReSendOtpDTO;
import com.rahul.project.gateway.dto.ResponseDTO;
import com.rahul.project.gateway.dto.VerifyOtpDTO;
import com.rahul.project.gateway.model.Country;
import com.rahul.project.gateway.model.OtpM;
import com.rahul.project.gateway.model.User;
import com.rahul.project.gateway.repository.UserRepository;
import com.rahul.project.gateway.utility.CommonUtility;
import com.rahul.project.gateway.utility.Translator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.invoke.MethodHandles;
import java.util.Calendar;
import java.util.List;

/**
 * @author Rahul Malhotra
 * @since 1.0
 * Date 2019-05-21
 */
@TransactionalService
public class OTPService {

    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final AbstractDao abstractDao;

    private final Translator translator;

    private final CommonUtility commonUtility;

    private final SmsService smsService;

    private final CommonDao commonDao;
    @Autowired
    UserDao userDao;
    @Autowired
    SendMailService sendMailService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    public OTPService(AbstractDao abstractDao, Translator translator, CommonUtility commonUtility, SmsService smsService, CommonDao commonDao) {
        this.abstractDao = abstractDao;
        this.translator = translator;
        this.commonUtility = commonUtility;
        this.smsService = smsService;
        this.commonDao = commonDao;
    }

    public ResponseDTO reSendOtp(ReSendOtpDTO reSendOtpDTO, ResponseDTO responseDTO) throws Exception {
        User user = userRepository.getBySignUpRandomKey(reSendOtpDTO.getRandomKey());
        if (user == null)
            throw new BusinessException(translator.toLocale("user.not.found", new String[]{reSendOtpDTO.getRandomKey()}));
        OtpDTO otpDTO = new OtpDTO();
        if (user.getCountry() == null)
            throw new BusinessException("country.code.required");
        otpDTO.setCountryId(user.getCountry().getId());
        otpDTO.setMobileNo(user.getMobile());
        otpDTO.setUserName(user.getFirstName());
        otpDTO.setTime(commonUtility.getDateByTimeZone(reSendOtpDTO.getTimeZone()));
        otpDTO.setTimeZone(reSendOtpDTO.getTimeZone());
        return sendOtp(otpDTO, responseDTO);
    }

    public ResponseDTO sendOtp(OtpDTO otpDTO, ResponseDTO responseDTO) throws Exception {
        User user;
        if (otpDTO.getRandomKey() != null) {
            user = userRepository.getBySignUpRandomKey(otpDTO.getRandomKey());
            if (user == null)
                throw new BusinessException("user.not.found");
        } else
            user = userDao.findByUserName(otpDTO.getIdentifier(), otpDTO.getUserType());
        String otp = String.valueOf(commonUtility.createOtp());
        Calendar generationTime = commonUtility.getCalendarConverted(commonUtility.getDateByTimeZone(otpDTO.getTimeZone()));
        Calendar expiryTime = commonUtility.getCalendarConverted(commonUtility.getDateByTimeZone(otpDTO.getTimeZone()));
        expiryTime.add(Calendar.MINUTE, 10);
        String userName;
        if (user != null) {
            otpDTO.setCountryId(user.getCountry().getId());
            otpDTO.setMobileNo(user.getMobile());
            responseDTO.setRandomKey(user.getSignUpRandomKey());
            userName = user.getFirstName();
        } else {
            userName = otpDTO.getUserName();
        }
        Country country = abstractDao.getEntityById(Country.class, otpDTO.getCountryId());

        String mobileNumber = country.getCode() + otpDTO.getMobileNo();

        OtpM otpM;
        List<OtpM> previousOtpM = commonDao.getOtpEntries(mobileNumber, generationTime);
        if (previousOtpM != null && previousOtpM.size() > 0) {
            otpM = previousOtpM.get(0);
            otpM.setExpirationTime(expiryTime);
            otp = otpM.getOtp();
        } else {
            otpM = new OtpM();
            otpM.setMobileNumber(mobileNumber);
            otpM.setStatus("OG");
            otpM.setGenerationTime(generationTime);
            otpM.setExpirationTime(expiryTime);
            otpM.setOtp(otp);
        }
        otpM.setMobile(otpDTO.getMobileNo());
        otpM.setCountry(country);
        abstractDao.persist(otpM);

        if (user != null && user.getIsEmailVerified() != null && user.getIsEmailVerified())
            sendMailService.sendOTPAdmin(user, otp, commonUtility.getDateConverted(expiryTime), userName);

        String message = translator.toLocale
                ("sms.otp", new Object[]{userName, otp, commonUtility.getDateConverted(expiryTime)});
        responseDTO.setResponseMessage(smsService.sendSMS(message, mobileNumber));
        return responseDTO;
    }

    public ResponseDTO verifyOtp(VerifyOtpDTO verifyOtpDTO, ResponseDTO responseDTO) throws Exception {
        User user;
        if (verifyOtpDTO.getRandomKey() != null)
            user = userRepository.getBySignUpRandomKey(verifyOtpDTO.getRandomKey());
        else
            user = userDao.findByUserName(verifyOtpDTO.getIdentifier(), verifyOtpDTO.getUserType());
        if (user != null) {
            verifyOtpDTO.setCountryId(user.getCountry().getId());
            verifyOtpDTO.setMobileNo(user.getMobile());
        } else
            throw new BusinessException("user.not.found.info");
        Country country = abstractDao.getEntityById(Country.class, verifyOtpDTO.getCountryId());
        String mobileNumber = country.getCode() + verifyOtpDTO.getMobileNo();
        List<OtpM> otpMS = commonDao.getOtpEntries(mobileNumber,
                commonUtility.getCalendarConverted(commonUtility.getDateByTimeZone(verifyOtpDTO.getTimeZone())));
        if (otpMS != null && otpMS.size() > 0) {
            if (verifyOtpDTO.getOtp().equalsIgnoreCase(otpMS.get(0).getOtp())) {
                otpMS.get(0).setStatus("OV");
                abstractDao.persist(otpMS.get(0));
//                user.setRandomKey(commonUtility.generateUserRandomKey());
                userRepository.save(user);
                responseDTO.setRandomKey(user.getRandomKey());
                responseDTO.setResponseMessage(translator.toLocale("otp.correct"));
            } else {
                throw new BusinessException(translator.toLocale("otp.incorrect"));
            }
        } else {
            throw new BusinessException(translator.toLocale("otp.regenerate"));
        }
        return responseDTO;
    }
}

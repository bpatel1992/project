package com.rahul.project.gateway.utility;

import com.rahul.project.gateway.configuration.BusinessException;
import com.rahul.project.gateway.configuration.annotations.SystemComponent;
import com.rahul.project.gateway.dao.AbstractDao;
import com.rahul.project.gateway.dto.ResponseDTO;
import com.rahul.project.gateway.model.Title;
import com.rahul.project.gateway.model.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.invoke.MethodHandles;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author Rahul Malhotra
 * @since 1.0
 * Date 2019-05-21
 */
@SystemComponent
@Transactional
public class CommonUtility {

    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");
    private final SimpleDateFormat dateFormatReport = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
    private final SimpleDateFormat dateFormatDay = new SimpleDateFormat("yyyy-MM-dd");
    private final SimpleDateFormat oneTimeFormat = new SimpleDateFormat("hh:mm:ss");
    private final SimpleDateFormat onlyDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final SimpleDateFormat dateFormatUnique = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    private final SimpleDateFormat onlyTimeAMPMFormat = new SimpleDateFormat("hh:mm a");

    @Autowired
    AbstractDao abstractDao;
    @Autowired
    Environment environment;

    String DATE_TIME_FORMAT_IST = "yyyy-MM-dd HH-mm-ss";

    public static void main(String[] args) throws Exception {
        CommonUtility commonUtility = new CommonUtility();
        System.out.println(new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a").format(commonUtility.getDateByTimeZoneDate("Africa/Lusaka")));
    }

    public String getEnvironmentProperty(String key) {
        return environment.getRequiredProperty(key);
    }

    public Date getDateConverted(String date) throws ParseException {
//        dateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
        return dateFormat.parse(date);
    }

    public Date getDateConvertedDay(String date) throws ParseException {
        return dateFormatDay.parse(date);
    }

    public Date getOnlyTime(String date) {
        try {
            return oneTimeFormat.parse(date);
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return null;
    }

    public Date getOnlyTimeAMPM(String date) {
        try {
            return onlyTimeAMPMFormat.parse(date);
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return null;
    }

    public String convertOnlyTimeIntoString(Date date) {
        try {
            return oneTimeFormat.format(date);
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return null;
    }

//    /**
//     * Converter for converting a string CodeValue on the DTO into a Code object with type SIZE_UNIT
//     */
//
//    public Converter<String, Date> onlyDateConverter = new Converter<String, Date>() {
//        public Date convert(MappingContext<String, Date> context) {
//            return getOnlyDate(context.getSource());
//        }
//    };
//    public Converter<String, Date> onlyTimeConverter = new Converter<String, Date>() {
//        public Date convert(MappingContext<String, Date> context) {
//            return getOnlyTime(context.getSource());
//        }
//    };
//    public Converter<Date, String> convertOnlyDateIntoString = new Converter<Date, String>() {
//        public String convert(MappingContext<Date,String> context) {
//            return convertOnlyDateIntoString(context.getSource());
//        }
//    };
//    public Converter<Date, String> convertOnlyTimeIntoString = new Converter<Date, String>() {
//        public String convert(MappingContext<Date, String> context) {
//            return convertOnlyTimeIntoString(context.getSource());
//        }
//    };
//

    public Date getOnlyDate(String date) {
        try {
            return onlyDateFormat.parse(date);
        } catch (ParseException e) {
            logger.error(String.valueOf(e));
        }
        return null;
    }

    public String convertOnlyDateIntoString(Date date) {
        return onlyDateFormat.format(date);
    }

    public Long getLoggedInUser() {
        Long aLong = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            if (!("admin".equalsIgnoreCase(currentUserName) || "rahul".equalsIgnoreCase(currentUserName)))
                return Long.parseLong(currentUserName);
        }
        return aLong;
    }

    public Calendar getCalendarConverted(String date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateFormat.parse(date));
        return calendar;
    }

    public String getDateConverted() {
        Calendar calendar = Calendar.getInstance();
        return dateFormat.format(calendar.getTime());
    }

    public String getDateFormatReport(Date date) {
        if (date != null)
            return dateFormatReport.format(date);
        else
            return null;
    }

    public String getDateByTimeZone(String timeZone) {
        Instant nowUtc = Instant.now();
        ZoneId zoneId = ZoneId.of(timeZone);
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(nowUtc, zoneId);
        return zonedDateTime.format(formatter);
    }

    public Date getDateByTimeZoneDate(String timeZone) throws ParseException {
        Instant nowUtc = Instant.now();
        ZoneId zoneId = ZoneId.of(timeZone);
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(nowUtc, zoneId);
        return dateFormat.parse(zonedDateTime.format(formatter));
    }

    public String getDateConverted(Calendar time) {
        return dateFormat.format(time.getTime());
    }

    public String maskOTP(String otp) {
        return otp.replaceAll(".(?=.{2})", "*");
    }

    public String maskMobile(String otp) {
        return otp.replaceAll(".(?=.{3})", "*");
    }

    public int createOtp() {
        return (new Random()).nextInt(900000) + 100000;
    }

    public String createFile(MultipartFile multipartFile, String path) throws Exception {
        String fileName;
        File outFile;
        if (multipartFile != null) {
            fileName = multipartFile.getOriginalFilename();
            logger.info("fileName=======" + fileName);
            outFile = new File(path);
            boolean mkdirs = outFile.getParentFile().mkdirs();
            boolean newFile = outFile.createNewFile();
            logger.info("directory created {}, file created {}", mkdirs, newFile);
            byte[] bytes = multipartFile.getBytes();
            BufferedOutputStream buffStream = new BufferedOutputStream(new FileOutputStream(outFile));
            buffStream.write(bytes);
            buffStream.close();
        } else {
            throw new BusinessException("file.not.found");
        }
        return outFile.getAbsolutePath();
    }

    public String get16DigitRandomKey() {

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmm");
        String transactionID = "" + (int) (9 * Math.random()) + (int) (9 * Math.random()) + (int) (9 * Math.random()) + (int) (9 * Math.random()) + (int) (9 * Math.random()) + (int) (9 * Math.random());

        transactionID = sdf.format(date) + transactionID;
        return transactionID;
    }

    public String get20DigitRandomKey() {
        Date date = new Date();
        String transactionID = "" + (int) (9 * Math.random()) + (int) (9 * Math.random()) + (int) (9 * Math.random());
        transactionID = dateFormatUnique.format(date) + transactionID;
        return transactionID;
    }

    public String generatePetRandomKey() {
        String transactionID = "" + (int) (9 * Math.random()) + (int) (9 * Math.random()) + (int) (9 * Math.random()) + (int) (9 * Math.random()) + (int) (9 * Math.random()) + (int) (9 * Math.random());
        transactionID = System.currentTimeMillis() + transactionID;
        return transactionID;
    }

    public String getTitle(Title title) {
        return title != null ? ((title.getLabel() != null)
                ? title.getLabel() + " " : title.getTitle() + " ") : "";

    }

    public String getName(User user) {
        return user != null ? (user.getFirstName() != null ? user.getFirstName() + " " : "") +
                (user.getLastName() != null ? user.getLastName() : "") : "";

    }

    public String getCompleteName(User user) {
        return getTitle(user.getTitle()) + getName(user);

    }

    public String getUserImageURL(User user) {
        return user.getImageName() != null ?
                environment.getRequiredProperty("gateway.api.url") + "assets/user/profile?randomKey=" + user.getRandomKey() : null;
    }

    public String getUserAvatarURL(User user) {
        return user.getAvatarName() != null ?
                environment.getRequiredProperty("gateway.api.url") +
                        "assets/user/avatar?randomKey=" + user.getRandomKey() : null;
    }

    public String getUserCoverURL(User user) {
        return user.getCoverName() != null ?
                environment.getRequiredProperty("gateway.api.url") + "assets/user/cover?randomKey=" + user.getRandomKey() : null;
    }

    public String getUserProfession(User user) {
        return user.getProfession() != null ? (user.getProfession().getLabel() != null
                ? user.getProfession().getLabel() : user.getProfession().getProfession()) : "";
    }

    public String generateUserRandomKey() {
        return generateRandomKey(10) + System.currentTimeMillis();
    }

    public ResponseDTO setResponseDTO(ResponseDTO responseDTO) {
        if (responseDTO.getResponseMessage().contains(":")) {
            responseDTO.setResponseCode(responseDTO.getResponseMessage().substring(0, responseDTO.getResponseMessage().indexOf(":")));
            responseDTO.setResponseMessage(responseDTO.getResponseMessage().substring(responseDTO.getResponseMessage().indexOf(":") + 1));
        }
        return responseDTO;
    }

    public String generateRandomKey(int count) {
        StringBuilder builder = new StringBuilder();
        final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        while (count-- != 0) {
            int character = (int) (Math.random() * ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        String randomkey = builder.toString();
        boolean flag = true;
        while (flag) {
            flag = checkDuplicateTenDigitRandom(randomkey);
            logger.info(" Random key found in database:::::::::" + flag);
            if (flag) {
                generateRandomKey(count);
            }
        }
        return randomkey;
    }

    private boolean checkDuplicateTenDigitRandom(String random) {
        Boolean flag = false;
        Session session = abstractDao.getSession();
        try {
            Criteria criteria = session.createCriteria(User.class);
            criteria.add(Restrictions.eq("randomKey", random));
            criteria.setProjection(Projections.rowCount());
            int count = ((Long) criteria.uniqueResult()).intValue();
            if (count != 0) {
                logger.info("present");
                flag = true;
            } else {
                logger.info("absent");
                flag = false;
            }
        } catch (Exception exception) {
            logger.error("Exception : ", exception);
        }
        return flag;

    }

    public Date getCurrentDate(String timezone) {
        Date currentDate = new Date();
        TimeZone tz = TimeZone.getTimeZone(timezone);
        Calendar mbCal = new GregorianCalendar(tz);
        mbCal.setTimeInMillis(currentDate.getTime());
        Calendar cal = Calendar.getInstance(tz);
        cal.set(Calendar.YEAR, mbCal.get(Calendar.YEAR));
        cal.set(Calendar.MONTH, mbCal.get(Calendar.MONTH));
        cal.set(Calendar.DAY_OF_MONTH, mbCal.get(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, mbCal.get(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, mbCal.get(Calendar.MINUTE));
        cal.set(Calendar.SECOND, mbCal.get(Calendar.SECOND));
        cal.set(Calendar.MILLISECOND, mbCal.get(Calendar.MILLISECOND));
        return cal.getTime();
    }

    public String getTransactionID() {

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String transactionID = "" + (int) (9 * Math.random()) + (int) (9 * Math.random()) + (int) (9 * Math.random()) + (int) (9 * Math.random()) + (int) (9 * Math.random()) + (int) (9 * Math.random());
        logger.info("Generated transaction ID -----------> " + transactionID);
        transactionID = sdf.format(date) + transactionID;
        return transactionID;
    }

    public String getDateString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_TIME_FORMAT_IST);
        return simpleDateFormat.format(date);
    }
}

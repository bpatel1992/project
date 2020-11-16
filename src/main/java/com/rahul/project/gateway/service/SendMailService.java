package com.rahul.project.gateway.service;

import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.dao.AbstractDao;
import com.rahul.project.gateway.dto.CreateAppointmentDto;
import com.rahul.project.gateway.model.PartnerAddress;
import com.rahul.project.gateway.model.User;
import com.rahul.project.gateway.utility.CommonUtility;
import com.rahul.project.gateway.utility.Translator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;
import java.lang.invoke.MethodHandles;


/**
 * Method to send mail to user if he/she is successfully registered
 *
 * @author Rahul Malhotra
 * date 2020-06-21
 */
@SuppressWarnings("unused")
@TransactionalService
public class SendMailService {

    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final Environment environment;

    private final JavaMailSender javaMailSender;

    private final Translator translator;

    private final CommonUtility commonUtil;


    private final AbstractDao abstractDao;

    @Autowired
    public SendMailService(Environment environment, JavaMailSender javaMailSender, AbstractDao abstractDao,
                           Translator translator, CommonUtility commonUtil) {
        this.environment = environment;
        this.javaMailSender = javaMailSender;
        this.abstractDao = abstractDao;
        this.translator = translator;
        this.commonUtil = commonUtil;

    }

    public void sendOTPAdmin(User user, String otp, String expiryDate, String userName) throws Exception {

        String emailConfirmUrl = null;
        String logoURL = environment.getRequiredProperty("email.application.logo");
        String webSiteURL = environment.getRequiredProperty("application.url");
        MimeMessage msg = javaMailSender.createMimeMessage();
        // true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
        helper.setTo(user.getEmail());
        helper.setSubject(translator.toLocale("send.otp.admin.subject.forgot.password"));

        // true = text/html
        helper.setText("<table bgcolor='#ebeef0' cellpadding='0' cellspacing='0' align='center' border='0' width='420'>  " +
                "<tbody><tr height='29'> <td width='10'></td> <td width='400'></td> <td width='10'></td> </tr><tr> <td width='10'>" +
                "</td> <td width='400' style='background:grey;border-top-left-radius:10px;border-top-right-radius:10px'> " +
                "<table width='400' cellpadding='0' cellspacing='0' border='0'> <tbody><tr height='15'> <td width='20'></td> " +
                "<td width='360' colspan='2'></td> <td width='20'></td></tr>" +
                "<tr> <td width='20'></td> <td align='left' valign='middle' width='81' style='padding:0;margin:0 0 0 0'>" +
                "<a href='" + webSiteURL + "' style='display:block' target='_blank'>" +
                "<img src='" + logoURL + "' alt='HelloVet' style='width:81px' class='CToWUd'></a></td> " +
                "<td align='right' valign='middle' width='279' style='font-family:Helvetica Neue,Helvetica,Arial," +
                "sans-serif;color:#ffffff;font-size:18px;line-height:33px;font-weight:300;white-space:nowrap;padding:3px 0 0 0;" +
                "margin:0 0 0 0'> <span style='color:#ffffff;text-decoration:none;margin:0 0 0 0;padding:0 0 0 0'>" +
                translator.toLocale("send.otp.admin.label.forgot.password") + "</span> </td> <td width='20'></td> </tr>	" +
                "<tr height='20'> <td width='20'></td> <td width='360' colspan='2'></td> <td width='20'></td></tr></tbody></table>" +
                " </td> <td width='10'></td> </tr><tr> <td width='10'></td> <td width='400' " +
                "style='background:#ffffff;border-bottom-left-radius:10px;border-bottom-right-radius:10px'> " +
                "<table width='400' cellpadding='0' cellspacing='0' border='0'>  <tbody><tr height='35'> " +
                "<td width='20'></td> <td width='360' colspan='2'></td> <td width='20'></td> </tr> <tr> " +
                "<td width='20'></td> <td width='360' colspan='2'> <table bgcolor='#ffffff' " +
                "width='360' cellpadding='0' cellspacing='0' border='0'><tbody><tr><td " +
                "style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;color:#3b4143;font-size:13px;" +
                "font-weight:300;line-height:18px;padding:0 0 20px 0'>Dear " +
                userName + "," + "<br/>" + "<br/>" + "Thank you for choosing HelloVet." + "<br/>" + "<br/>" +
                "\n" +
                "We use email to send important communications related to your account and HelloVet\n" +
                "\n" +
                "Services." + "<br/>" + "<br/>" +
                "\n" +
                "OTP(i.e. One Time Password) is\n" + otp +
                "\n" +
                "It will get expired after\n" + expiryDate +
                "\n" +
                "Wishing you happy and convenient collecting with HelloVet." +
                "<tr><td style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;font-size:16px;font-weight:300;text-align:center;line-height:16px;padding:0 0 27px 0'></td></tr> <tr><td></td></tr><tr><td style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;color:#3b4143;font-size:13px;font-weight:300;line-height:18px;padding:0 0 20px 0'>Thanking you,<br><span class='il'>HelloVet</span> Team <br>\n" +
                "boltontechnologies.info@gmail.com </td></tr> </tbody></table></td> <td width='20'></td> </tr> " +
                "<tr height='30'><td width='20'></td> <td width='360' colspan='2'></td> <td width='20'></td> </tr></tbody></table> </td> <td width='10'></td> </tr><tr height='15'> <td width='10'></td> <td width='400'></td> <td width='10'></td> </tr><tr> <td width='10'></td> <td align='center' style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;color:#84939d;font-size:10px;line-height:10px;margin:0 0 0 0;padding:0 0 20px 0'>© <span class='il'>HelloVet </span> </td><td width='10'></td> </tr>" +
                "<tr height='29'> <td width='10'></td> <td width='400'></td> <td width='10'></td> </tr> </tbody></table>", true);

        javaMailSender.send(msg);

    }

    public void sendStaffAdminVerificationMail(User user, String userName) throws Exception {

        String emailConfirmUrl;
        String logoURL = environment.getRequiredProperty("email.application.logo");
        String webSiteURL = environment.getRequiredProperty("application.url");
        long timeInMillis = System.currentTimeMillis();
        user.setRandomKey(commonUtil.generateRandomKey(10) + timeInMillis + commonUtil.generateRandomKey(5));
        emailConfirmUrl = webSiteURL + "#/email-signup/" + user.getRandomKey();
        MimeMessage msg = javaMailSender.createMimeMessage();

        // true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);

        helper.setTo(user.getEmail());

        helper.setSubject(translator.toLocale("verify.email.subject"));


        // true = text/html
        helper.setText("<table bgcolor='#ebeef0' cellpadding='0' cellspacing='0' align='center' border='0' width='420'>  " +
                "<tbody><tr height='29'> <td width='10'></td> <td width='400'></td> <td width='10'></td> </tr><tr> <td width='10'></td> <td width='400' style='background:grey;border-top-left-radius:10px;border-top-right-radius:10px'> <table width='400' cellpadding='0' cellspacing='0' border='0'> <tbody><tr height='15'> <td width='20'></td> <td width='360' colspan='2'></td> <td width='20'></td></tr>" +
                "<tr> <td width='20'></td> <td align='left' valign='middle' width='81' style='padding:0;margin:0 0 0 0'><a href='" + webSiteURL + "' style='display:block' target='_blank'><img src='" + logoURL + "' alt='HelloVet' style='width:81px' class='CToWUd'></a></td> <td align='right' valign='middle' width='279' style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;color:#ffffff;font-size:18px;line-height:33px;font-weight:300;white-space:nowrap;padding:3px 0 0 0;margin:0 0 0 0'> <span style='color:#ffffff;text-decoration:none;margin:0 0 0 0;padding:0 0 0 0'>Confirmation</span> </td> <td width='20'></td> </tr>	" +
                "<tr height='20'> <td width='20'></td> <td width='360' colspan='2'></td> <td width='20'></td></tr></tbody></table> </td> <td width='10'></td> </tr><tr> <td width='10'></td> <td width='400' style='background:#ffffff;border-bottom-left-radius:10px;border-bottom-right-radius:10px'> <table width='400' cellpadding='0' cellspacing='0' border='0'>  <tbody><tr height='35'> <td width='20'></td> <td width='360' colspan='2'></td> <td width='20'></td> </tr> <tr> <td width='20'></td> <td width='360' colspan='2'> <table bgcolor='#ffffff' width='360' cellpadding='0' cellspacing='0' border='0'><tbody><tr><td style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;color:#3b4143;font-size:13px;font-weight:300;line-height:18px;padding:0 0 20px 0'>Dear " +
                userName + "," + "<br/>" + "<br/>" + "Thank you for choosing HelloVet." + "<br/>" + "<br/>" +
                "\n" +
                "We use email to send important communications related to your account and HelloVet\n" +
                "\n" +
                "Services." + "<br/>" + "<br/>" +
                "\n" +
                "We request you to please save your password by clicking on the button given below. In case\n" +
                "\n" +
                "there is a problem with the button please use the link given under the same." + "<br/>" + "<br/>" +
                "\n" +
                "Wishing you happy and convenient collecting with HelloVet." +
                "</td></tr>" + "<tr> " +
                "<td colspan='2' align='center' padding-top: 35px;'>" +
                "<a href='" + emailConfirmUrl + "'><button type='button' style='padding: 5px 40px;color: #FFF;background-color: #00a9f7;font-weight:bold;'>Save Password</button></a></td> </tr> " +
                "<tr> " +
                "<td colspan='2' align='center' style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;color:#3b4143;font-size:13px;font-weight:bold;padding-top:30px;'> Click on this link to Save</td> </tr> " +
                "<tr> " +
                "<td colspan='2' align='center' style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;color:#3b4143;font-size:13px;font-weight:300;padding-bottom:30px;'> " +
                "<a href='" + emailConfirmUrl + "'>" + emailConfirmUrl + "</a></td> </tr> " +

                "<tr><td style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;font-size:16px;font-weight:300;text-align:center;line-height:16px;padding:0 0 27px 0'></td></tr> <tr><td></td></tr><tr><td style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;color:#3b4143;font-size:13px;font-weight:300;line-height:18px;padding:0 0 20px 0'>Thanking you,<br><span class='il'>HelloVet</span> Team <br>\n" +
                "care.digipe@mann-india.com </td></tr> </tbody></table></td> <td width='20'></td> </tr> " +
                "<tr height='30'><td width='20'></td> <td width='360' colspan='2'></td> <td width='20'></td> </tr></tbody></table> </td> <td width='10'></td> </tr><tr height='15'> <td width='10'></td> <td width='400'></td> <td width='10'></td> </tr><tr> <td width='10'></td> <td align='center' style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;color:#84939d;font-size:10px;line-height:10px;margin:0 0 0 0;padding:0 0 20px 0'>© <span class='il'>HelloVet </span> </td><td width='10'></td> </tr>" +
                "<tr height='29'> <td width='10'></td> <td width='400'></td> <td width='10'></td> </tr> </tbody></table>", true);

        javaMailSender.send(msg);


    }


    public void sendClientConfirmation(User user, String userName) throws Exception {

        String accountActivationUrl;
        String logoURL = environment.getRequiredProperty("email.application.logo");
        String webSiteURL = environment.getRequiredProperty("application.url");
        //long timeInMillis = System.currentTimeMillis();
        // user.setRandomKey(commonUtil.generateRandomKey(10) + timeInMillis + commonUtil.generateRandomKey(5));
        accountActivationUrl = webSiteURL + "auth/sign-up";
        MimeMessage msg = javaMailSender.createMimeMessage();

        // true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);

        helper.setTo(user.getEmail());

        helper.setSubject(translator.toLocale("account.activation"));

        helper.setText("<table bgcolor='#ebeef0' cellpadding='0' cellspacing='0' align='center' border='0' width='420'>  " +
                "<tbody><tr height='29'> <td width='10'></td> <td width='400'></td> <td width='10'></td> </tr><tr> <td width='10'></td> <td width='400' style='background:grey;border-top-left-radius:10px;border-top-right-radius:10px'> <table width='400' cellpadding='0' cellspacing='0' border='0'> <tbody><tr height='15'> <td width='20'></td> <td width='360' colspan='2'></td> <td width='20'></td></tr>" +
                "<tr> <td width='20'></td> <td align='left' valign='middle' width='81' style='padding:0;margin:0 0 0 0'><a href='" + webSiteURL + "' style='display:block' target='_blank'><img src='" + logoURL + "' alt='PetShree' style='width:81px' class='CToWUd'></a></td> <td align='right' valign='middle' width='279' style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;color:#ffffff;font-size:18px;line-height:33px;font-weight:300;white-space:nowrap;padding:3px 0 0 0;margin:0 0 0 0'> <span style='color:#ffffff;text-decoration:none;margin:0 0 0 0;padding:0 0 0 0'>Confirmation</span> </td> <td width='20'></td> </tr>	" +
                "<tr height='20'> <td width='20'></td> <td width='360' colspan='2'></td> <td width='20'></td></tr></tbody></table> </td> <td width='10'></td> </tr><tr> <td width='10'></td> <td width='400' style='background:#ffffff;border-bottom-left-radius:10px;border-bottom-right-radius:10px'> <table width='400' cellpadding='0' cellspacing='0' border='0'>  <tbody><tr height='35'> <td width='20'></td> <td width='360' colspan='2'></td> <td width='20'></td> </tr> <tr> <td width='20'></td> <td width='360' colspan='2'> <table bgcolor='#ffffff' width='360' cellpadding='0' cellspacing='0' border='0'><tbody><tr><td style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;color:#3b4143;font-size:13px;font-weight:300;line-height:18px;padding:0 0 20px 0'>Dear " +
                userName + "," + "<br/>" + "<br/>" + "You are add By one of our PetShree Partner." + "<br/>" + "<br/>" +
                "\n" +
                "We request you to please Activate your Account by clicking on the button given below. In case\n" +
                "\n" +
                "there is a problem with the button please use the link given under the same." + "<br/>" + "<br/>" +
                "\n" +
                "Wishing you happy and convenient collecting with PetShree." +
                "</td></tr>" + "<tr> " +
                "<td colspan='2' align='center' padding-top: 35px;'>" +
                "<a href='" + accountActivationUrl + "'><button type='button' style='padding: 5px 40px;color: #FFF;background-color: #00a9f7;font-weight:bold;'>Activate Account</button></a></td> </tr> " +
                "<tr> " +
                "<td colspan='2' align='center' style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;color:#3b4143;font-size:13px;font-weight:bold;padding-top:30px;'> Click on this link to Activate Account</td> </tr> " +
                "<tr> " +
                "<td colspan='2' align='center' style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;color:#3b4143;font-size:13px;font-weight:300;padding-bottom:30px;'> " +
                "<a href='" + accountActivationUrl + "'>" + accountActivationUrl + "</a></td> </tr> " +

                "<tr><td style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;font-size:16px;font-weight:300;text-align:center;line-height:16px;padding:0 0 27px 0'></td></tr> <tr><td></td></tr><tr><td style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;color:#3b4143;font-size:13px;font-weight:300;line-height:18px;padding:0 0 20px 0'>Thanking you,<br><span class='il'>PetShree</span> Team <br>\n" +
                "</td></tr> </tbody></table></td> <td width='20'></td> </tr> " +
                "<tr height='30'><td width='20'></td> <td width='360' colspan='2'></td> <td width='20'></td> </tr></tbody></table> </td> <td width='10'></td> </tr><tr height='15'> <td width='10'></td> <td width='400'></td> <td width='10'></td> </tr><tr> <td width='10'></td> <td align='center' style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;color:#84939d;font-size:10px;line-height:10px;margin:0 0 0 0;padding:0 0 20px 0'>© <span class='il'>PetShree </span> </td><td width='10'></td> </tr>" +
                "<tr height='29'> <td width='10'></td> <td width='400'></td> <td width='10'></td> </tr> </tbody></table>", true);

        javaMailSender.send(msg);


    }

    public void sendAppointmentBooked(User customer, CreateAppointmentDto createAppointmentDto, PartnerAddress clinic, String petName, String attendantName) throws Exception {

        String accountActivationUrl;
        String logoURL = environment.getRequiredProperty("email.application.logo");
        String webSiteURL = environment.getRequiredProperty("application.url");
        //long timeInMillis = System.currentTimeMillis();
        // user.setRandomKey(commonUtil.generateRandomKey(10) + timeInMillis + commonUtil.generateRandomKey(5));
        accountActivationUrl = webSiteURL + "auth/sign-up";
        MimeMessage msg = javaMailSender.createMimeMessage();

        // true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);

        helper.setTo(customer.getEmail());

        helper.setSubject(translator.toLocale("appointment.booked"));

        helper.setText("<table bgcolor='#ebeef0' cellpadding='0' cellspacing='0' align='center' border='0' width='420'>  " +
                "<tbody><tr height='29'> <td width='10'></td> <td width='400'></td> <td width='10'></td> </tr><tr> <td width='10'></td> <td width='400' style='background:grey;border-top-left-radius:10px;border-top-right-radius:10px'> <table width='400' cellpadding='0' cellspacing='0' border='0'> <tbody><tr height='15'> <td width='20'></td> <td width='360' colspan='2'></td> <td width='20'></td></tr>" +
                "<tr> <td width='20'></td> <td align='left' valign='middle' width='81' style='padding:0;margin:0 0 0 0'><a href='" + webSiteURL + "' style='display:block' target='_blank'><img src='" + logoURL + "' alt='PetShree' style='width:81px' class='CToWUd'></a></td> <td align='right' valign='middle' width='279' style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;color:#ffffff;font-size:18px;line-height:33px;font-weight:300;white-space:nowrap;padding:3px 0 0 0;margin:0 0 0 0'> <span style='color:#ffffff;text-decoration:none;margin:0 0 0 0;padding:0 0 0 0'>Confirmation</span> </td> <td width='20'></td> </tr>	" +
                "<tr height='20'> <td width='20'></td> <td width='360' colspan='2'></td> <td width='20'></td></tr></tbody></table> </td> <td width='10'></td> </tr><tr> <td width='10'></td> <td width='400' style='background:#ffffff;border-bottom-left-radius:10px;border-bottom-right-radius:10px'> <table width='400' cellpadding='0' cellspacing='0' border='0'>  <tbody><tr height='35'> <td width='20'></td> <td width='360' colspan='2'></td> <td width='20'></td> </tr> <tr> <td width='20'></td> <td width='360' colspan='2'> <table bgcolor='#ffffff' width='360' cellpadding='0' cellspacing='0' border='0'><tbody><tr><td style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;color:#3b4143;font-size:13px;font-weight:300;line-height:18px;padding:0 0 20px 0'>Hii " +
                customer.getFirstName() + "," + "<br/>" + "<br/>" + "Your pet(" + petName + ") Appointment is Booked with Dr. " + attendantName + " " +
                " for " + createAppointmentDto.getToTime() + "-" + createAppointmentDto.getFromTime() + " on " + createAppointmentDto.getDate() + "." + "<br/>" + "<br/>" +
                "\n" +
                clinic.getName() + " Clinic Placed at" + clinic.getAddress() + "Kindly be on time. Please call us at 9876345678 for any assistance\n" +
                "<tr><td style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;font-size:16px;font-weight:300;text-align:center;line-height:16px;padding:0 0 27px 0'></td></tr> <tr><td></td></tr><tr><td style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;color:#3b4143;font-size:13px;font-weight:300;line-height:18px;padding:0 0 20px 0'>Thanking you,<br><span class='il'>PetShree</span> Team <br>\n" +
                "</td></tr> </tbody></table></td> <td width='20'></td> </tr> " +
                "<tr height='30'><td width='20'></td> <td width='360' colspan='2'></td> <td width='20'></td> </tr></tbody></table> </td> <td width='10'></td> </tr><tr height='15'> <td width='10'></td> <td width='400'></td> <td width='10'></td> </tr><tr> <td width='10'></td> <td align='center' style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;color:#84939d;font-size:10px;line-height:10px;margin:0 0 0 0;padding:0 0 20px 0'>© <span class='il'>PetShree </span> </td><td width='10'></td> </tr>" +
                "<tr height='29'> <td width='10'></td> <td width='400'></td> <td width='10'></td> </tr> </tbody></table>", true);

        javaMailSender.send(msg);


    }





    public void sendEmailConfirmation(User user) throws Exception {

        String emailActivationUrl;
        String logoURL = environment.getRequiredProperty("email.application.logo");
        String webSiteURL = environment.getRequiredProperty("application.url");
        //long timeInMillis = System.currentTimeMillis();
        // user.setRandomKey(commonUtil.generateRandomKey(10) + timeInMillis + commonUtil.generateRandomKey(5));
        emailActivationUrl = webSiteURL + "update/email-status/?id="+user.getId();
        MimeMessage msg = javaMailSender.createMimeMessage();

        // true = multipart message
        MimeMessageHelper helper = new MimeMessageHelper(msg, true);

        helper.setTo(user.getEmail());

        helper.setSubject(translator.toLocale("verify.email.subject"));

        helper.setText("<table bgcolor='#ebeef0' cellpadding='0' cellspacing='0' align='center' border='0' width='420'>  " +
                "<tbody><tr height='29'> <td width='10'></td> <td width='400'></td> <td width='10'></td> </tr><tr> <td width='10'></td> <td width='400' style='background:grey;border-top-left-radius:10px;border-top-right-radius:10px'> <table width='400' cellpadding='0' cellspacing='0' border='0'> <tbody><tr height='15'> <td width='20'></td> <td width='360' colspan='2'></td> <td width='20'></td></tr>" +
                "<tr> <td width='20'></td> <td align='left' valign='middle' width='81' style='padding:0;margin:0 0 0 0'><a href='" + webSiteURL + "' style='display:block' target='_blank'><img src='" + logoURL + "' alt='PetShree' style='width:81px' class='CToWUd'></a></td> <td align='right' valign='middle' width='279' style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;color:#ffffff;font-size:18px;line-height:33px;font-weight:300;white-space:nowrap;padding:3px 0 0 0;margin:0 0 0 0'> <span style='color:#ffffff;text-decoration:none;margin:0 0 0 0;padding:0 0 0 0'>Confirmation</span> </td> <td width='20'></td> </tr>	" +
                "<tr height='20'> <td width='20'></td> <td width='360' colspan='2'></td> <td width='20'></td></tr></tbody></table> </td> <td width='10'></td> </tr><tr> <td width='10'></td> <td width='400' style='background:#ffffff;border-bottom-left-radius:10px;border-bottom-right-radius:10px'> <table width='400' cellpadding='0' cellspacing='0' border='0'>  <tbody><tr height='35'> <td width='20'></td> <td width='360' colspan='2'></td> <td width='20'></td> </tr> <tr> <td width='20'></td> <td width='360' colspan='2'> <table bgcolor='#ffffff' width='360' cellpadding='0' cellspacing='0' border='0'><tbody><tr><td style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;color:#3b4143;font-size:13px;font-weight:300;line-height:18px;padding:0 0 20px 0'>Dear " +
                user.getFirstName() + "," + "<br/>" + "<br/>" + "Thank you for choosing PetShree." + "<br/>" + "<br/>" +
                "\n" +
                "We use email to send important communications related to your account and Petshree\n" +
                "\n" +
                "Services." + "<br/>" + "<br/>" +
                "\n" +
                "We request you to please verify your email id by clicking on the button given below. In case\n" +
                "\n" +
                "there is a problem with the button please use the link given under the same." + "<br/>" + "<br/>" +
                "\n" +
                "Wishing you happy and convenient collecting with PetShree." +
                "</td></tr>" + "<tr> " +
                "<td colspan='2' align='center' padding-top: 35px;'>" +
                "<a href='" + emailActivationUrl + "'><button type='button' style='padding: 5px 40px;color: #FFF;background-color: #00a9f7;font-weight:bold;'>Verify Email Id</button></a></td> </tr> " +
                "<tr> " +
                "<td colspan='2' align='center' style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;color:#3b4143;font-size:13px;font-weight:bold;padding-top:30px;'> Click on this link to Verify</td> </tr> " +
                "<tr> " +
                "<td colspan='2' align='center' style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;color:#3b4143;font-size:13px;font-weight:300;padding-bottom:30px;'> " +
                "<a href='" + emailActivationUrl + "'>" + emailActivationUrl + "</a></td> </tr> " +

                "<tr><td style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;font-size:16px;font-weight:300;text-align:center;line-height:16px;padding:0 0 27px 0'></td></tr> <tr><td></td></tr><tr><td style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;color:#3b4143;font-size:13px;font-weight:300;line-height:18px;padding:0 0 20px 0'>Thanking you,<br><span class='il'>PetShree</span> Team <br>\n" +
                "</td></tr> </tbody></table></td> <td width='20'></td> </tr> " +
                "<tr height='30'><td width='20'></td> <td width='360' colspan='2'></td> <td width='20'></td> </tr></tbody></table> </td> <td width='10'></td> </tr><tr height='15'> <td width='10'></td> <td width='400'></td> <td width='10'></td> </tr><tr> <td width='10'></td> <td align='center' style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;color:#84939d;font-size:10px;line-height:10px;margin:0 0 0 0;padding:0 0 20px 0'>© <span class='il'>PetShree </span> </td><td width='10'></td> </tr>" +
                "<tr height='29'> <td width='10'></td> <td width='400'></td> <td width='10'></td> </tr> </tbody></table>", true);

//        helper.setText("<table bgcolor='#ebeef0' cellpadding='0' cellspacing='0' align='center' border='0' width='420'>  " +
//                "<tbody><tr height='29'> <td width='10'></td> <td width='400'></td> <td width='10'></td> </tr><tr> <td width='10'></td> <td width='400' style='background:grey;border-top-left-radius:10px;border-top-right-radius:10px'> <table width='400' cellpadding='0' cellspacing='0' border='0'> <tbody><tr height='15'> <td width='20'></td> <td width='360' colspan='2'></td> <td width='20'></td></tr>" +
//                "<tr> <td width='20'></td> <td align='left' valign='middle' width='81' style='padding:0;margin:0 0 0 0'><a href='" + webSiteURL + "' style='display:block' target='_blank'><img src='" + logoURL + "' alt='PetShree' style='width:81px' class='CToWUd'></a></td> <td align='right' valign='middle' width='279' style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;color:#ffffff;font-size:18px;line-height:33px;font-weight:300;white-space:nowrap;padding:3px 0 0 0;margin:0 0 0 0'> <span style='color:#ffffff;text-decoration:none;margin:0 0 0 0;padding:0 0 0 0'>Confirmation</span> </td> <td width='20'></td> </tr>	" +
//                "<tr height='20'> <td width='20'></td> <td width='360' colspan='2'></td> <td width='20'></td></tr></tbody></table> </td> <td width='10'></td> </tr><tr> <td width='10'></td> <td width='400' style='background:#ffffff;border-bottom-left-radius:10px;border-bottom-right-radius:10px'> <table width='400' cellpadding='0' cellspacing='0' border='0'>  <tbody><tr height='35'> <td width='20'></td> <td width='360' colspan='2'></td> <td width='20'></td> </tr> <tr> <td width='20'></td> <td width='360' colspan='2'> <table bgcolor='#ffffff' width='360' cellpadding='0' cellspacing='0' border='0'><tbody><tr><td style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;color:#3b4143;font-size:13px;font-weight:300;line-height:18px;padding:0 0 20px 0'>Dear " +
//                userName + "," + "<br/>" + "<br/>" + "You are add By one of our PetShree Partner." + "<br/>" + "<br/>" +
//                "\n" +
//                "We request you to please Activate your Account by clicking on the button given below. In case\n" +
//                "\n" +
//                "there is a problem with the button please use the link given under the same." + "<br/>" + "<br/>" +
//                "\n" +
//                "Wishing you happy and convenient collecting with PetShree." +
//                "</td></tr>" + "<tr> " +
//                "<td colspan='2' align='center' padding-top: 35px;'>" +
//                "<a href='" + accountActivationUrl + "'><button type='button' style='padding: 5px 40px;color: #FFF;background-color: #00a9f7;font-weight:bold;'>Activate Account</button></a></td> </tr> " +
//                "<tr> " +
//                "<td colspan='2' align='center' style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;color:#3b4143;font-size:13px;font-weight:bold;padding-top:30px;'> Click on this link to Activate Account</td> </tr> " +
//                "<tr> " +
//                "<td colspan='2' align='center' style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;color:#3b4143;font-size:13px;font-weight:300;padding-bottom:30px;'> " +
//                "<a href='" + accountActivationUrl + "'>" + accountActivationUrl + "</a></td> </tr> " +
//
//                "<tr><td style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;font-size:16px;font-weight:300;text-align:center;line-height:16px;padding:0 0 27px 0'></td></tr> <tr><td></td></tr><tr><td style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;color:#3b4143;font-size:13px;font-weight:300;line-height:18px;padding:0 0 20px 0'>Thanking you,<br><span class='il'>PetShree</span> Team <br>\n" +
//                "</td></tr> </tbody></table></td> <td width='20'></td> </tr> " +
//                "<tr height='30'><td width='20'></td> <td width='360' colspan='2'></td> <td width='20'></td> </tr></tbody></table> </td> <td width='10'></td> </tr><tr height='15'> <td width='10'></td> <td width='400'></td> <td width='10'></td> </tr><tr> <td width='10'></td> <td align='center' style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;color:#84939d;font-size:10px;line-height:10px;margin:0 0 0 0;padding:0 0 20px 0'>© <span class='il'>PetShree </span> </td><td width='10'></td> </tr>" +
//                "<tr height='29'> <td width='10'></td> <td width='400'></td> <td width='10'></td> </tr> </tbody></table>", true);

        javaMailSender.send(msg);


    }



//    public void sendContactSupportMail(UmGcsuserM umGcsuserM, ContactSupportRequest contactSupportRequest, String sendTo) throws Exception {
//
//        String emailConfirmUrl = null;
//        String logoURL = null;
//        String webSiteURL = environment.getRequiredProperty("ApplicationContextPath");
//        Long timeinmills = System.currentTimeMillis();
//        umGcsuserM.setFirstName((umGcsuserM.getFirstName().substring(0, 1).toUpperCase()) + umGcsuserM.getFirstName().substring(1));
//        //String emailId = umGcsuserM.getEmailId();
//        MimeMessage msg = javaMailSender.createMimeMessage();
//
//        // true = multipart message
//        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
//
//        helper.setTo(sendTo);
//
//        helper.setSubject(translator.toLocale("contact.support.subject"));
//
//
//        // true = text/html
//        helper.setText("<table bgcolor='#ebeef0' cellpadding='0' cellspacing='0' align='center' border='0' width='420'>  " +
//                "<tbody><tr height='29'> <td width='10'></td> <td width='400'></td> <td width='10'></td> </tr><tr> <td width='10'></td> <td width='400' style='background:grey;border-top-left-radius:10px;border-top-right-radius:10px'> <table width='400' cellpadding='0' cellspacing='0' border='0'> <tbody><tr height='15'> <td width='20'></td> <td width='360' colspan='2'></td> <td width='20'></td></tr>" +
//                "<tr> <td width='20'></td> <td align='left' valign='middle' width='81' style='padding:0;margin:0 0 0 0'><a href='" + webSiteURL + "' style='display:block' target='_blank'><img src='" + logoURL + "' alt='HelloVet' style='width:81px' class='CToWUd'></a></td> <td align='right' valign='middle' width='279' style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;color:#ffffff;font-size:18px;line-height:33px;font-weight:300;white-space:nowrap;padding:3px 0 0 0;margin:0 0 0 0'> <span style='color:#ffffff;text-decoration:none;margin:0 0 0 0;padding:0 0 0 0'>Confirmation</span> </td> <td width='20'></td> </tr>	" +
//                "<tr height='20'> <td width='20'></td> <td width='360' colspan='2'></td> <td width='20'></td></tr></tbody></table> </td> <td width='10'></td> </tr><tr> <td width='10'></td> <td width='400' style='background:#ffffff;border-bottom-left-radius:10px;border-bottom-right-radius:10px'> <table width='400' cellpadding='0' cellspacing='0' border='0'>  <tbody><tr height='35'> <td width='20'></td> <td width='360' colspan='2'></td> <td width='20'></td> </tr> <tr> <td width='20'></td> <td width='360' colspan='2'> <table bgcolor='#ffffff' width='360' cellpadding='0' cellspacing='0' border='0'><tbody><tr><td style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;color:#3b4143;font-size:13px;font-weight:300;line-height:18px;padding:0 0 20px 0'>Dear " +
//                umGcsuserM.getFirstName() + "," + "<br/>" + "<br/>" + "Thank you for choosing HelloVet." + "<br/>" + "<br/>" +
//                "\n" +
//                "We use email to send important communications related to your account and HelloVet\n" +
//                "\n" +
//                "Services." + "<br/>" + "<br/>" +
//                "\n" +
//                "A complaint has be lodged by the user of digipe with the following message : \n" +
//                "\n " + contactSupportRequest.getMessage() +
//                " for the Transaction id " + contactSupportRequest.getTransactionId() + "<br/>" + "<br/>" +
//                "\n" +
//                "Wishing you happy and convenient collecting with HelloVet." +
//                "</td></tr>" + "<tr> " +
//                "<td colspan='2' align='center' padding-top: 35px;'>" +
//                "</td> </tr> " +
//                "<tr> " +
//                "<td colspan='2' align='center' style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;color:#3b4143;font-size:13px;font-weight:bold;padding-top:30px;'> Click on this link to Save</td> </tr> " +
//                "<tr> " +
//                "<td colspan='2' align='center' style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;color:#3b4143;font-size:13px;font-weight:300;padding-bottom:30px;'> " +
//                "</td> </tr> " +
//
//                "<tr><td style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;font-size:16px;font-weight:300;text-align:center;line-height:16px;padding:0 0 27px 0'></td></tr> <tr><td></td></tr><tr><td style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;color:#3b4143;font-size:13px;font-weight:300;line-height:18px;padding:0 0 20px 0'>Thanking you,<br><span class='il'>HelloVet</span> Team <br>\n" +
//                "care.digipe@mann-india.com </td></tr> </tbody></table></td> <td width='20'></td> </tr> " +
//                "<tr height='30'><td width='20'></td> <td width='360' colspan='2'></td> <td width='20'></td> </tr></tbody></table> </td> <td width='10'></td> </tr><tr height='15'> <td width='10'></td> <td width='400'></td> <td width='10'></td> </tr><tr> <td width='10'></td> <td align='center' style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;color:#84939d;font-size:10px;line-height:10px;margin:0 0 0 0;padding:0 0 20px 0'>© <span class='il'>HelloVet </span> </td><td width='10'></td> </tr>" +
//                "<tr height='29'> <td width='10'></td> <td width='400'></td> <td width='10'></td> </tr> </tbody></table>", true);
//
//        javaMailSender.send(msg);
//
//
//    }


//    public void sendMailToStaff(StaffM staffM, MerchantDetailM merchantDetailM, String str) throws Exception {
//
//
//        String emailConfirmUrl = null;
//        String logoURL = null;
//        UmGcsuserM umGcsuserM = staffM.getUmGcsuserM();
//        String contextPath = environment.getRequiredProperty("ApplicationContextPathAS");
//        String webSiteURL = environment.getRequiredProperty("ApplicationContextPath");
//        //logoURL = contextPath + "/images/mpaygo/mpaygo_logo_blue.png";
//        Long timeinmills = System.currentTimeMillis();
//        staffM.setFirstName((staffM.getFirstName().substring(0, 1).toUpperCase()) + staffM.getFirstName().substring(1));
//        //umGcsuserM.setRandomKey(commonUtil.generateTenDigitRandomKey() + timeinmills + commonUtil.generateTenDigitRandomKey());
//        String emailId = staffM.getUmGcsuserM().getEmailId();
//        //boolean keyUpdate = emailVerificationService.updateEmailVerificationKey(umGcsuserM);
//        String sub = "";
//        String msgbody = "";
//        String loginUrl = contextPath + "#/email-signup/" + umGcsuserM.getRandomKey();
//
//        //if (keyUpdate) {
//        MimeMessage msg = javaMailSender.createMimeMessage();
//
//        // true = multipart message
//        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
//
//        helper.setTo(emailId);
//
//
//        if (str.equalsIgnoreCase(Constant.MAIL_FOR_REGISTRAION)) {
//            sub = translator.toLocale("has.nvited.you", new Object[]{merchantDetailM.getCompanyName()});
//
//            msgbody = staffInvitationHtml(merchantDetailM.getUmGcsuserM().getFirstName() + " " + merchantDetailM.getUmGcsuserM().getLastName(), staffM.getFirstName(), loginUrl, webSiteURL, "");
//
//        } else if (str
//                .equalsIgnoreCase(Constant.MAIL_FOR_STAFFACTIVATION)) {
//
//            sub = translator.toLocale("your.HelloVet.account", new Object[]{merchantDetailM.getCompanyName()});
//
//            msgbody = staffActivationHtml(staffM, merchantDetailM, "", webSiteURL, "");
//        }
//
//
//        helper.setSubject(sub);
//        helper.setText(msgbody, true);
//        javaMailSender.send(msg);
//
//        //  }
//
//
//    }


   /*  private String staffInvitationHtml(String merchantName, String staffName, String signUpUrl, String webSiteURL, String logoURL) {
        return "<table bgcolor='#ebeef0' cellpadding='0' cellspacing='0' align='center' border='0' width='420'>  <tbody><tr height='29'> <td width='10'></td> <td width='400'></td> <td width='10'></td> </tr><tr> <td width='10'></td> <td width='400' style='background:grey;border-top-left-radius:10px;border-top-right-radius:10px'> <table width='400' cellpadding='0' cellspacing='0' border='0'> <tbody><tr height='15'> <td width='20'></td> <td width='360' colspan='2'></td> <td width='20'></td></tr>" +
                "<tr> <td width='20'></td> <td align='left' valign='middle' width='81' style='padding:0;margin:0 0 0 0'><a href='" + webSiteURL + "' style='display:block' target='_blank'><img src='" + logoURL + "' alt='HelloVet' style='width:81px' class='CToWUd'></a></td> <td align='right' valign='middle' width='279' style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;color:#ffffff;font-size:18px;line-height:33px;font-weight:300;white-space:nowrap;padding:3px 0 0 0;margin:0 0 0 0'> <span style='color:#ffffff;text-decoration:none;margin:0 0 0 0;padding:0 0 0 0'>Invitation To Accept Payments</span> </td> <td width='20'></td> </tr>	" +
                "<tr height='20'> <td width='20'></td> <td width='360' colspan='2'></td> <td width='20'></td></tr></tbody></table> </td> <td width='10'></td> </tr><tr> <td width='10'></td> <td width='400' style='background:#ffffff;border-bottom-left-radius:10px;border-bottom-right-radius:10px'> <table width='400' cellpadding='0' cellspacing='0' border='0'>  <tbody><tr height='35'> <td width='20'></td> <td width='360' colspan='2'></td> <td width='20'></td> </tr> <tr> <td width='20'></td> <td width='360' colspan='2'> <table bgcolor='#ffffff' width='360' cellpadding='0' cellspacing='0' border='0'><tbody><tr><td style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;color:#3b4143;font-size:13px;font-weight:300;line-height:18px;padding:0 0 20px 0'>Hello " +
                "" + staffName +
                ",</td></tr> " +
                "<tr><td style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;color:#3b4143;font-size:13px;font-weight:300;line-height:18px;padding:0 0 20px 0'>" +
                merchantName +
                " has invited you to accept payments using the <span class='il'>HelloVet</span> Mobile App on your smartphone. With <span class='il'></span> you can easily accept credit/debit cards without a card reader, send invoices and get paid online, manage cash &amp; cheques and more. All card payments will be deposited to " +
                merchantName +
                "'s " +
                "Bank Account.<br><br> Sign up with your mobile number to get started in less than two minutes.</td></tr>" +
                " <tr><td><br><p>If unable to click. Copy Paste " + signUpUrl + " in another tab.</p></td></tr>" +
                "<tr><td style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;font-size:16px;font-weight:300;text-align:center;line-height:16px;padding:0 0 27px 0'><a href='" + signUpUrl + "' style='display:inline-block;font-size:16px;color:#ffffff;line-height:16px;background:#39b896;border-radius:5px;padding:12px 25px 12px 25px' target='_blank'>Sign Up</a></td></tr> <tr><td></td></tr>" +
                "<tr><td style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;color:#3b4143;font-size:13px;font-weight:300;line-height:18px;padding:0 0 20px 0'>Thanks,<br> The <span class='il'>HelloVet</span> Team</td></tr> </tbody></table></td> <td width='20'></td> </tr> " +
                "<tr height='30'><td width='20'></td> <td width='360' colspan='2'></td> <td width='20'></td> </tr></tbody></table> </td> <td width='10'></td> </tr><tr height='15'> <td width='10'></td> <td width='400'></td> <td width='10'></td> </tr><tr> <td width='10'></td> <td align='center' style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;color:#84939d;font-size:10px;line-height:10px;margin:0 0 0 0;padding:0 0 20px 0'>© <span class='il'>HelloVet </span> </td><td width='10'></td> </tr><tr height='29'> <td width='10'></td> <td width='400'></td> <td width='10'></td> </tr> </tbody></table>";
    }

   private String staffActivationHtml(StaffM staffM, MerchantDetailM merchantDetailM, String mailSignature, String webSiteURL, String logoURL) {
        return "<table bgcolor='#ebeef0' cellpadding='0' cellspacing='0' align='center' border='0' width='420'>  <tbody><tr height='29'> <td width='10'></td> <td width='400'></td> <td width='10'></td> </tr><tr> <td width='10'></td> <td width='400' style='background:grey;border-top-left-radius:10px;border-top-right-radius:10px'> <table width='400' cellpadding='0' cellspacing='0' border='0'> <tbody><tr height='15'> <td width='20'></td> <td width='360' colspan='2'></td> <td width='20'></td></tr>" +
                "<tr> <td width='20'></td> <td align='left' valign='middle' width='81' style='padding:0;margin:0 0 0 0'><a href='" + webSiteURL + "' style='display:block' target='_blank'><img src='" + logoURL + "' alt='HelloVet' style='width:81px' class='CToWUd'></a></td> <td align='right' valign='middle' width='279' style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;color:#ffffff;font-size:18px;line-height:33px;font-weight:300;white-space:nowrap;padding:3px 0 0 0;margin:0 0 0 0'> <span style='color:#ffffff;text-decoration:none;margin:0 0 0 0;padding:0 0 0 0'>Unlinked from Payments</span> </td> <td width='20'></td> </tr>	" +
                "<tr height='20'> <td width='20'></td> <td width='360' colspan='2'></td> <td width='20'></td></tr></tbody></table> </td> <td width='10'></td> </tr><tr> <td width='10'></td> <td width='400' style='background:#ffffff;border-bottom-left-radius:10px;border-bottom-right-radius:10px'> <table width='400' cellpadding='0' cellspacing='0' border='0'>  <tbody><tr height='35'> <td width='20'></td> <td width='360' colspan='2'></td> <td width='20'></td> </tr> <tr> <td width='20'></td> <td width='360' colspan='2'> <table bgcolor='#ffffff' width='360' cellpadding='0' cellspacing='0' border='0'><tbody><tr><td style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;color:#3b4143;font-size:13px;font-weight:300;line-height:18px;padding:0 0 20px 0'>Hello " +
                staffM.getFirstName()
                + ","
                + "<br><br>"
                + "Your account has been activated. You can now accept payments on behalf of "
                + merchantDetailM.getCompanyName()
                + "."
                + "<br><br>"
                + "If you have any questions please contact us at support@digipe.com or call toll-free at (855) 487-6957. "
                + "<br><br>" + mailSignature +
                "<tr><td style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;font-size:16px;font-weight:300;text-align:center;line-height:16px;padding:0 0 27px 0'></td></tr>" +
                " <tr><td></td></tr><tr><td style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;color:#3b4143;font-size:13px;font-weight:300;line-height:18px;padding:0 0 20px 0'>Thanks,<br> The <span class='il'>HelloVet</span> Team</td></tr> </tbody></table></td> <td width='20'></td> </tr> " +
                "<tr height='30'><td width='20'></td> <td width='360' colspan='2'></td> <td width='20'></td> </tr></tbody></table> </td>" +
                " <td width='10'></td> </tr><tr height='15'> <td width='10'></td> <td width='400'></td> <td width='10'></td> </tr>" +
                "<tr> <td width='10'></td> <td align='center' style='font-family:Helvetica Neue,Helvetica,Arial,sans-serif;color:#84939d;font-size:10px;line-height:10px;margin:0 0 0 0;padding:0 0 20px 0'>© <span class='il'>HelloVet </span> </td>" +
                "<td width='10'></td> </tr><tr height='29'> <td width='10'></td> <td width='400'></td> <td width='10'></td> </tr> </tbody></table>";

    }*/

}

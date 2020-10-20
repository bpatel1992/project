package com.rahul.project.gateway.service;

import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.dao.AbstractDao;
import com.rahul.project.gateway.dto.TransactionProcessDTO;
import com.rahul.project.gateway.hash.AESSecurity;
import com.rahul.project.gateway.model.Appointment;
import com.rahul.project.gateway.model.Services;
import com.rahul.project.gateway.model.Transaction;
import com.rahul.project.gateway.model.User;
import com.rahul.project.gateway.repository.TransactionRepository;
import com.rahul.project.gateway.utility.CommonUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;

@TransactionalService
public class TransactionProcessService {

    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    AbstractDao abstractDao;

    @Autowired
    CommonUtility commonUtility;
    @Autowired
    Environment environment;
    @Autowired
    AESSecurity security;
    String INDIAN_STANDARD_TIME_ZONE = "IST";
    String SUCCESS_FLAG = "success";
    @Autowired
    private TransactionRepository transactionRepository;

    public TransactionProcessDTO processGatewayHostedSave(TransactionProcessDTO transactionProcessDTO) {

        logger.info(" Inside ProcessTransactionService first request to save transaction");

        User sendByUser = abstractDao.getEntityById(User.class, transactionProcessDTO.getCustomerId());
        Appointment appointment = abstractDao.getEntityById(Appointment.class, transactionProcessDTO.getAppointmentId());

        Transaction transaction = new Transaction();
        transaction.setAmount(new BigDecimal(String.valueOf(transactionProcessDTO.getAmount())));
        transaction.setStatus(Transaction.TransactionStatus.INPROGRESS);
        transaction.setServiceReferenceNumber(appointment.getId());
        transaction.setCustomerUserId(sendByUser);
        transaction.setFee(new BigDecimal(1));
        transaction.setTax(new BigDecimal("1.5"));
        transaction.setPayableAmount(transaction.getAmount().add(transaction.getFee().add(transaction.getTax())));
        transaction.setServices(new Services(transactionProcessDTO.getServiceId()));
        transaction.setIsApproved(false);
        transaction.setIsReconciled(false);
        transaction.setRedirectUrl(transactionProcessDTO.getRedirectURL());
        transaction.setTransactionId(commonUtility.getTransactionID());
        java.util.Date date = commonUtility.getCurrentDate(INDIAN_STANDARD_TIME_ZONE);
        transaction.setDate(date);
        transaction.setTime(date);
        transaction.setLogTime(date);

        abstractDao.saveOrUpdateEntity(transaction);

        transactionProcessDTO.setRespKey(SUCCESS_FLAG);
        transactionProcessDTO.setTransactionId(transaction.getTransactionId());
        transactionProcessDTO.setTransactionTime(commonUtility.getDateString(transaction.getLogTime()));
        transactionProcessDTO.setRedirectURL(environment.getRequiredProperty("payment.aggregator.url") + "?pgcode=PGIND001&callBackURL="
                + environment.getRequiredProperty("payment.aggregator.callback.url") + "&txnRef=" + transaction.getTransactionId()
                + "&currency=inr&mobileNo=" + sendByUser.getMobile() + ((sendByUser.getEmail() != null
                && !"".equalsIgnoreCase(sendByUser.getEmail())) ? "&email=" + sendByUser.getEmail() : "")
                + "&customerId=" + sendByUser.getId() + "&amount=" + transactionProcessDTO.getAmount() + "&hash=" +
                security.encrypt("PGIND001" + transactionProcessDTO.getAmount() + sendByUser.getId() + sendByUser.getEmail()
                        + sendByUser.getMobile() + transaction.getTransactionId() + environment.getRequiredProperty("payment.aggregator.callback.url") + "inr"));

        return transactionProcessDTO;
    }


    public TransactionProcessDTO processGatewayHostedUpdate(TransactionProcessDTO transactionProcessDTO) throws Exception {

        Transaction transaction = transactionRepository.getByTransactionId(transactionProcessDTO.getTransactionId());

        if (transactionProcessDTO.getStatus().equals("S")) {
            transaction.setStatus(Transaction.TransactionStatus.SUCCESS);
        } else {
            transaction.setStatus(Transaction.TransactionStatus.FAILED);
        }
        transaction.setTransactionGatewayReferenceId(transactionProcessDTO.getTransactionGatewayReferenceId());
        abstractDao.saveOrUpdateEntity(transaction);


        if (transaction.getRedirectUrl() != null)
            transactionProcessDTO.setRedirectURL(transaction.getRedirectUrl());
        transactionProcessDTO.setRespKey(SUCCESS_FLAG);
        transactionProcessDTO.setTransactionId(transaction.getTransactionId());
        transactionProcessDTO.setTransactionTime(commonUtility.getDateString(transaction.getLogTime()));
        return transactionProcessDTO;
    }


}

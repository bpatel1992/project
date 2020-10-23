package com.rahul.project.gateway.service;

import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.dao.AbstractDao;
import com.rahul.project.gateway.dto.CreateAppointmentDto;
import com.rahul.project.gateway.dto.FeeDTO;
import com.rahul.project.gateway.dto.TransactionProcessDTO;
import com.rahul.project.gateway.enums.FeeStatus;
import com.rahul.project.gateway.enums.TaxStatus;
import com.rahul.project.gateway.hash.AESSecurity;
import com.rahul.project.gateway.model.*;
import com.rahul.project.gateway.repository.FeeRepository;
import com.rahul.project.gateway.repository.TransactionRepository;
import com.rahul.project.gateway.repository.UserAddressTimingRepository;
import com.rahul.project.gateway.utility.CommonUtility;
import com.rahul.project.gateway.utility.Translator;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
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

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    FeeRepository feeRepository;

    ModelMapper modelMapper;

    String INDIAN_STANDARD_TIME_ZONE = "IST";
    String SUCCESS_FLAG = "success";


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
                + "&customerId=" + sendByUser.getId() + "&amount=" + transaction.getPayableAmount() + "&hash=" +
                security.encrypt("PGIND001" + transaction.getPayableAmount() + sendByUser.getId() + sendByUser.getEmail()
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


    PropertyMap<FeeDTO, Fee> feeMapping = new PropertyMap<FeeDTO, Fee>() {
        protected void configure() {
            map().getService().setId(source.getServicesId());
            map().getAuthority().setAuthorityId(source.getAuthorityId());
        }
    };

    PropertyMap<Fee, FeeDTO> feeFieldMapping = new PropertyMap<Fee, FeeDTO>() {
        protected void configure() {
            map().setAuthorityId(source.getAuthority().getAuthorityId());
            map().setServicesId(source.getService().getId());

        }
    };

    @Autowired
    public TransactionProcessService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        modelMapper.addMappings(feeMapping);
        modelMapper.addMappings(feeFieldMapping);
    }


    public FeeDTO processFee(FeeDTO feeDTO) throws Exception {

        Fee fee = feeRepository.getByServiceAndAuthority(new Services(feeDTO.getServicesId()),new Authority(feeDTO.getAuthorityId()));
//        feeDTO=modelMapper.map(fee, FeeDTO.class);
    feeDTO.setFee(fee.getFee());
    feeDTO.setTax(fee.getTax());
    feeDTO.setFeeType(fee.getFeeType().getName());
    feeDTO.setTaxType(fee.getTaxType().getName());
       if(TaxStatus.VARIABLE.equals(fee.getTaxType()) && FeeStatus.VARIABLE.equals(fee.getFeeType())){
           feeDTO.setPayableAmount(feeDTO.getAmount().add(feeDTO.getAmount().multiply(feeDTO.getFee().divide(new BigDecimal(100)))).add(feeDTO.getFee().multiply(feeDTO.getTax().divide(new BigDecimal(100)))));
       }else if(TaxStatus.VARIABLE.equals(fee.getTaxType())){
           feeDTO.setPayableAmount(feeDTO.getAmount().add(feeDTO.getFee()).add(feeDTO.getFee().multiply(feeDTO.getTax().divide(new BigDecimal(100)))));
       }else if(FeeStatus.VARIABLE.equals(fee.getFeeType())){
           feeDTO.setPayableAmount(feeDTO.getAmount().add(feeDTO.getTax()).add(feeDTO.getAmount().multiply(feeDTO.getFee().divide(new BigDecimal(100)))));
       }
       else{
           feeDTO.setPayableAmount(feeDTO.getAmount().add(feeDTO.getFee()).add(feeDTO.getTax()));
       }
        return feeDTO;
    }



}

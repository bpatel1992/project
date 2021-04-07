package com.rahul.project.gateway.service;

import com.rahul.project.gateway.configuration.BusinessException;
import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.dao.AbstractDao;
import com.rahul.project.gateway.dto.FeeDTO;
import com.rahul.project.gateway.dto.TransactionProcessDTO;
import com.rahul.project.gateway.enums.TransactionStatus;
import com.rahul.project.gateway.hash.AESSecurity;
import com.rahul.project.gateway.model.*;
import com.rahul.project.gateway.repository.FeeRepository;
import com.rahul.project.gateway.repository.TransactionRepository;
import com.rahul.project.gateway.repository.UserServiceStatusRepository;
import com.rahul.project.gateway.utility.CommonUtility;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.math.BigDecimal;
import java.util.Date;

@TransactionalService
public class TransactionProcessService {

//    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

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

    @Autowired
    UserServiceStatusRepository userServiceStatusRepository;

//    ModelMapper modelMapper;

    String SUCCESS_FLAG = "success";

    public TransactionProcessDTO processGatewayResponse(TransactionProcessDTO transactionProcessDTO) throws Exception {
        Transaction transaction = transactionRepository.getByTransactionId(transactionProcessDTO.getTransactionId());

        if ("S".equalsIgnoreCase(transactionProcessDTO.getStatus())) {
            transaction.setStatus(TransactionStatus.SUCCESS);
            if (transaction.getService() != null) {
                if (transaction.getService().getId() == 2) {
                    ServicePackage servicePackage = transaction.getServicePackage();
                    UserServiceStatus userServiceStatus;
                    userServiceStatus = userServiceStatusRepository.getDistinctByUserIdAndServiceId(transaction.getCustomerUserId(), new Services(2));
                    Date fromDate;
                    if (userServiceStatus == null) {
                        userServiceStatus = new UserServiceStatus();
                        userServiceStatus.setUserId(transaction.getCustomerUserId());
                        userServiceStatus.setServiceId(new Services(2));
                        fromDate = new Date();
                    } else
                        fromDate = userServiceStatus.getValidityToDate();
                    userServiceStatus.setValidityFromDate(fromDate);
                    userServiceStatus.setValidityToDate(DateUtils.addMonths(fromDate, servicePackage.getValidityInMonths()));
                    userServiceStatus.setPlan(servicePackage);
                    userServiceStatus.setTimeZone(transaction.getTimeZone());
                    userServiceStatus.setStatus(true);
                    abstractDao.saveOrUpdateEntity(userServiceStatus);

                    User customerUserId = transaction.getCustomerUserId();
                    customerUserId.setSubscribed(true);
//                    abstractDao.saveOrUpdateEntity(customerUserId);
                }
            }
        } else {
            transaction.setStatus(TransactionStatus.FAILED);
        }
        transaction.setAggregatorReferenceNumber(transactionProcessDTO.getTransactionGatewayReferenceId());


        abstractDao.saveOrUpdateEntity(transaction);


        if (transaction.getRedirectUrl() != null)
            transactionProcessDTO.setRedirectURL(transaction.getRedirectUrl());
        transactionProcessDTO.setRespKey(SUCCESS_FLAG);
        transactionProcessDTO.setTransactionId(transaction.getTransactionId());
        transactionProcessDTO.setLogDate(commonUtility.getDateConverted(transaction.getLogTime()));
        return transactionProcessDTO;
    }

    public TransactionProcessDTO processGatewayHostedSave(TransactionProcessDTO transactionProcessDTO) throws Exception {
        Long loggedInUser = commonUtility.getLoggedInUser();
        User sendByUser = abstractDao.getEntityById(User.class, loggedInUser != null
                ? loggedInUser : transactionProcessDTO.getCustomerId());

        Transaction transaction = new Transaction();
        if (transactionProcessDTO.getServiceId() == 1) {
            Appointment appointment = abstractDao.getEntityById(Appointment.class, transactionProcessDTO.getAppointmentId());
            transaction.setServiceReferenceNumber(appointment.getId());
            transaction.setAmount(new BigDecimal(String.valueOf(transactionProcessDTO.getAmount())));

            FeeDTO feeDTO = new FeeDTO(transactionProcessDTO.getServiceId(), transactionProcessDTO.getAuthorityId(), transactionProcessDTO.getAmount());
            feeDTO = processFee(feeDTO);
            if (feeDTO != null) {
                transaction.setFee(feeDTO.getFee());
                transaction.setTax(feeDTO.getTax());
                transaction.setPayableAmount(transaction.getAmount().add(transaction.getFee().add(transaction.getTax())));
            } else
                transaction.setPayableAmount(transaction.getAmount());

        } else if (2 == transactionProcessDTO.getServiceId()) {
            ServicePackage servicePackage = abstractDao.getEntityById(ServicePackage.class, transactionProcessDTO.getPackageId());
            if (servicePackage == null)
                throw new BusinessException("internal.error");
            transaction.setPayableAmount(servicePackage.getPackageMRP().subtract(servicePackage.getDiscount()));
            transaction.setServicePackage(servicePackage);
        }

        transaction.setService(new Services(transactionProcessDTO.getServiceId()));
        transaction.setTransactionType(new TransactionTypes(transactionProcessDTO.getTxnTypeId()));

        transaction.setStatus(TransactionStatus.INPROGRESS);

        transaction.setCustomerUserId(sendByUser);


        transaction.setIsApproved(false);
        transaction.setIsReconciled(false);
        transaction.setRedirectUrl(transactionProcessDTO.getRedirectURL());
        transaction.setTransactionId(commonUtility.getTransactionID());
        Date date = commonUtility.getDateByTimeZoneDate(transactionProcessDTO.getTimeZone());
        transaction.setDate(date);
        transaction.setTime(date);
        transaction.setLogTime(date);
        transaction.setTimeZone(transactionProcessDTO.getTimeZone());
        abstractDao.saveOrUpdateEntity(transaction);

        transactionProcessDTO.setRespKey(SUCCESS_FLAG);
        transactionProcessDTO.setTransactionId(transaction.getTransactionId());
        transactionProcessDTO.setLogDate(commonUtility.getDateConverted(transaction.getLogTime()));
        transactionProcessDTO.setRedirectURL(environment.getRequiredProperty("payment.aggregator.url") + "?pgcode=PGIND001&callBackURL="
                + environment.getRequiredProperty("payment.aggregator.callback.url") + "&txnRef=" + transaction.getTransactionId()
                + "&currency=inr&mobileNo=" + sendByUser.getMobile() + ((sendByUser.getEmail() != null
                && !"".equalsIgnoreCase(sendByUser.getEmail())) ? "&email=" + sendByUser.getEmail() : "")
                + "&customerId=" + sendByUser.getId() + "&amount=" + transaction.getPayableAmount() + "&hash=" +
                security.encrypt("PGIND001" + transaction.getPayableAmount() + sendByUser.getId() + sendByUser.getEmail()
                        + sendByUser.getMobile() + transaction.getTransactionId() + environment.getRequiredProperty("payment.aggregator.callback.url") + "inr"));

        return transactionProcessDTO;
    }



    /*PropertyMap<FeeDTO, Fee> feeMapping = new PropertyMap<FeeDTO, Fee>() {
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
    };*/

  /*  @Autowired
    public TransactionProcessService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        modelMapper.addMappings(feeMapping);
        modelMapper.addMappings(feeFieldMapping);
    }*/


    public FeeDTO processFee(FeeDTO feeDTO) throws Exception {

        Fee fee = new Fee();//feeRepository.getByServiceAndAuthority(new Services(feeDTO.getServicesId()), new Authority(feeDTO.getAuthorityId()));
        if (fee != null) {
//        feeDTO=modelMapper.map(fee, FeeDTO.class);

            /*feeDTO.setFee(fee.getFeeAmount());
            feeDTO.setTax(fee.getTax());
            feeDTO.setFeeType(fee.getFeeType().getName());
            feeDTO.setTaxType(fee.getTaxType().getName());
            if (TaxType.VARIABLE.equals(fee.getTaxType()) && FeeStatus.VARIABLE.equals(fee.getFeeType())) {
                feeDTO.setPayableAmount(feeDTO.getAmount().add(feeDTO.getAmount().multiply(feeDTO.getFee().divide(new BigDecimal(100)))).add(feeDTO.getFee().multiply(feeDTO.getTax().divide(new BigDecimal(100)))));
            } else if (TaxType.VARIABLE.equals(fee.getTaxType())) {
                feeDTO.setPayableAmount(feeDTO.getAmount().add(feeDTO.getFee()).add(feeDTO.getFee().multiply(feeDTO.getTax().divide(new BigDecimal(100)))));
            } else if (FeeStatus.VARIABLE.equals(fee.getFeeType())) {
                feeDTO.setPayableAmount(feeDTO.getAmount().add(feeDTO.getTax()).add(feeDTO.getAmount().multiply(feeDTO.getFee().divide(new BigDecimal(100)))));
            } else {
                feeDTO.setPayableAmount(feeDTO.getAmount().add(feeDTO.getFee()).add(feeDTO.getTax()));
            }*/
        } else
            feeDTO = null;
        return feeDTO;
    }


}

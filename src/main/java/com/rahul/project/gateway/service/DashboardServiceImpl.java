package com.rahul.project.gateway.service;

import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.dao.AbstractDao;
import com.rahul.project.gateway.dao.DashboardDao;
import com.rahul.project.gateway.dto.*;
import com.rahul.project.gateway.repository.TransactionRepository;
import com.rahul.project.gateway.utility.CommonUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@TransactionalService
public class DashboardServiceImpl implements DashBoardService {

    private static final Logger logger = LoggerFactory.getLogger(DashboardServiceImpl.class);

    @Autowired
    DashboardDao dashboardDao;

    @Autowired
    CommonUtility commonUtil;

    @Autowired
    TransactionRepository repository;
    @Autowired
    AbstractDao abstractDao;

    public TxnSummary getTxnSummary() throws Exception {

        TxnSummary txnSummary = null;

        try {
            List<Object> objectList = dashboardDao.getTxnSummary();

            if (objectList != null && objectList.size() > 0) {
                Object[] objArry = (Object[]) objectList.get(0);
                txnSummary = new TxnSummary();
                txnSummary.setTotalTxn(Long.parseLong(objArry[0].toString()));
                txnSummary.setTotalAmount(new BigDecimal(objArry[1].toString()));
                txnSummary.setOperatorEarning(new BigDecimal(objArry[2] != null ? objArry[2].toString() : "0.0"));

            }
            txnSummary.setOperatorLiability(dashboardDao.getTotalLibality());

        } catch (Exception e) {
            logger.error(" Error in getTxnSummary ", e);
        }
        return txnSummary;
    }

    @Override
    public TotalDashboardSummaryResponse getTotalDashboardSummary() {

        TotalDashboardSummaryResponse totalDashboardSummaryResponse = new TotalDashboardSummaryResponse();
        totalDashboardSummaryResponse.setTotalEarning(new BigDecimal(100));
        totalDashboardSummaryResponse.setTotalSales(new BigDecimal(200));
        return totalDashboardSummaryResponse;
    }

    @Override
    public Set<WeeklySaleDashboardDataResponse> getDailySaleData() {
        Set<WeeklySaleDashboardDataResponse> dashboardResponses = new HashSet<>();

        WeeklySaleDashboardDataResponse weeklyDashboardDataResponse = null;
        {
            weeklyDashboardDataResponse = new WeeklySaleDashboardDataResponse();
            weeklyDashboardDataResponse.setAmount(new BigDecimal("50"));
            weeklyDashboardDataResponse.setWeekName("Sunday");
            dashboardResponses.add(weeklyDashboardDataResponse);
        }

        {
            weeklyDashboardDataResponse = new WeeklySaleDashboardDataResponse();
            weeklyDashboardDataResponse.setAmount(new BigDecimal("40"));
            weeklyDashboardDataResponse.setWeekName("Monday");
            dashboardResponses.add(weeklyDashboardDataResponse);
        }
        {
            weeklyDashboardDataResponse = new WeeklySaleDashboardDataResponse();
            weeklyDashboardDataResponse.setAmount(new BigDecimal("30"));
            weeklyDashboardDataResponse.setWeekName("Tuesday");
            dashboardResponses.add(weeklyDashboardDataResponse);
        }
        {
            weeklyDashboardDataResponse = new WeeklySaleDashboardDataResponse();
            weeklyDashboardDataResponse.setAmount(new BigDecimal("20"));
            weeklyDashboardDataResponse.setWeekName("Wednesday");
            dashboardResponses.add(weeklyDashboardDataResponse);
        }
        {
            weeklyDashboardDataResponse = new WeeklySaleDashboardDataResponse();
            weeklyDashboardDataResponse.setAmount(new BigDecimal("10"));
            weeklyDashboardDataResponse.setWeekName("Thursday");
            dashboardResponses.add(weeklyDashboardDataResponse);
        }
        {
            weeklyDashboardDataResponse = new WeeklySaleDashboardDataResponse();
            weeklyDashboardDataResponse.setAmount(new BigDecimal("60"));
            weeklyDashboardDataResponse.setWeekName("Friday");
            dashboardResponses.add(weeklyDashboardDataResponse);
        }
        {
            weeklyDashboardDataResponse = new WeeklySaleDashboardDataResponse();
            weeklyDashboardDataResponse.setAmount(new BigDecimal("70"));
            weeklyDashboardDataResponse.setWeekName("Saturday");
            dashboardResponses.add(weeklyDashboardDataResponse);
        }
        return dashboardResponses;
    }

    @Override
    public Set<WeeklyEarningDashboardDataResponse> getWeeklyEarningData() {
        Set<WeeklyEarningDashboardDataResponse> dashboardResponses = new HashSet<>();

        WeeklyEarningDashboardDataResponse weeklyDashboardDataResponse = null;
        {
            weeklyDashboardDataResponse = new WeeklyEarningDashboardDataResponse();
            weeklyDashboardDataResponse.setColor("red");
            weeklyDashboardDataResponse.setAmount(new BigDecimal("50"));
            weeklyDashboardDataResponse.setWeekName("Sunday");
            dashboardResponses.add(weeklyDashboardDataResponse);
        }

        {
            weeklyDashboardDataResponse = new WeeklyEarningDashboardDataResponse();
            weeklyDashboardDataResponse.setAmount(new BigDecimal("40"));
            weeklyDashboardDataResponse.setColor("red");
            weeklyDashboardDataResponse.setWeekName("Monday");
            dashboardResponses.add(weeklyDashboardDataResponse);
        }
        {
            weeklyDashboardDataResponse = new WeeklyEarningDashboardDataResponse();
            weeklyDashboardDataResponse.setAmount(new BigDecimal("30"));
            weeklyDashboardDataResponse.setColor("red");
            weeklyDashboardDataResponse.setWeekName("Tuesday");
            dashboardResponses.add(weeklyDashboardDataResponse);
        }
        {
            weeklyDashboardDataResponse = new WeeklyEarningDashboardDataResponse();
            weeklyDashboardDataResponse.setAmount(new BigDecimal("20"));
            weeklyDashboardDataResponse.setWeekName("Wednesday");
            weeklyDashboardDataResponse.setColor("red");
            dashboardResponses.add(weeklyDashboardDataResponse);
        }
        {
            weeklyDashboardDataResponse = new WeeklyEarningDashboardDataResponse();
            weeklyDashboardDataResponse.setAmount(new BigDecimal("10"));
            weeklyDashboardDataResponse.setColor("white");
            weeklyDashboardDataResponse.setWeekName("Thursday");
            dashboardResponses.add(weeklyDashboardDataResponse);
        }
        {
            weeklyDashboardDataResponse = new WeeklyEarningDashboardDataResponse();
            weeklyDashboardDataResponse.setAmount(new BigDecimal("60"));
            weeklyDashboardDataResponse.setColor("yellow");
            weeklyDashboardDataResponse.setWeekName("Friday");
            dashboardResponses.add(weeklyDashboardDataResponse);
        }
        {
            weeklyDashboardDataResponse = new WeeklyEarningDashboardDataResponse();
            weeklyDashboardDataResponse.setAmount(new BigDecimal("70"));
            weeklyDashboardDataResponse.setColor("red");
            weeklyDashboardDataResponse.setWeekName("Saturday");
            dashboardResponses.add(weeklyDashboardDataResponse);
        }
        return dashboardResponses;


    }

    @Override
    public RecentFundTransfers getRecentFundTransfer(int pageNo, int pageSize) {
        RecentFundTransfers recentFundTransfers;
        recentFundTransfers = dashboardDao.getRecentFundTransfer(commonUtil.getLoggedInUser(), pageNo, pageSize);
        return recentFundTransfers;
    }

    @Override
    public TotalReceiveAmount totalAmountReceive() {
        TotalReceiveAmount totalReceiveAmount = new TotalReceiveAmount();
        totalReceiveAmount.setResponseCode("5555");
        totalReceiveAmount.setResponseMessage("Failed");
        try {
            Double amount = repository.getTotalReceiveAmount(commonUtil.getLoggedInUser());
            if (amount != null) {
                totalReceiveAmount.setResponseCode("0000");
                totalReceiveAmount.setResponseMessage("Success");
                totalReceiveAmount.setAmount(amount);
            } else {
                totalReceiveAmount.setResponseCode("0000");
                totalReceiveAmount.setResponseMessage("Success");
                totalReceiveAmount.setAmount(0.00);
            }
        } catch (Exception e) {
            logger.info(e.toString());
        }
        return totalReceiveAmount;
    }

    @Override
    public List<TopFiveMerchantDetailResponse> topFiveMerchants(DashboardDTO dashboardDTO) {

        String query = "select SUM(amount),COUNT(*), customer_user_id FROM txn_m WHERE DATE_TRUNC('" + dashboardDTO.getFilter() + "',txn_date) > CURRENT_DATE - interval '1 " + dashboardDTO.getFilter() + "' group by customer_user_id  ORDER BY sum DESC LIMIT 5";
        List<Object[]> merchantList = abstractDao.getQueryForListSQL(query);
        List<TopFiveMerchantDetailResponse> merchantDetailResponseList = null;
        if (Validation.isValidList(merchantList)) {
            merchantDetailResponseList = new ArrayList<>();
            TopFiveMerchantDetailResponse topFiveMerchantDetailResponse = null;
            for (Object[] a : merchantList) {
                topFiveMerchantDetailResponse = new TopFiveMerchantDetailResponse();
                topFiveMerchantDetailResponse.setMerchantUserId(Long.valueOf(a[2].toString()));
                topFiveMerchantDetailResponse.setMerchantNoOfTxn(Integer.valueOf(a[1].toString()));
                topFiveMerchantDetailResponse.setMerchantTotalAmount(a[0].toString());
                merchantDetailResponseList.add(topFiveMerchantDetailResponse);
            }
        }
        return merchantDetailResponseList;
    }

    @Override
    public TotalReceiveAmount totalSpentAmount() {
        TotalReceiveAmount totalReceiveAmount = new TotalReceiveAmount();
        totalReceiveAmount.setResponseCode("5555");
        totalReceiveAmount.setResponseMessage("Failed");
        try {
            Double amount = repository.getTotalSpentAmount(commonUtil.getLoggedInUser());
            if (amount != null) {
                totalReceiveAmount.setResponseCode("0000");
                totalReceiveAmount.setResponseMessage("Success");
                totalReceiveAmount.setAmount(amount);
            } else {
                totalReceiveAmount.setResponseCode("0000");
                totalReceiveAmount.setResponseMessage("Success");
                totalReceiveAmount.setAmount(0.00);
            }
        } catch (Exception e) {
            logger.info(e.toString());
        }
        return totalReceiveAmount;
    }
}

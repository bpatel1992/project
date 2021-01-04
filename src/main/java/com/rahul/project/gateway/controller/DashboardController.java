package com.rahul.project.gateway.controller;

import com.rahul.project.gateway.configuration.annotations.RESTController;
import com.rahul.project.gateway.dto.*;
import com.rahul.project.gateway.service.DashBoardService;
import com.rahul.project.gateway.service.Validation;
import com.rahul.project.gateway.utility.CommonUtility;
import com.rahul.project.gateway.utility.Translator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.lang.invoke.MethodHandles;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author rahul malhotra <rahul.malhotra@mann-india.com>
 * @version 2020-03-04
 * @since 1.0
 */

@RESTController
public class DashboardController {

    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    CommonUtility commonUtil;

    @Autowired
    DashBoardService dashBoardService;

    @Autowired
    Translator translator;

    String SUCCESS_RESPONSE_CODE = "0000";
    String FAILURE_RESPONSE_CODE = "1111";

    @RequestMapping(method = RequestMethod.POST, value = "/oauth2/api/dashboard/getDashboardSummary",
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public TxnSummary getTxnSummary(@Valid @RequestBody DashboardTxnSummaryDTO dashboardDTO) throws Exception {
        return dashBoardService.getTxnSummary();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/oauth2/api/merchant/dashboard/getTotalDashboardSummary",
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public TotalDashboardSummaryResponse getTotalDashboardSummary(@Valid @RequestBody TotalDashboardSummaryRequest totalDashboardSummaryRequest) throws Exception {
        return dashBoardService.getTotalDashboardSummary();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/oauth2/api/merchant/dashboard/getDashboardSaleData",
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Set<WeeklySaleDashboardDataResponse> getDailySaleData(@Valid @RequestBody TotalDashboardSummaryRequest totalDashboardSummaryRequest) throws Exception {
        return dashBoardService.getDailySaleData();
    }

    @RequestMapping(method = RequestMethod.POST, value = "/oauth2/api/merchant/dashboard/getDashboardEarningData",
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Set<WeeklyEarningDashboardDataResponse> getEarningData(@Valid @RequestBody TotalDashboardSummaryRequest totalDashboardSummaryRequest) throws Exception {
        return dashBoardService.getWeeklyEarningData();
    }

    @RequestMapping(path = {"/oauth2/api/dashboard/wallet/status"}, method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Set<WalletDashboardResponse> processTxnByWallet(@Valid @RequestBody DashboardDTO dashboardDTO) throws Exception {
        Set<WalletDashboardResponse> dashboardResponses = new HashSet<>();
        logger.info("inside pos process in payment process controller");
        {
            WalletDashboardResponse walletDashboardResponse = new WalletDashboardResponse();
            walletDashboardResponse.setWalletAmount(commonUtil.currencyFormat(new BigDecimal("5000")));
            walletDashboardResponse.setAmount(new BigDecimal("5000"));
            walletDashboardResponse.setWalletColor("red");
            walletDashboardResponse.setWalletName("customer wallet");
            dashboardResponses.add(walletDashboardResponse);
        }
        {
            WalletDashboardResponse walletDashboardResponse = new WalletDashboardResponse();
            walletDashboardResponse.setWalletAmount(commonUtil.currencyFormat(new BigDecimal("50")));
            walletDashboardResponse.setAmount(new BigDecimal("50"));
            walletDashboardResponse.setWalletColor("yellow");
            walletDashboardResponse.setWalletName("commission wallet");
            dashboardResponses.add(walletDashboardResponse);
        }
        {
            WalletDashboardResponse walletDashboardResponse = new WalletDashboardResponse();
            walletDashboardResponse.setWalletAmount(commonUtil.currencyFormat(new BigDecimal("3549")));
            walletDashboardResponse.setAmount(new BigDecimal("3549"));
            walletDashboardResponse.setWalletColor("green");
            walletDashboardResponse.setWalletName("settlement wallet");
            dashboardResponses.add(walletDashboardResponse);
        }
        {
            WalletDashboardResponse walletDashboardResponse = new WalletDashboardResponse();
            walletDashboardResponse.setWalletAmount(commonUtil.currencyFormat(new BigDecimal("689")));
            walletDashboardResponse.setAmount(new BigDecimal("689"));
            walletDashboardResponse.setWalletColor("blue");
            walletDashboardResponse.setWalletName("tax wallet");
            dashboardResponses.add(walletDashboardResponse);
        }
        return dashboardResponses;
    }

    @RequestMapping(path = {"/oauth2/api/dashboard/transaction/status"}, method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public TransactionDetailResponse processTxnStatus(@Valid @RequestBody DashboardDTO dashboardDTO) throws Exception {
        TransactionDetailResponse transactionDetailResponse = new TransactionDetailResponse();
        transactionDetailResponse.setTransactionAmountLabel("Transaction Amount (* 1000)");
        transactionDetailResponse.setTransactionAmountColor("red");
        transactionDetailResponse.setNoOfTxnColor("blue");
        transactionDetailResponse.setNoOfTxnLabel("Number of Transactions");
        Set<TxnDetailResponse> txnDetailRespons = new HashSet<>();
        {
            TxnDetailResponse txnDetailResponse = new TxnDetailResponse();
            txnDetailResponse.setDisplayOrder(1);
            txnDetailResponse.setNoOfTransactions(5L);
            txnDetailResponse.setNoOfTxns("5");
            txnDetailResponse.setTransactionAmount(new BigDecimal("12"));
            txnDetailResponse.setTxnsAmount(commonUtil.currencyFormat(new BigDecimal("12")));
            txnDetailResponse.setRangeLabel("December");
            txnDetailRespons.add(txnDetailResponse);
        }
        {
            TxnDetailResponse txnDetailResponse = new TxnDetailResponse();
            txnDetailResponse.setDisplayOrder(2);
            txnDetailResponse.setNoOfTransactions(24L);
            txnDetailResponse.setNoOfTxns("24");
            txnDetailResponse.setTransactionAmount(new BigDecimal("10"));
            txnDetailResponse.setTxnsAmount(commonUtil.currencyFormat(new BigDecimal("10")));
            txnDetailResponse.setRangeLabel("January");
            txnDetailRespons.add(txnDetailResponse);
        }
        {
            TxnDetailResponse txnDetailResponse = new TxnDetailResponse();
            txnDetailResponse.setDisplayOrder(3);
            txnDetailResponse.setNoOfTransactions(34L);
            txnDetailResponse.setTransactionAmount(new BigDecimal("20"));
            txnDetailResponse.setTxnsAmount(commonUtil.currencyFormat(new BigDecimal("20")));
            txnDetailResponse.setNoOfTxns("34");
            txnDetailResponse.setRangeLabel("February");
            txnDetailRespons.add(txnDetailResponse);
        }
        {
            TxnDetailResponse txnDetailResponse = new TxnDetailResponse();
            txnDetailResponse.setDisplayOrder(4);
            txnDetailResponse.setNoOfTransactions(32L);
            txnDetailResponse.setNoOfTxns("32");
            txnDetailResponse.setTransactionAmount(new BigDecimal("8"));
            txnDetailResponse.setTxnsAmount(commonUtil.currencyFormat(new BigDecimal("8")));
            txnDetailResponse.setRangeLabel("March");
            txnDetailRespons.add(txnDetailResponse);
        }
        transactionDetailResponse.setTxnDetailResponses(txnDetailRespons);
        return transactionDetailResponse;
    }

    @RequestMapping(path = {"/oauth2/api/dashboard/cash/in/status"}, method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public CashInDetailResponse cashInStatus(@Valid @RequestBody DashboardDTO dashboardDTO) throws Exception {
        CashInDetailResponse cashInDetailResponse = new CashInDetailResponse();
        cashInDetailResponse.setCashInAmountColor("red");
        cashInDetailResponse.setCashInAmountLabel("Amount of Cash In");
        cashInDetailResponse.setNoOfCashInColor("blue");
        cashInDetailResponse.setNoOfCashInLabel("No of Cash In");
        Set<TxnDetailResponse> txnDetailRespons = new HashSet<>();
        {
            TxnDetailResponse txnDetailResponse = new TxnDetailResponse();
            txnDetailResponse.setDisplayOrder(1);
            txnDetailResponse.setNoOfTransactions(2L);
            txnDetailResponse.setNoOfTxns("2");
            txnDetailResponse.setTransactionAmount(new BigDecimal("50"));
            txnDetailResponse.setTxnsAmount(commonUtil.currencyFormat(new BigDecimal("50")));
            txnDetailResponse.setRangeLabel("December");
            txnDetailRespons.add(txnDetailResponse);
        }
        {
            TxnDetailResponse txnDetailResponse = new TxnDetailResponse();
            txnDetailResponse.setDisplayOrder(2);
            txnDetailResponse.setNoOfTransactions(10L);
            txnDetailResponse.setNoOfTxns("10");
            txnDetailResponse.setTransactionAmount(new BigDecimal("150"));
            txnDetailResponse.setTxnsAmount(commonUtil.currencyFormat(new BigDecimal("150")));
            txnDetailResponse.setRangeLabel("January");
            txnDetailRespons.add(txnDetailResponse);
        }
        {
            TxnDetailResponse txnDetailResponse = new TxnDetailResponse();
            txnDetailResponse.setDisplayOrder(3);
            txnDetailResponse.setNoOfTransactions(12L);
            txnDetailResponse.setNoOfTxns("12");
            txnDetailResponse.setTransactionAmount(new BigDecimal("180"));
            txnDetailResponse.setTxnsAmount(commonUtil.currencyFormat(new BigDecimal("180")));
            txnDetailResponse.setRangeLabel("February");
            txnDetailRespons.add(txnDetailResponse);
        }
        {
            TxnDetailResponse txnDetailResponse = new TxnDetailResponse();
            txnDetailResponse.setDisplayOrder(4);
            txnDetailResponse.setNoOfTransactions(20L);
            txnDetailResponse.setNoOfTxns("20");
            txnDetailResponse.setTransactionAmount(new BigDecimal("200"));
            txnDetailResponse.setTxnsAmount(commonUtil.currencyFormat(new BigDecimal("200")));
            txnDetailResponse.setRangeLabel("March");
            txnDetailRespons.add(txnDetailResponse);
        }
        cashInDetailResponse.setTxnDetailResponses(txnDetailRespons);
        return cashInDetailResponse;
    }

    @RequestMapping(path = {"/oauth2/api/dashboard/cash/out/status"}, method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public CashOutDetailResponse cashOutStatus(@Valid @RequestBody DashboardDTO dashboardDTO) throws Exception {
        CashOutDetailResponse cashOutDetailResponse = new CashOutDetailResponse();
        cashOutDetailResponse.setCashOutAmountColor("red");
        cashOutDetailResponse.setCashOutAmountLabel("Amount of Cash Out");
        cashOutDetailResponse.setNoOfCashOutColor("blue");
        cashOutDetailResponse.setNoOfCashOutLabel("No of Cash Out");
        Set<TxnDetailResponse> txnDetailRespons = new HashSet<>();
        {
            TxnDetailResponse txnDetailResponse = new TxnDetailResponse();
            txnDetailResponse.setDisplayOrder(1);
            txnDetailResponse.setNoOfTransactions(10L);
            txnDetailResponse.setNoOfTxns("10");
            txnDetailResponse.setTransactionAmount(new BigDecimal("400"));
            txnDetailResponse.setTxnsAmount(commonUtil.currencyFormat(new BigDecimal("400")));
            txnDetailResponse.setRangeLabel("December");
            txnDetailRespons.add(txnDetailResponse);
        }
        {
            TxnDetailResponse txnDetailResponse = new TxnDetailResponse();
            txnDetailResponse.setDisplayOrder(2);
            txnDetailResponse.setNoOfTransactions(10L);
            txnDetailResponse.setNoOfTxns("10");
            txnDetailResponse.setTransactionAmount(new BigDecimal("300"));
            txnDetailResponse.setTxnsAmount(commonUtil.currencyFormat(new BigDecimal("300")));
            txnDetailResponse.setRangeLabel("January");
            txnDetailRespons.add(txnDetailResponse);
        }
        {
            TxnDetailResponse txnDetailResponse = new TxnDetailResponse();
            txnDetailResponse.setDisplayOrder(3);
            txnDetailResponse.setNoOfTransactions(25L);
            txnDetailResponse.setNoOfTxns("25");
            txnDetailResponse.setTransactionAmount(new BigDecimal("289"));
            txnDetailResponse.setTxnsAmount(commonUtil.currencyFormat(new BigDecimal("289")));
            txnDetailResponse.setRangeLabel("February");
            txnDetailRespons.add(txnDetailResponse);
        }
        {
            TxnDetailResponse txnDetailResponse = new TxnDetailResponse();
            txnDetailResponse.setDisplayOrder(4);
            txnDetailResponse.setNoOfTransactions(12L);
            txnDetailResponse.setNoOfTxns("12");
            txnDetailResponse.setTransactionAmount(new BigDecimal("695"));
            txnDetailResponse.setTxnsAmount(commonUtil.currencyFormat(new BigDecimal("695")));
            txnDetailResponse.setRangeLabel("March");
            txnDetailRespons.add(txnDetailResponse);
        }
        cashOutDetailResponse.setTxnDetailResponses(txnDetailRespons);
        return cashOutDetailResponse;
    }

    @RequestMapping(path = {"/oauth2/api/dashboard/commission/in/status"}, method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public CommissionInDetailResponse commissionInStatus(@Valid @RequestBody DashboardDTO dashboardDTO) throws Exception {
        CommissionInDetailResponse commissionInDetailResponse = new CommissionInDetailResponse();
        commissionInDetailResponse.setCommissionInAmountColor("red");
        commissionInDetailResponse.setCommissionInAmountLabel("Amount of Commission In");
        commissionInDetailResponse.setNoOfCommissionInLabel("blue");
        commissionInDetailResponse.setNoOfCommissionInLabel("No of Commission In");
        Set<TxnDetailResponse> txnDetailRespons = new HashSet<>();
        {
            TxnDetailResponse txnDetailResponse = new TxnDetailResponse();
            txnDetailResponse.setDisplayOrder(1);
            txnDetailResponse.setNoOfTransactions(26L);
            txnDetailResponse.setNoOfTxns("26");
            txnDetailResponse.setTransactionAmount(new BigDecimal("5000"));
            txnDetailResponse.setTxnsAmount(commonUtil.currencyFormat(new BigDecimal("5000")));
            txnDetailResponse.setRangeLabel("December");
            txnDetailRespons.add(txnDetailResponse);
        }
        {
            TxnDetailResponse txnDetailResponse = new TxnDetailResponse();
            txnDetailResponse.setDisplayOrder(2);
            txnDetailResponse.setNoOfTransactions(10L);
            txnDetailResponse.setNoOfTxns("10");
            txnDetailResponse.setTransactionAmount(new BigDecimal("1500"));
            txnDetailResponse.setTxnsAmount(commonUtil.currencyFormat(new BigDecimal("1500")));
            txnDetailResponse.setRangeLabel("January");
            txnDetailRespons.add(txnDetailResponse);
        }
        {
            TxnDetailResponse txnDetailResponse = new TxnDetailResponse();
            txnDetailResponse.setDisplayOrder(3);
            txnDetailResponse.setNoOfTransactions(16L);
            txnDetailResponse.setNoOfTxns("16");
            txnDetailResponse.setTransactionAmount(new BigDecimal("1808"));
            txnDetailResponse.setTxnsAmount(commonUtil.currencyFormat(new BigDecimal("1808")));
            txnDetailResponse.setRangeLabel("February");
            txnDetailRespons.add(txnDetailResponse);
        }
        {
            TxnDetailResponse txnDetailResponse = new TxnDetailResponse();
            txnDetailResponse.setDisplayOrder(4);
            txnDetailResponse.setNoOfTransactions(20L);
            txnDetailResponse.setNoOfTxns("20");
            txnDetailResponse.setTransactionAmount(new BigDecimal("2080"));
            txnDetailResponse.setTxnsAmount(commonUtil.currencyFormat(new BigDecimal("2080")));
            txnDetailResponse.setRangeLabel("March");
            txnDetailRespons.add(txnDetailResponse);
        }
        commissionInDetailResponse.setTxnDetailResponses(txnDetailRespons);
        return commissionInDetailResponse;
    }

    @RequestMapping(path = {"/oauth2/api/dashboard/commission/out/status"}, method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public CommissionOutDetailResponse commissionOutStatus(@Valid @RequestBody DashboardDTO dashboardDTO) throws Exception {
        CommissionOutDetailResponse commissionOutDetailResponse = new CommissionOutDetailResponse();
        commissionOutDetailResponse.setCommissionOutAmountColor("red");
        commissionOutDetailResponse.setCommissionOutAmountLabel("Amount of Commission Out");
        commissionOutDetailResponse.setNoOfCommissionOutColor("blue");
        commissionOutDetailResponse.setNoOfCommissionOutLabel("No of Commission Out");
        Set<TxnDetailResponse> txnDetailRespons = new HashSet<>();
        {
            TxnDetailResponse txnDetailResponse = new TxnDetailResponse();
            txnDetailResponse.setDisplayOrder(1);
            txnDetailResponse.setNoOfTransactions(10L);
            txnDetailResponse.setNoOfTxns("10");
            txnDetailResponse.setTransactionAmount(new BigDecimal("4010"));
            txnDetailResponse.setTxnsAmount(commonUtil.currencyFormat(new BigDecimal("4010")));
            txnDetailResponse.setRangeLabel("December");
            txnDetailRespons.add(txnDetailResponse);
        }
        {
            TxnDetailResponse txnDetailResponse = new TxnDetailResponse();
            txnDetailResponse.setDisplayOrder(2);
            txnDetailResponse.setNoOfTransactions(10L);
            txnDetailResponse.setNoOfTxns("10");
            txnDetailResponse.setTransactionAmount(new BigDecimal("3010"));
            txnDetailResponse.setTxnsAmount(commonUtil.currencyFormat(new BigDecimal("3010")));
            txnDetailResponse.setRangeLabel("January");
            txnDetailRespons.add(txnDetailResponse);
        }
        {
            TxnDetailResponse txnDetailResponse = new TxnDetailResponse();
            txnDetailResponse.setDisplayOrder(3);
            txnDetailResponse.setNoOfTransactions(25L);
            txnDetailResponse.setNoOfTxns("25");
            txnDetailResponse.setTransactionAmount(new BigDecimal("2819"));
            txnDetailResponse.setTxnsAmount(commonUtil.currencyFormat(new BigDecimal("2819")));
            txnDetailResponse.setRangeLabel("February");
            txnDetailRespons.add(txnDetailResponse);
        }
        {
            TxnDetailResponse txnDetailResponse = new TxnDetailResponse();
            txnDetailResponse.setDisplayOrder(4);
            txnDetailResponse.setNoOfTransactions(12L);
            txnDetailResponse.setNoOfTxns("12");
            txnDetailResponse.setTransactionAmount(new BigDecimal("6915"));
            txnDetailResponse.setTxnsAmount(commonUtil.currencyFormat(new BigDecimal("6915")));
            txnDetailResponse.setRangeLabel("March");
            txnDetailRespons.add(txnDetailResponse);
        }
        commissionOutDetailResponse.setTxnDetailResponses(txnDetailRespons);
        return commissionOutDetailResponse;
    }

    @RequestMapping(path = {"/oauth2/api/dashboard/top/five/merchant"}, method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<TopFiveMerchantDetailResponse> top5Merchant(@Valid @RequestBody DashboardDTO dashboardDTO) throws Exception {


        TopFiveMerchantResponse topFiveMerchantResponse = new TopFiveMerchantResponse();
        List<TopFiveMerchantDetailResponse> merchantDetailResponseList = dashBoardService.topFiveMerchants(dashboardDTO);
        if (Validation.isValidList(merchantDetailResponseList)) {
            topFiveMerchantResponse.setMerchantDetailResponse(merchantDetailResponseList);
            topFiveMerchantResponse.setResponseCode(SUCCESS_RESPONSE_CODE);
            topFiveMerchantResponse.setResponseMessage(translator.toLocale("message.success"));
        } else {
            topFiveMerchantResponse.setResponseCode(FAILURE_RESPONSE_CODE);
            topFiveMerchantResponse.setResponseMessage(translator.toLocale("message.failed"));
        }
        return merchantDetailResponseList;
    }

    @RequestMapping(path = {"/oauth2/api/dashboard/top/five/vas"}, method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public TopFiveVASResponse top5VAS(@Valid @RequestBody DashboardDTO dashboardDTO) throws Exception {
        TopFiveVASResponse topFiveVASResponse = new TopFiveVASResponse();
        topFiveVASResponse.setVASCodeLabel("Code");
        topFiveVASResponse.setVASNameLabel("VAS");
        topFiveVASResponse.setVASNoOfTxnLabel("# Transactions");
        topFiveVASResponse.setVASTotalAmountLabel("Amount");
        Set<TopFiveVASDetailResponse> topFiveVASDetailResponses = new HashSet<>();
        {
            TopFiveVASDetailResponse topFiveVASDetailResponse = new TopFiveVASDetailResponse();
            topFiveVASDetailResponse.setDisplayOrder(1);
            topFiveVASDetailResponse.setVASCode("vas0989");
            topFiveVASDetailResponse.setVASName("Recharge");
            topFiveVASDetailResponse.setVASNoOfTxn("2540");
            topFiveVASDetailResponse.setVASTotalAmount(commonUtil.currencyFormat(new BigDecimal("4010950")));
            topFiveVASDetailResponses.add(topFiveVASDetailResponse);
        }
        {
            TopFiveVASDetailResponse topFiveVASDetailResponse = new TopFiveVASDetailResponse();
            topFiveVASDetailResponse.setDisplayOrder(2);
            topFiveVASDetailResponse.setVASCode("gard0989");
            topFiveVASDetailResponse.setVASName("DTH");
            topFiveVASDetailResponse.setVASNoOfTxn("2510");
            topFiveVASDetailResponse.setVASTotalAmount(commonUtil.currencyFormat(new BigDecimal("4014450")));
            topFiveVASDetailResponses.add(topFiveVASDetailResponse);
        }
        {
            TopFiveVASDetailResponse topFiveVASDetailResponse = new TopFiveVASDetailResponse();
            topFiveVASDetailResponse.setDisplayOrder(1);
            topFiveVASDetailResponse.setVASCode("vas04343");
            topFiveVASDetailResponse.setVASName("DTH");
            topFiveVASDetailResponse.setVASNoOfTxn("2500");
            topFiveVASDetailResponse.setVASTotalAmount(commonUtil.currencyFormat(new BigDecimal("4000950")));
            topFiveVASDetailResponses.add(topFiveVASDetailResponse);
        }
        {
            TopFiveVASDetailResponse topFiveVASDetailResponse = new TopFiveVASDetailResponse();
            topFiveVASDetailResponse.setDisplayOrder(1);
            topFiveVASDetailResponse.setVASCode("dads0989");
            topFiveVASDetailResponse.setVASName("Recharge");
            topFiveVASDetailResponse.setVASNoOfTxn("2450");
            topFiveVASDetailResponse.setVASTotalAmount(commonUtil.currencyFormat(new BigDecimal("40100950")));
            topFiveVASDetailResponses.add(topFiveVASDetailResponse);
        }
        {
            TopFiveVASDetailResponse topFiveVASDetailResponse = new TopFiveVASDetailResponse();
            topFiveVASDetailResponse.setDisplayOrder(1);
            topFiveVASDetailResponse.setVASCode("haded0989");
            topFiveVASDetailResponse.setVASName("Broadband");
            topFiveVASDetailResponse.setVASNoOfTxn("2320");
            topFiveVASDetailResponse.setVASTotalAmount(commonUtil.currencyFormat(new BigDecimal("2017450")));
            topFiveVASDetailResponses.add(topFiveVASDetailResponse);
        }
        topFiveVASResponse.setTxnDetailResponses(topFiveVASDetailResponses);
        return topFiveVASResponse;
    }


    @RequestMapping(value = "oauth2/api/dashboard/recent/fund/transfers", method = RequestMethod.POST, produces = "application/json")
    public RecentFundTransfers recentFundTransfer(@RequestBody GetRecentFundTransferRequest getRecentFundTransferRequest) {
        return dashBoardService.getRecentFundTransfer(getRecentFundTransferRequest.getPageNo(), getRecentFundTransferRequest.getPageSize());
    }

    @RequestMapping(value = "oauth2/api/dashboard/total/fund/receive", method = RequestMethod.GET, produces = "application/json")
    public TotalReceiveAmount totalAmountReceive() {
        return dashBoardService.totalAmountReceive();
    }


    @RequestMapping(value = "oauth2/api/dashboard/total/fund/spent", method = RequestMethod.GET, produces = "application/json")
    public TotalReceiveAmount totalSpentAmount() {
        return dashBoardService.totalSpentAmount();
    }
}

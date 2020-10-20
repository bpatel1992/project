package com.rahul.project.gateway.crud.bean;

import java.util.List;

/**
 * Created by mannindia on 24/11/16.
 */
public class DailyDashboardBean {

    private int newMerchantAddedCount;
    private int newPosnodesActivated;
    private int newMerchantAddedCountMonth;
    private int newPosnodesActivatedMonth;
    private String intialdate;
    private String fromDate;
    private String nextDate;
    private int abandonedMerchantsTillDate;
    private float activationFeeCollected;
    private float activationFeeCollectedMonth;
    private float monthlyFeeCollected;
    private float monthlyFeeCollectedMonth;
    private int noOfTransactions;
    private int noOfTransactionsMonth;
    private int noOfMposTransactions;
    private int noOfMposTransactionsMonth;
    private int noOfSMSTransactions;
    private int noOfSMSTransactionsMonth;
    private float amountOfTransactions;
    private float amountOfTransactionsMonth;
    private float amountOfMposTransactions;
    private float amountOfMposTransactionsMonth;
    private float amountOfSMSTransactions;
    private float amountOfSMSTransactionsMonth;
    private float averageValueOfTransactions;
    private float averageValueOfTransactionsMonth;
    private float averageValueOfMposTransactions;
    private float averageValueOfMposTransactionsMonth;
    private float averageValueOfSMSTransactions;
    private float averageValueOfSMSTransactionsMonth;
    private float TDRCollected;
    private float TDRCollectedMonth;
    private float smsTransactionPercent;
    private float smsTransactionPercentMonth;
    private float gatewayCommission;
    private float gatewayCommissionMonth;
    private float mpaygoCommission;
    private float mpaygoCommissionMonth;
    private float totalAmount;
    private float totalAmountMonth;
    private int onBoardingAttempted;
    private int onBoardingAttemptedMonth;
    private int totalMerchants;
    private int totalNoOfadditionalPOS;
    private int totalNoOfPOS;
    private int totalNoOfTransaction;
    private float totalValueOfTransaction;
    private float totalEarning;
    private List companyName;
    private List noOfPos;
    private List<Double> amount;
    private List inactivecompanyName;
    private List inactivenoOfPos;
    private List<Double> inactiveamount;
    private int noOfFailedTransaction;
    private int noOfPendingTransaction;
    private int noOfFailedTransactionMonth;
    private int noOfPendingTransactionMonth;


    public int getNewPosnodesActivated() {
        return newPosnodesActivated;
    }

    public void setNewPosnodesActivated(int newPosnodesActivated) {
        this.newPosnodesActivated = newPosnodesActivated;
    }

    public int getNewMerchantAddedCountMonth() {
        return newMerchantAddedCountMonth;
    }

    public void setNewMerchantAddedCountMonth(int newMerchantAddedCountMonth) {
        this.newMerchantAddedCountMonth = newMerchantAddedCountMonth;
    }

    public int getNewPosnodesActivatedMonth() {
        return newPosnodesActivatedMonth;
    }

    public void setNewPosnodesActivatedMonth(int newPosnodesActivatedMonth) {
        this.newPosnodesActivatedMonth = newPosnodesActivatedMonth;
    }

    public int getNewMerchantAddedCount() {
        return newMerchantAddedCount;
    }

    public void setNewMerchantAddedCount(int newMerchantAddedCount) {
        this.newMerchantAddedCount = newMerchantAddedCount;
    }

    public String getIntialdate() {
        return intialdate;
    }

    public void setIntialdate(String intialdate) {
        this.intialdate = intialdate;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public int getAbandonedMerchantsTillDate() {
        return abandonedMerchantsTillDate;
    }

    public void setAbandonedMerchantsTillDate(int abandonedMerchantsTillDate) {
        this.abandonedMerchantsTillDate = abandonedMerchantsTillDate;
    }


    public String getNextDate() {
        return nextDate;
    }

    public void setNextDate(String nextDate) {
        this.nextDate = nextDate;
    }

    public float getActivationFeeCollected() {
        return activationFeeCollected;
    }

    public void setActivationFeeCollected(float activationFeeCollected) {
        this.activationFeeCollected = activationFeeCollected;
    }

    public float getActivationFeeCollectedMonth() {
        return activationFeeCollectedMonth;
    }

    public void setActivationFeeCollectedMonth(float activationFeeCollectedMonth) {
        this.activationFeeCollectedMonth = activationFeeCollectedMonth;
    }

    public float getMonthlyFeeCollected() {
        return monthlyFeeCollected;
    }

    public void setMonthlyFeeCollected(float monthlyFeeCollected) {
        this.monthlyFeeCollected = monthlyFeeCollected;
    }

    public float getMonthlyFeeCollectedMonth() {
        return monthlyFeeCollectedMonth;
    }

    public void setMonthlyFeeCollectedMonth(float monthlyFeeCollectedMonth) {
        this.monthlyFeeCollectedMonth = monthlyFeeCollectedMonth;
    }

    public int getNoOfTransactions() {
        return noOfTransactions;
    }

    public void setNoOfTransactions(int noOfTransactions) {
        this.noOfTransactions = noOfTransactions;
    }

    public int getNoOfTransactionsMonth() {
        return noOfTransactionsMonth;
    }

    public void setNoOfTransactionsMonth(int noOfTransactionsMonth) {
        this.noOfTransactionsMonth = noOfTransactionsMonth;
    }

    public int getNoOfMposTransactions() {
        return noOfMposTransactions;
    }

    public void setNoOfMposTransactions(int noOfMposTransactions) {
        this.noOfMposTransactions = noOfMposTransactions;
    }

    public int getNoOfMposTransactionsMonth() {
        return noOfMposTransactionsMonth;
    }

    public void setNoOfMposTransactionsMonth(int noOfMposTransactionsMonth) {
        this.noOfMposTransactionsMonth = noOfMposTransactionsMonth;
    }

    public int getNoOfSMSTransactions() {
        return noOfSMSTransactions;
    }

    public void setNoOfSMSTransactions(int noOfSMSTransactions) {
        this.noOfSMSTransactions = noOfSMSTransactions;
    }

    public int getNoOfSMSTransactionsMonth() {
        return noOfSMSTransactionsMonth;
    }

    public void setNoOfSMSTransactionsMonth(int noOfSMSTransactionsMonth) {
        this.noOfSMSTransactionsMonth = noOfSMSTransactionsMonth;
    }


    public float getAmountOfTransactions() {
        return amountOfTransactions;
    }

    public void setAmountOfTransactions(float amountOfTransactions) {
        this.amountOfTransactions = amountOfTransactions;
    }

    public float getAmountOfTransactionsMonth() {
        return amountOfTransactionsMonth;
    }

    public void setAmountOfTransactionsMonth(float amountOfTransactionsMonth) {
        this.amountOfTransactionsMonth = amountOfTransactionsMonth;
    }

    public float getAmountOfMposTransactions() {
        return amountOfMposTransactions;
    }

    public void setAmountOfMposTransactions(float amountOfMposTransactions) {
        this.amountOfMposTransactions = amountOfMposTransactions;
    }

    public float getAmountOfMposTransactionsMonth() {
        return amountOfMposTransactionsMonth;
    }

    public void setAmountOfMposTransactionsMonth(float amountOfMposTransactionsMonth) {
        this.amountOfMposTransactionsMonth = amountOfMposTransactionsMonth;
    }

    public float getAmountOfSMSTransactions() {
        return amountOfSMSTransactions;
    }

    public void setAmountOfSMSTransactions(float amountOfSMSTransactions) {
        this.amountOfSMSTransactions = amountOfSMSTransactions;
    }

    public float getAmountOfSMSTransactionsMonth() {
        return amountOfSMSTransactionsMonth;
    }

    public void setAmountOfSMSTransactionsMonth(float amountOfSMSTransactionsMonth) {
        this.amountOfSMSTransactionsMonth = amountOfSMSTransactionsMonth;
    }

    public float getAverageValueOfTransactions() {
        return averageValueOfTransactions;
    }

    public void setAverageValueOfTransactions(float averageValueOfTransactions) {
        this.averageValueOfTransactions = averageValueOfTransactions;
    }

    public float getAverageValueOfTransactionsMonth() {
        return averageValueOfTransactionsMonth;
    }

    public void setAverageValueOfTransactionsMonth(float averageValueOfTransactionsMonth) {
        this.averageValueOfTransactionsMonth = averageValueOfTransactionsMonth;
    }

    public float getAverageValueOfMposTransactions() {
        return averageValueOfMposTransactions;
    }

    public void setAverageValueOfMposTransactions(float averageValueOfMposTransactions) {
        this.averageValueOfMposTransactions = averageValueOfMposTransactions;
    }

    public float getAverageValueOfMposTransactionsMonth() {
        return averageValueOfMposTransactionsMonth;
    }

    public void setAverageValueOfMposTransactionsMonth(float averageValueOfMposTransactionsMonth) {
        this.averageValueOfMposTransactionsMonth = averageValueOfMposTransactionsMonth;
    }

    public float getAverageValueOfSMSTransactions() {
        return averageValueOfSMSTransactions;
    }

    public void setAverageValueOfSMSTransactions(float averageValueOfSMSTransactions) {
        this.averageValueOfSMSTransactions = averageValueOfSMSTransactions;
    }

    public float getAverageValueOfSMSTransactionsMonth() {
        return averageValueOfSMSTransactionsMonth;
    }

    public void setAverageValueOfSMSTransactionsMonth(float averageValueOfSMSTransactionsMonth) {
        this.averageValueOfSMSTransactionsMonth = averageValueOfSMSTransactionsMonth;
    }

    public float getTDRCollected() {
        return TDRCollected;
    }

    public void setTDRCollected(float TDRCollected) {
        this.TDRCollected = TDRCollected;
    }

    public float getTDRCollectedMonth() {
        return TDRCollectedMonth;
    }

    public void setTDRCollectedMonth(float TDRCollectedMonth) {
        this.TDRCollectedMonth = TDRCollectedMonth;
    }

    public float getSmsTransactionPercent() {
        return smsTransactionPercent;
    }

    public void setSmsTransactionPercent(float smsTransactionPercent) {
        this.smsTransactionPercent = smsTransactionPercent;
    }

    public float getSmsTransactionPercentMonth() {
        return smsTransactionPercentMonth;
    }

    public void setSmsTransactionPercentMonth(float smsTransactionPercentMonth) {
        this.smsTransactionPercentMonth = smsTransactionPercentMonth;
    }

    public float getGatewayCommission() {
        return gatewayCommission;
    }

    public void setGatewayCommission(float gatewayCommission) {
        this.gatewayCommission = gatewayCommission;
    }

    public float getGatewayCommissionMonth() {
        return gatewayCommissionMonth;
    }

    public void setGatewayCommissionMonth(float gatewayCommissionMonth) {
        this.gatewayCommissionMonth = gatewayCommissionMonth;
    }

    public float getMpaygoCommission() {
        return mpaygoCommission;
    }

    public void setMpaygoCommission(float mpaygoCommission) {
        this.mpaygoCommission = mpaygoCommission;
    }

    public float getMpaygoCommissionMonth() {
        return mpaygoCommissionMonth;
    }

    public void setMpaygoCommissionMonth(float mpaygoCommissionMonth) {
        this.mpaygoCommissionMonth = mpaygoCommissionMonth;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public float getTotalAmountMonth() {
        return totalAmountMonth;
    }

    public void setTotalAmountMonth(float totalAmountMonth) {
        this.totalAmountMonth = totalAmountMonth;
    }

    public int getOnBoardingAttempted() {
        return onBoardingAttempted;
    }

    public void setOnBoardingAttempted(int onBoardingAttempted) {
        this.onBoardingAttempted = onBoardingAttempted;
    }

    public int getOnBoardingAttemptedMonth() {
        return onBoardingAttemptedMonth;
    }

    public void setOnBoardingAttemptedMonth(int onBoardingAttemptedMonth) {
        this.onBoardingAttemptedMonth = onBoardingAttemptedMonth;
    }


    public int getTotalNoOfadditionalPOS() {
        return totalNoOfadditionalPOS;
    }

    public void setTotalNoOfadditionalPOS(int totalNoOfadditionalPOS) {
        this.totalNoOfadditionalPOS = totalNoOfadditionalPOS;
    }

    public int getTotalNoOfPOS() {
        return totalNoOfPOS;
    }

    public void setTotalNoOfPOS(int totalNoOfPOS) {
        this.totalNoOfPOS = totalNoOfPOS;
    }

    public int getTotalNoOfTransaction() {
        return totalNoOfTransaction;
    }

    public void setTotalNoOfTransaction(int totalNoOfTransaction) {
        this.totalNoOfTransaction = totalNoOfTransaction;
    }

    public float getTotalValueOfTransaction() {
        return totalValueOfTransaction;
    }

    public void setTotalValueOfTransaction(float totalValueOfTransaction) {
        this.totalValueOfTransaction = totalValueOfTransaction;
    }

    public int getTotalMerchants() {
        return totalMerchants;
    }

    public void setTotalMerchants(int totalMerchants) {
        this.totalMerchants = totalMerchants;
    }

    public float getTotalEarning() {
        return totalEarning;
    }

    public void setTotalEarning(float totalEarning) {
        this.totalEarning = totalEarning;
    }

    public List getCompanyName() {
        return companyName;
    }

    public void setCompanyName(List companyName) {
        this.companyName = companyName;
    }

    public List getNoOfPos() {
        return noOfPos;
    }

    public void setNoOfPos(List noOfPos) {
        this.noOfPos = noOfPos;
    }

    public List<Double> getAmount() {
        return amount;
    }

    public void setAmount(List<Double> amount) {
        this.amount = amount;
    }

    public List getInactivecompanyName() {
        return inactivecompanyName;
    }

    public void setInactivecompanyName(List inactivecompanyName) {
        this.inactivecompanyName = inactivecompanyName;
    }

    public List getInactivenoOfPos() {
        return inactivenoOfPos;
    }

    public void setInactivenoOfPos(List inactivenoOfPos) {
        this.inactivenoOfPos = inactivenoOfPos;
    }

    public List<Double> getInactiveamount() {
        return inactiveamount;
    }

    public void setInactiveamount(List<Double> inactiveamount) {
        this.inactiveamount = inactiveamount;
    }

    public int getNoOfFailedTransaction() {
        return noOfFailedTransaction;
    }

    public void setNoOfFailedTransaction(int noOfFailedTransaction) {
        this.noOfFailedTransaction = noOfFailedTransaction;
    }

    public int getNoOfPendingTransaction() {
        return noOfPendingTransaction;
    }

    public void setNoOfPendingTransaction(int noOfPendingTransaction) {
        this.noOfPendingTransaction = noOfPendingTransaction;
    }

    public int getNoOfFailedTransactionMonth() {
        return noOfFailedTransactionMonth;
    }

    public void setNoOfFailedTransactionMonth(int noOfFailedTransactionMonth) {
        this.noOfFailedTransactionMonth = noOfFailedTransactionMonth;
    }

    public int getNoOfPendingTransactionMonth() {
        return noOfPendingTransactionMonth;
    }

    public void setNoOfPendingTransactionMonth(int noOfPendingTransactionMonth) {
        this.noOfPendingTransactionMonth = noOfPendingTransactionMonth;
    }
}

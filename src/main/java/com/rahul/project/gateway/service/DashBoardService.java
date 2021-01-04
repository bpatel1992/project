package com.rahul.project.gateway.service;


import com.rahul.project.gateway.dto.*;

import java.util.List;
import java.util.Set;

public interface DashBoardService {

    TxnSummary getTxnSummary() throws Exception;

    TotalDashboardSummaryResponse getTotalDashboardSummary();

    Set<WeeklySaleDashboardDataResponse> getDailySaleData();

    Set<WeeklyEarningDashboardDataResponse> getWeeklyEarningData();

    RecentFundTransfers getRecentFundTransfer(int pageNo, int pageSize);

    TotalReceiveAmount totalAmountReceive();

    List<TopFiveMerchantDetailResponse> topFiveMerchants(DashboardDTO dashboardDTO);

    TotalReceiveAmount totalSpentAmount();
}

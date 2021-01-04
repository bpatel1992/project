package com.rahul.project.gateway.dao;

import com.rahul.project.gateway.dto.RecentFundTransfers;

import java.math.BigDecimal;
import java.util.List;

public interface DashboardDao {

    List<Object> getTxnSummary() throws Exception;

    BigDecimal getTotalLibality() throws Exception;

    RecentFundTransfers getRecentFundTransfer(Long id, int pageNo, int pageSize);

}

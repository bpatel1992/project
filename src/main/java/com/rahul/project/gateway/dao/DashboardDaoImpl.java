package com.rahul.project.gateway.dao;

import com.rahul.project.gateway.configuration.annotations.RepositoryDao;
import com.rahul.project.gateway.dto.RecentFundTransferDetails;
import com.rahul.project.gateway.dto.RecentFundTransfers;
import com.rahul.project.gateway.model.Transaction;
import com.rahul.project.gateway.model.Transfer;
import com.rahul.project.gateway.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RepositoryDao
public class DashboardDaoImpl implements DashboardDao {

    private static final Logger logger = LoggerFactory.getLogger(DashboardDaoImpl.class);
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AbstractDao abstractDao;

    public List<Object> getTxnSummary() throws Exception {

        return transactionRepository.getTxnSummary();
    }

    public BigDecimal getTotalLibality() throws Exception {
        BigDecimal result = BigDecimal.ZERO;

        try {
            CriteriaBuilder cb = abstractDao.getSession().getCriteriaBuilder();
            CriteriaQuery<BigDecimal> query = cb.createQuery(BigDecimal.class);
            Root<Transfer> transferTable = query.from(Transfer.class);
            Join<Transfer, Transaction> transactionJoin = transferTable.join("transaction", JoinType.INNER);
            List<String> inStatus = Arrays.asList("S");
            Expression<String> parentExpression = transactionJoin.get("status");
            Predicate inPredicate = parentExpression.in(inStatus);

            List<Predicate> conditions = new ArrayList<>();
            conditions.add(inPredicate);
            conditions.add(cb.isFalse(transactionJoin.get("isApproved")));
            query.select(cb.sum(transferTable.get("amount")));

            conditions.add(cb.equal(transferTable.get("requestType"), "CR"));
            query.where(conditions.stream().toArray(Predicate[]::new));
            TypedQuery<BigDecimal> typedQuery = abstractDao.getSession().createQuery(query);

            result = typedQuery.getSingleResult();
            logger.info("Size==============" + result);


        } catch (Exception e) {
            logger.error("Exception ", e);
            throw e;
        }
        return result;
    }

    @Override
    public RecentFundTransfers getRecentFundTransfer(Long id, int pageNo, int pageSize) {
        RecentFundTransfers recentFundTransfers = new RecentFundTransfers();
        List<RecentFundTransferDetails> list = new ArrayList<>();
        RecentFundTransferDetails recentFundTransferDetails;
        recentFundTransfers.setResponseCode("5555");
        recentFundTransfers.setResponseMessage("failed");

        try {
            Pageable pageRequest;
            pageRequest = PageRequest.of(pageNo, pageSize);


            List<Object> transfers = transactionRepository.getRecentFundTransfers(id, pageRequest);
            if (transfers != null && transfers.size() > 0) {
                for (Object o : transfers) {
                    Object[] obj = (Object[]) o;
                    recentFundTransferDetails = new RecentFundTransferDetails();
                    recentFundTransferDetails.setName(obj[0].toString());
                    recentFundTransferDetails.setId(Long.parseLong(obj[1].toString()));
                    recentFundTransferDetails.setPhone(obj[2].toString());
                    list.add(recentFundTransferDetails);
                }
                recentFundTransfers.setResponseCode("0000");
                recentFundTransfers.setResponseMessage("Success");
                recentFundTransfers.setRecentFundTransferDetailsList(list);
                return recentFundTransfers;
            }
        } catch (Exception e) {
            logger.info(e.toString());
        }
        return recentFundTransfers;
    }

}

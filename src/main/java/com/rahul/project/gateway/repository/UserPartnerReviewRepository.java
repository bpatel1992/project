package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.chat.model.ChatMessage;
import com.rahul.project.gateway.model.UserPartnerReview;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository(value = "UserPartnerReviewRepository")
public interface UserPartnerReviewRepository extends BaseRepository<UserPartnerReview, Long> {

    List<UserPartnerReview> findByPartnerIdAndStatusOrderByCreatedAsc(Long partnerId,String status,Pageable pageable);
}

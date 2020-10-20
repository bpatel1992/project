package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.NewsLetterSubscription;
import org.springframework.stereotype.Repository;

/**
 * NewsLetterSubscription Repository to handle any NewsLetterSubscription related Operations
 *
 * @author Rahul Malhotra
 */
@Repository(value = "NewsLetterSubscriptionRepository")
public interface NewsLetterSubscriptionRepository extends BaseRepository<NewsLetterSubscription, Long> {
    NewsLetterSubscription getByEmail(String email);
}

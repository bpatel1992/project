package com.rahul.project.gateway.repository;

import com.rahul.project.gateway.model.ChatMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface ChatMessageRepository extends CrudRepository<ChatMessage, String> {
    @Query(" FROM"
        + "    ChatMessage m"
        + "  WHERE"
        + "    m.senderId IN (:userIdOne, :userIdTwo)"
        + "  AND"
        + "    m.receiverId IN (:userIdOne, :userIdTwo)"
        + "  ORDER BY"
        + "    m.timeSent"
        + "  DESC")
    public List<ChatMessage> getExistingChatMessages(
            @Param("userIdOne") Long userIdOne, @Param("userIdTwo") Long userIdTwo, Pageable pageable);
}

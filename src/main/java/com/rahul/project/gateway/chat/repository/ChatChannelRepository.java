package com.rahul.project.gateway.chat.repository;

import com.rahul.project.gateway.chat.model.ChatChannel;
import com.rahul.project.gateway.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository(value = "ChatChannelRepository")
public interface ChatChannelRepository extends BaseRepository<ChatChannel, String> {
  @Query(" FROM"
      + "    ChatChannel c"
      + "  WHERE"
      + "    c.senderId IN (:userOneId, :userTwoId) "
      + "  AND"
      + "    c.receiverId IN (:userOneId, :userTwoId)")
  public List<ChatChannel> findExistingChannel(
          @Param("userOneId") Long userOneId, @Param("userTwoId") Long userTwoId);
  
  @Query(" SELECT"
      + "    channelId"
      + "  FROM"
      + "    ChatChannel c"
      + "  WHERE"
      + "    c.senderId IN (:userIdOne, :userIdTwo)"
      + "  AND"
      + "    c.receiverId IN (:userIdOne, :userIdTwo)")
  public String getChannelUuid(
          @Param("userIdOne") Long userIdOne, @Param("userIdTwo") Long userIdTwo);

  @Query(" FROM"
      + "    ChatChannel c"
      + "  WHERE"
      + "    c.channelId IS :uuid")
  public ChatChannel getChannelDetails(@Param("uuid") String uuid);
}
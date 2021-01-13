package com.rahul.project.gateway.video.repository;

import com.rahul.project.gateway.repository.BaseRepository;
import com.rahul.project.gateway.video.model.VideoChannel;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository(value = "VideoChannelRepository")
public interface VideoChannelRepository extends BaseRepository<VideoChannel, String> {

    VideoChannel findByPartnerIdAndUserId(Long partnerId,Long userId);

}

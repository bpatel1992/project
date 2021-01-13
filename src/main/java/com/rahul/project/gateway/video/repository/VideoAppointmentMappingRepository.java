package com.rahul.project.gateway.video.repository;

import com.rahul.project.gateway.repository.BaseRepository;
import com.rahul.project.gateway.video.model.VideoAppointmentMapping;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository(value = "VideoAppointmentMappingRepository")
public interface VideoAppointmentMappingRepository extends BaseRepository<VideoAppointmentMapping, Long> {

    VideoAppointmentMapping findByAppointmentId(Long appointmentId);

}
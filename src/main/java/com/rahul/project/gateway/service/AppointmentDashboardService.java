package com.rahul.project.gateway.service;

import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.dto.CreateAppointmentDto;
import com.rahul.project.gateway.dto.appointmentdashboard.AddressTypeAppointmentDTO;
import com.rahul.project.gateway.dto.appointmentdashboard.AppointmentCardDTO;
import com.rahul.project.gateway.dto.appointmentdashboard.AppointmentDashboardDto;
import com.rahul.project.gateway.model.Appointment;
import com.rahul.project.gateway.model.BusinessTiming;
import com.rahul.project.gateway.model.TimeRange;
import com.rahul.project.gateway.repository.AppointmentRepository;
import com.rahul.project.gateway.repository.UserAddressTimingRepository;
import com.rahul.project.gateway.repository.UserHolidaysRepository;
import com.rahul.project.gateway.repository.UserRepository;
import com.rahul.project.gateway.utility.CommonUtility;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.Converter;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.lang.invoke.MethodHandles;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@TransactionalService
public class AppointmentDashboardService {


    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final CommonUtility commonUtility;
    private final UserAddressTimingRepository userAddressTimingRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserHolidaysRepository userHolidaysRepository;
    /**
     * Converter for converting a string Date Value on the DTO into a Code object with type Date
     */

    public Converter<String, Date> onlyDateConverter = new Converter<String, Date>() {
        public Date convert(MappingContext<String, Date> context) {
            return commonUtility.getOnlyDate(context.getSource());
        }
    };
    public Converter<String, Date> onlyTimeConverter = new Converter<String, Date>() {
        public Date convert(MappingContext<String, Date> context) {
            return commonUtility.getOnlyTime(context.getSource());
        }
    };
    public Converter<Date, String> convertOnlyDateIntoString = new Converter<Date, String>() {
        public String convert(MappingContext<Date, String> context) {
            return commonUtility.convertOnlyDateIntoString(context.getSource());
        }
    };
    public Converter<Date, String> convertOnlyTimeIntoString = new Converter<Date, String>() {
        public String convert(MappingContext<Date, String> context) {
            return commonUtility.convertOnlyTimeIntoString(context.getSource());
        }
    };
    PropertyMap<CreateAppointmentDto, Appointment> appointmentMapping = new PropertyMap<CreateAppointmentDto, Appointment>() {
        protected void configure() {
            map().getAppointmentReason().setId(source.getAppointmentReasonId());
            map().getAppointmentType().setId(source.getAppointmentTypeId());
            map().getAttendant().setId(source.getAttendantId());
            map().getStatusType().setId(source.getStatusTypeId());
            map().getPartner().setId(source.getPartnerId());
            map().getPet().setId(source.getPetId());
            map().getClinic().setId(source.getClinicId());
            map().getCustomer().setId(source.getCustomerId());
            map().getAppointmentRepeat().setId(source.getAppointmentRepeatId());
            using(onlyDateConverter).map(source.getDate()).setAppointmentDate(null);
            using(onlyTimeConverter).map(source.getFromTime()).setAppointmentFromTime(null);
            using(onlyTimeConverter).map(source.getToTime()).setAppointmentToTime(null);
        }
    };
    PropertyMap<Appointment, CreateAppointmentDto> appointmentFieldMapping = new PropertyMap<Appointment, CreateAppointmentDto>() {
        protected void configure() {
            map().setAppointmentReasonId(source.getAppointmentReason().getId());
            map().setAppointmentTypeId(source.getAppointmentType().getId());
            map().setAttendantId(source.getAttendant().getId());
            map().setStatusTypeId(source.getStatusType().getId());
            map().setPartnerId(source.getPartner().getId());
            map().setPetId(source.getPet().getId());
            map().setClinicId(source.getClinic().getId());
            map().setCustomerId(source.getCustomer().getId());
            map().setAppointmentRepeatId(source.getAppointmentRepeat().getId());
            using(convertOnlyDateIntoString).map(source.getAppointmentDate()).setDate(null);
            using(convertOnlyTimeIntoString).map(source.getAppointmentFromTime()).setFromTime(null);
            using(convertOnlyTimeIntoString).map(source.getAppointmentToTime()).setToTime(null);

        }
    };

    public AppointmentDashboardService(UserAddressTimingRepository userAddressTimingRepository,
                                       CommonUtility commonUtility) {
        this.commonUtility = commonUtility;
        this.userAddressTimingRepository = userAddressTimingRepository;
    }

    public AppointmentDashboardDto appointments(AppointmentDashboardDto appointmentDashboardDto) throws Exception {
        Date appointmentDate = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(appointmentDashboardDto.getDate());

        List<Appointment> appointmentList =
                appointmentRepository.appointmentListByDate(appointmentDashboardDto.getAttendantId(), appointmentDate);
        if (CollectionUtils.isEmpty(appointmentList)) {
            logger.info("No appointments scheduled for attendantId");
            return appointmentDashboardDto;
        }
        List<Appointment> pendindAppointments = appointmentList.stream().filter(appointment ->
                appointment.getStatusType().getStatusTypeName().equals(Appointment.AppointmentStatus.PENDING.name()))
                .collect(Collectors.toList());
        appointmentDashboardDto.setTotalAppointments(appointmentList.size());
        appointmentDashboardDto.setTotalPendingAppointments(pendindAppointments.size());
        fetchHospitalAppointments(appointmentList, appointmentDashboardDto);
        fetchClinicAppointments(appointmentList, appointmentDashboardDto);
        fetchOnlineConsultationAppointments(appointmentList, appointmentDashboardDto);
        fetchBookingMeter(appointmentList, appointmentDashboardDto, appointmentDate);

        return appointmentDashboardDto;
    }

    public AppointmentDashboardDto upcomingAppointments(AppointmentDashboardDto appointmentDashboardDto) throws Exception {
        Date appointmentDate = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(appointmentDashboardDto.getDate());
        List<Appointment> appointmentList =
                appointmentRepository.appointmentListByDate(appointmentDashboardDto.getAttendantId(), appointmentDate);
        if (CollectionUtils.isEmpty(appointmentList)) {
            logger.info("No appointments scheduled for attendantId");
            return appointmentDashboardDto;
        }
        fetchUpcomingAppointments(appointmentList, appointmentDashboardDto, appointmentDate);
        return appointmentDashboardDto;
    }

    public AppointmentDashboardDto liveAppointments(AppointmentDashboardDto appointmentDashboardDto) throws Exception {
        Date appointmentDate = new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(appointmentDashboardDto.getDate());
        List<Appointment> appointmentList =
                appointmentRepository.appointmentListByDate(appointmentDashboardDto.getAttendantId(), appointmentDate);
        if (CollectionUtils.isEmpty(appointmentList)) {
            logger.info("No appointments scheduled for attendantId");
            return appointmentDashboardDto;
        }
        fetchPendingAppointments(appointmentList, appointmentDashboardDto, appointmentDate);
        return appointmentDashboardDto;
    }


    private void fetchHospitalAppointments(List<Appointment> appointmentList,
                                           AppointmentDashboardDto appointmentDashboardDto) {
        List<AddressTypeAppointmentDTO> hAppointments = appointmentList.stream().filter(appointment ->
                StringUtils.equalsAnyIgnoreCase(appointment.getClinic().getName(), "hospital"))
                .map(appointment -> {
                    AddressTypeAppointmentDTO addressTypeAppointmentDTO = new AddressTypeAppointmentDTO();
                    addressTypeAppointmentDTO.setAddressTypeId(appointment.getClinic().getAddressType().getId());
                    addressTypeAppointmentDTO.setAddressTypeName(appointment.getClinic().getAddressType().getAddressType());
                    return addressTypeAppointmentDTO;
                }).collect(Collectors.toList());
        appointmentDashboardDto.setHAppointments(hAppointments);
    }

    private void fetchClinicAppointments(List<Appointment> appointmentList,
                                         AppointmentDashboardDto appointmentDashboardDto) {
        List<AddressTypeAppointmentDTO> hAppointments = appointmentList.stream().filter(appointment ->
                StringUtils.equalsAnyIgnoreCase(appointment.getClinic().getName(), "clinic"))
                .map(appointment -> {
                    AddressTypeAppointmentDTO addressTypeAppointmentDTO = new AddressTypeAppointmentDTO();
                    addressTypeAppointmentDTO.setAddressTypeId(appointment.getClinic().getAddressType().getId());
                    addressTypeAppointmentDTO.setAddressTypeName(appointment.getClinic().getAddressType().getAddressType());
                    return addressTypeAppointmentDTO;
                }).collect(Collectors.toList());
        appointmentDashboardDto.setCAppointments(hAppointments);
    }

    private void fetchOnlineConsultationAppointments(List<Appointment> appointmentList,
                                                     AppointmentDashboardDto appointmentDashboardDto) {
        List<AddressTypeAppointmentDTO> hAppointments = appointmentList.stream().filter(appointment ->
                StringUtils.equalsAnyIgnoreCase(appointment.getClinic().getName(), "Online Consultation"))
                .map(appointment -> {
                    AddressTypeAppointmentDTO addressTypeAppointmentDTO = new AddressTypeAppointmentDTO();
                    addressTypeAppointmentDTO.setAddressTypeId(appointment.getClinic().getAddressType().getId());
                    addressTypeAppointmentDTO.setAddressTypeName(appointment.getClinic().getAddressType().getAddressType());
                    return addressTypeAppointmentDTO;
                }).collect(Collectors.toList());
        appointmentDashboardDto.setOcAppointments(hAppointments);
    }

    private void fetchBookingMeter(List<Appointment> appointmentList,
                                   AppointmentDashboardDto appointmentDashboardDto, Date date) {
        Calendar time = Calendar.getInstance();
        time.setTime(date);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(time.toInstant(), time.getTimeZone().toZoneId());
        int day = localDateTime.getDayOfWeek().getValue();
        Set<BusinessTiming> businessTimings = userAddressTimingRepository.businessTimingsByUserId(appointmentDashboardDto.getAttendantId(),
                String.valueOf(day));
        if(!CollectionUtils.isEmpty(businessTimings)) {
            int totalBusinessTimingsInMinutes = businessTimings.stream()
                    .mapToInt(businessTiming -> {
                        int minutes = 0;
                        List<TimeRange> ranges = new ArrayList<>(businessTiming.getTimeRange());
                        ranges.sort(Comparator.comparing(TimeRange::getDisplayOrder));
                        for (TimeRange timeRange : ranges) {
                            time.set(Calendar.HOUR_OF_DAY, timeRange.getFromHours());
                            time.set(Calendar.MINUTE, timeRange.getFromMinutes());
                            LocalTime fromTime = LocalTime.parse(
                                    (timeRange.getFromHours() > 9 ? timeRange.getFromHours() : "0" + timeRange.getFromHours()) +
                                            ":" +
                                            (timeRange.getFromMinutes() > 9 ? timeRange.getFromMinutes() : "0" + timeRange.getFromMinutes()));
                            LocalTime toTime = LocalTime.parse(
                                    (timeRange.getToHours() > 9 ? timeRange.getToHours() : "0" + timeRange.getToHours()) +
                                            ":" +
                                            (timeRange.getToMinutes() > 9 ? timeRange.getToMinutes() : "0" + timeRange.getToMinutes()));
                            minutes = minutes + Integer.parseInt(String.valueOf(TimeUnit.SECONDS.toMinutes(Duration.between(fromTime, toTime).getSeconds())));
                        }
                        return minutes;
                    }).sum();

            int totalAppointmentTimeInMinutes = appointmentList.stream()
                    .mapToInt(appointment -> {
                        int minutes = 0;
                        Calendar fromTimeCal = Calendar.getInstance();
                        fromTimeCal.setTime(appointment.getAppointmentFromTime());
                        Calendar toTimeCal = Calendar.getInstance();
                        toTimeCal.setTime(appointment.getAppointmentToTime());
                        LocalTime fromTime = LocalDateTime.ofInstant(fromTimeCal.toInstant(), fromTimeCal.getTimeZone().toZoneId()).toLocalTime();
                        LocalTime toTime = LocalDateTime.ofInstant(toTimeCal.toInstant(), toTimeCal.getTimeZone().toZoneId()).toLocalTime();
                        minutes = Integer.parseInt(String.valueOf(TimeUnit.SECONDS.toMinutes(Duration.between(fromTime, toTime).getSeconds())));
                        return minutes;
                    }).sum();
            double bookingMeter = 0;
            if (totalBusinessTimingsInMinutes > 0) {
                bookingMeter = totalAppointmentTimeInMinutes / totalBusinessTimingsInMinutes;
                bookingMeter *= 100;
            }

            appointmentDashboardDto.setBookingMeter(bookingMeter);
        }
    }

    private void fetchUpcomingAppointments(List<Appointment> appointmentList,
                                           AppointmentDashboardDto appointmentDashboardDto, Date date) {
        Calendar time = Calendar.getInstance();
        time.setTime(date);
        LocalTime localTime = LocalDateTime.ofInstant(time.toInstant(), time.getTimeZone().toZoneId()).toLocalTime();
        List<AppointmentCardDTO> appointments = appointmentList.stream()
                .filter(appointment -> {
                    Calendar fromTimeCal = Calendar.getInstance();
                    fromTimeCal.setTime(appointment.getAppointmentFromTime());
                    /*Calendar toTimeCal = Calendar.getInstance();
                    toTimeCal.setTime(appointment.getAppointmentToTime());*/
                    LocalTime fromTime = LocalDateTime.ofInstant(fromTimeCal.toInstant(), fromTimeCal.getTimeZone().toZoneId()).toLocalTime();
                    /*LocalTime toTime = LocalDateTime.ofInstant(toTimeCal.toInstant(), toTimeCal.getTimeZone().toZoneId()).toLocalTime();*/
                    return (localTime.compareTo(fromTime) <= 0) &&
                            Appointment.AppointmentStatus.getBookedAppointmentStatuses().contains(Appointment.AppointmentStatus.valueOf(appointment.getStatusType().getStatusTypeName()));
                })
                .map(appointment -> createAppointmentCardDTO(appointment)).collect(Collectors.toList());
        appointmentDashboardDto.setUpcomingAppointmentDTOS(appointments);
    }

    private void fetchPendingAppointments(List<Appointment> appointmentList,
                                           AppointmentDashboardDto appointmentDashboardDto, Date date) {
        Calendar time = Calendar.getInstance();
        time.setTime(date);
        List<AppointmentCardDTO> appointments = appointmentList.stream()
                .filter(appointment ->
                     Appointment.AppointmentStatus.PENDING.name().equals(appointment.getStatusType().getStatusTypeName()))
                .map(appointment -> createAppointmentCardDTO(appointment)).collect(Collectors.toList());
        appointmentDashboardDto.setLiveAppointmentDTOS(appointments);
    }

    private AppointmentCardDTO createAppointmentCardDTO(Appointment appointment){
        AppointmentCardDTO appointmentCardDTO = new AppointmentCardDTO();
        appointmentCardDTO.setAddressTypeId(appointment.getClinic().getAddressType().getId());
        appointmentCardDTO.setAddressTypeName(appointment.getClinic().getAddressType().getAddressType());
        appointmentCardDTO.setCustomerId(appointment.getCustomer().getId());
        appointmentCardDTO.setOwnerName(appointment.getCustomer().getCoverName());
        appointmentCardDTO.setPetId(appointment.getPet().getId());
        appointmentCardDTO.setPetName(appointment.getPet().getName());
        appointmentCardDTO.setImageName(appointment.getPet().getImageName());
        DateTimeFormatter timeFormatter
                = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH);
        Calendar fromTimeCal = Calendar.getInstance();
        fromTimeCal.setTime(appointment.getAppointmentFromTime());
        Calendar toTimeCal = Calendar.getInstance();
        toTimeCal.setTime(appointment.getAppointmentToTime());
        LocalTime fromTime = LocalDateTime.ofInstant(fromTimeCal.toInstant(), fromTimeCal.getTimeZone().toZoneId()).toLocalTime();
        LocalTime toTime = LocalDateTime.ofInstant(toTimeCal.toInstant(), toTimeCal.getTimeZone().toZoneId()).toLocalTime();
        appointmentCardDTO.setFromTime(fromTime.format(timeFormatter));
        appointmentCardDTO.setToTime(toTime.format(timeFormatter));
        return appointmentCardDTO;
    }


}




package com.rahul.project.gateway.service;

import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.crud.controller.CrudCtrlBase;
import com.rahul.project.gateway.dao.AbstractDao;
import com.rahul.project.gateway.dto.AppointmentAvailabilityDto;
import com.rahul.project.gateway.dto.AppointmentAvailabilitySlotDto;
import com.rahul.project.gateway.dto.CreateAppointmentDto;
import com.rahul.project.gateway.model.*;
import com.rahul.project.gateway.repository.AppointmentRepository;
import com.rahul.project.gateway.repository.UserAddressTimingRepository;
import com.rahul.project.gateway.utility.CommonUtility;
import com.rahul.project.gateway.utility.Translator;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.invoke.MethodHandles;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@TransactionalService
public class AppointmentService {


    private final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final ModelMapper modelMapper;
    private final AbstractDao abstractDao;
    private final CommonUtility commonUtility;
    private final SmsService smsService;
    private final Translator translator;
    private final SendMailService sendMailService;
    private final UserAddressTimingRepository userAddressTimingRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;
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

    public AppointmentService(ModelMapper modelMapper, AbstractDao abstractDao, UserAddressTimingRepository userAddressTimingRepository,
                              CommonUtility commonUtility, SmsService smsService, Translator translator, SendMailService sendMailService) {
        this.modelMapper = modelMapper;
        modelMapper.addMappings(appointmentMapping);
        modelMapper.addMappings(appointmentFieldMapping);
        this.abstractDao = abstractDao;
        this.commonUtility = commonUtility;
        this.smsService = smsService;
        this.translator = translator;
        this.sendMailService = sendMailService;
        this.userAddressTimingRepository = userAddressTimingRepository;
    }

    public CreateAppointmentDto getAvailability(CreateAppointmentDto createAppointmentDto) throws Exception {
        Appointment appointment = modelMapper.map(createAppointmentDto, Appointment.class);
        User attendant = abstractDao.getEntityById(User.class, createAppointmentDto.getAttendantId());
        Integer slotInMin = attendant.getChargesSlotInMin();

        Date date = appointment.getAppointmentDate();
        Calendar time = Calendar.getInstance();
        time.setTime(date);
        int day = time.get(Calendar.DAY_OF_WEEK);
        Set<TimeRange> timeRanges = userAddressTimingRepository.businessTimings(attendant.getId(),
                appointment.getClinic().getId(), String.valueOf(day));
        if (timeRanges != null && timeRanges.size() > 0) {
            List<TimeRange> ranges = new ArrayList<>(timeRanges);
            ranges.sort(Comparator.comparing(TimeRange::getDisplayOrder));
            List<AppointmentAvailabilityDto> appointmentAvailabilityDtos = new ArrayList<>();
            int c = 0;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
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
                while (fromTime.plus(slotInMin, ChronoUnit.MINUTES).compareTo(toTime) <= 0) {
                    appointmentAvailabilityDtos.add(createEntry(formatter, fromTime, ++c, appointment));
                    fromTime = fromTime.plus(slotInMin, ChronoUnit.MINUTES);
                }
            }
//            logger.info(appointmentAvailabilityDtos.toString());
            createAppointmentDto.setAppointmentAvailabilitySlotDtos(slotCreation(appointmentAvailabilityDtos, formatter));
        }

        return createAppointmentDto;
    }

    private AppointmentAvailabilityDto createEntry(DateTimeFormatter formatter, LocalTime localTime, int c,
                                                   Appointment appointment) {

        AppointmentAvailabilityDto appointmentAvailabilityDto = new AppointmentAvailabilityDto();
        appointmentAvailabilityDto.setTime(localTime.format(formatter));
        List<Long> appointmentExist = appointmentRepository.appointmentExist(appointment.getAttendant().getId(), appointment.getClinic().getId(),
                commonUtility.getOnlyTimeAMPM(appointmentAvailabilityDto.getTime()), appointment.getAppointmentDate(),
                Appointment.AppointmentStatus.getBookedAppointmentStatuses());
        appointmentAvailabilityDto.setAvailable(appointmentExist.size() <= 0);
        appointmentAvailabilityDto.setDisplayOrder(c);
        return appointmentAvailabilityDto;
    }

    private List<AppointmentAvailabilitySlotDto> slotCreation(List<AppointmentAvailabilityDto> appointmentAvailabilityDtos,
                                                              DateTimeFormatter formatter) {
        LocalTime time6 = LocalTime.parse("05:59");
        LocalTime time12 = LocalTime.parse("12:00");
        LocalTime time16 = LocalTime.parse("15:59");
        List<AppointmentAvailabilitySlotDto> slotDtoArrayList = new ArrayList<>();
        List<AppointmentAvailabilityDto> morning = appointmentAvailabilityDtos.stream().filter(appointmentAvailabilityDto ->
                LocalTime.parse(appointmentAvailabilityDto.getTime(), formatter).isAfter(time6) &&
                        LocalTime.parse(appointmentAvailabilityDto.getTime(), formatter).isBefore(time12)
        ).collect(Collectors.toList());
        createSlotEntry(slotDtoArrayList, morning, "morning");
        List<AppointmentAvailabilityDto> afternoon = appointmentAvailabilityDtos.stream().filter(appointmentAvailabilityDto ->
                LocalTime.parse(appointmentAvailabilityDto.getTime(), formatter).isAfter(time12) &&
                        LocalTime.parse(appointmentAvailabilityDto.getTime(), formatter).isBefore(time16)
        ).collect(Collectors.toList());
        createSlotEntry(slotDtoArrayList, afternoon, "afternoon");
        List<AppointmentAvailabilityDto> evening = appointmentAvailabilityDtos.stream().filter(appointmentAvailabilityDto ->
                LocalTime.parse(appointmentAvailabilityDto.getTime(), formatter).isAfter(time16)).collect(Collectors.toList());
        createSlotEntry(slotDtoArrayList, evening, "evening");
        return slotDtoArrayList;

    }

    private void createSlotEntry(List<AppointmentAvailabilitySlotDto> slotDtos,
                                 List<AppointmentAvailabilityDto> dtos, String slot) {

        if (dtos != null && dtos.size() > 0) {
            AppointmentAvailabilitySlotDto availabilitySlotDto = new AppointmentAvailabilitySlotDto();
            availabilitySlotDto.setSlot(slot);
            availabilitySlotDto.setAppointmentAvailabilityDtos(dtos);
            slotDtos.add(availabilitySlotDto);
        }
    }

    public CreateAppointmentDto createAppointment(CreateAppointmentDto createAppointmentDto) throws Exception {
        Appointment appointment = modelMapper.map(createAppointmentDto, Appointment.class);

        if (createAppointmentDto.getId() != null) {
            Appointment entityById = abstractDao.getEntityById(Appointment.class, createAppointmentDto.getId());
            CrudCtrlBase.copyNonNullProperties(entityById, appointment);
        }

        appointment = abstractDao.saveOrUpdateEntity(appointment);
        createAppointmentDto = modelMapper.map(appointment, CreateAppointmentDto.class);
        if (createAppointmentDto != null) {
            User customer = abstractDao.getEntityById(User.class, createAppointmentDto.getCustomerId());
            User attendant = abstractDao.getEntityById(User.class, createAppointmentDto.getAttendantId());
            PartnerAddress clinic = abstractDao.getEntityById(PartnerAddress.class, createAppointmentDto.getClinicId());

            Pet pet = abstractDao.getEntityById(Pet.class, createAppointmentDto.getPetId());
            String message = "";
            String mobileNumber = customer.getCountry().getCode() + customer.getMobile();
            if (createAppointmentDto.getAppointmentTypeId() == 1) {
                message = translator.toLocale
                        ("sms.appointment.booked", new Object[]{customer.getFirstName(),
                                pet.getName(), attendant.getFirstName(), createAppointmentDto.getFromTime(),
                                createAppointmentDto.getToTime(), createAppointmentDto.getDate(),
                                clinic.getName(), clinic.getAddress()});
            }
            smsService.sendSMS(message, mobileNumber);
            if (!customer.getEmail().isEmpty()) {
                sendMailService.sendAppointmentBooked(customer, createAppointmentDto, clinic, pet.getName(), attendant.getFirstName());
            }

        }
        return createAppointmentDto;
    }
}

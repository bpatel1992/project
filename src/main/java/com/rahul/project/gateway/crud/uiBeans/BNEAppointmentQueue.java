package com.rahul.project.gateway.crud.uiBeans;


import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.crud.annotation.UIBeanSpecifier;
import com.rahul.project.gateway.dto.AppointmentQueueDto;
import com.rahul.project.gateway.model.Appointment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
@NoArgsConstructor
@UIBeanSpecifier(id = "1", beanClass = BNEAppointmentQueue.class)
@TransactionalService(value = "BNEAppointmentQueue")
public class BNEAppointmentQueue implements BNE{

@Autowired
    Environment environment;


    @Override
    public List process(List list) {
        List<AppointmentQueueDto> masterDataDTOS = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (Appointment appointment : (List<Appointment>) list) {
                masterDataDTOS.add(processEntity(appointment));
            }
        }
        return masterDataDTOS;
    }

    @Override
    public Object process(Object o) {
        return processEntity((Appointment) o);
    }


    private AppointmentQueueDto processEntity(Appointment appointment)  {
        AppointmentQueueDto appointmentQueueDto=new AppointmentQueueDto();

        appointmentQueueDto.setDogName(appointment.getPet()!=null?appointment.getPet().getName():null);
        appointmentQueueDto.setOwnerName(appointment.getCustomer()!=null?appointment.getCustomer().getFullName():null);
        appointmentQueueDto.setAppointmentType(appointment.getAppointmentType()!=null?appointment.getAppointmentType().getAppointmentType():null);
        appointmentQueueDto.setAppointmentFromTime(appointment.getAppointmentFromTime().toString()!=null?appointment.getAppointmentFromTime().toString():null);
        appointmentQueueDto.setAppointmentToTime(appointment.getAppointmentToTime().toString()!=null?appointment.getAppointmentToTime().toString():null);
        appointmentQueueDto.setAppointmentId(appointment.getId());
        appointmentQueueDto.setImageUrl(appointment.getPet() != null ?
                environment.getRequiredProperty("gateway.api.url") + "assets/pet/profile?randomKey=" + appointment.getPet().getRandomKey() : null);
        appointmentQueueDto.setStatus(appointment.getStatusType()!=null?appointment.getStatusType().getStatusTypeName():null);

        appointmentQueueDto.setSlot( TimeUnit.MILLISECONDS.toMinutes((appointment.getAppointmentToTime().getTime()-appointment.getAppointmentFromTime().getTime()))+"");

        return appointmentQueueDto;

    }

//    public String getSlot(Date fromTime ,Date toTime) throws ParseException {
////        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
////        Date date1 = format.parse(fromTime);
////        Date date2 = format.parse(toTime);
//        long difference = toTime.getTime() - fromTime.getTime();
//        return difference+"";
//    }
}

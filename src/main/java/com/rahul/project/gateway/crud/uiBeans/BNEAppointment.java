package com.rahul.project.gateway.crud.uiBeans;

import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.crud.annotation.UIBeanSpecifier;
import com.rahul.project.gateway.dto.AppointmentDTO;
import com.rahul.project.gateway.dto.Client;
import com.rahul.project.gateway.dto.MasterDataDTO;
import com.rahul.project.gateway.dto.Pet;
import com.rahul.project.gateway.model.Appointment;
import com.rahul.project.gateway.utility.CommonUtility;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@UIBeanSpecifier(id = "1", beanClass = BNEAppointment.class)
@TransactionalService(value = "BNEAppointment")
public class BNEAppointment implements BNE {

    @Autowired
    CommonUtility commonUtility;

    @Autowired
    Environment environment;

    private AppointmentDTO processData(Appointment appointment) {
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        {
            MasterDataDTO masterDataDTO = new MasterDataDTO();
            masterDataDTO.setId(appointment.getAppointmentReason().getId());
            masterDataDTO.setName(appointment.getAppointmentReason().getLabel() != null ?
                    appointment.getAppointmentReason().getLabel() :
                    appointment.getAppointmentReason().getAppointmentReason());
            appointmentDTO.setAppointmentReason(masterDataDTO);
        }
        {
            MasterDataDTO masterDataDTO = new MasterDataDTO();
            masterDataDTO.setId(appointment.getAppointmentRepeat().getId());
            masterDataDTO.setName(appointment.getAppointmentRepeat().getLabel() != null ?
                    appointment.getAppointmentRepeat().getLabel() :
                    appointment.getAppointmentRepeat().getAppointmentRepeat());
            appointmentDTO.setAppointmentRepeat(masterDataDTO);
        }
        {
            MasterDataDTO masterDataDTO = new MasterDataDTO();
            masterDataDTO.setId(appointment.getAppointmentType().getId());
            masterDataDTO.setName(appointment.getAppointmentType().getLabel() != null ?
                    appointment.getAppointmentType().getLabel() :
                    appointment.getAppointmentType().getAppointmentType());
            appointmentDTO.setAppointmentType(masterDataDTO);
        }
        List<Client> clients = new ArrayList<>();
        Client client = new Client(appointment.getCustomer().getId());
        client.setName((appointment.getCustomer().getTitle() != null ?
                appointment.getCustomer().getTitle().getTitle() : "") + " " +
                appointment.getCustomer().getFirstName() + " " + appointment.getCustomer().getLastName());
        client.setImage(appointment.getCustomer().getImageName() != null ?
                environment.getRequiredProperty("gateway.api.url") + "assets/user/profile?randomKey=" +
                        appointment.getCustomer().getRandomKey() : null);
        clients.add(client);
        appointmentDTO.setClients(clients);
        if (appointment.getPet() != null) {
            List<Pet> pets = new ArrayList<>();
            Pet pet = new Pet(appointment.getPet().getId());
            pet.setName(appointment.getPet().getName());
            pet.setImage(appointment.getPet().getImageName() != null ?
                    environment.getRequiredProperty("gateway.api.url") + "assets/pet/profile?randomKey=" +
                            appointment.getPet().getRandomKey() : null);
            pets.add(pet);
            appointmentDTO.setPets(pets);
        }
        appointmentDTO.setAppointmentDate(commonUtility.getDateFormatReport(appointment.getAppointmentDate()));
        appointmentDTO.setAppointmentFromTime(commonUtility.getDateFormatReport(appointment.getAppointmentFromTime()));
        appointmentDTO.setAppointmentToTime(commonUtility.getDateFormatReport(appointment.getAppointmentToTime()));

        return appointmentDTO;
    }

    @Override
    public List process(List list) {
        List<AppointmentDTO> appointmentDTOS = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (Appointment appointment : (List<Appointment>) list) {
                appointmentDTOS.add(processData(appointment));
            }
        }
        return appointmentDTOS;
    }

    @Override
    public Object process(Object o) {
        return processData((Appointment) o);
    }
}

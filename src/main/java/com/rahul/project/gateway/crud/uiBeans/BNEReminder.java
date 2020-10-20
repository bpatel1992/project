package com.rahul.project.gateway.crud.uiBeans;

import com.rahul.project.gateway.configuration.annotations.TransactionalService;
import com.rahul.project.gateway.crud.annotation.UIBeanSpecifier;
import com.rahul.project.gateway.dto.Client;
import com.rahul.project.gateway.dto.Pet;
import com.rahul.project.gateway.dto.ReminderReportDTO;
import com.rahul.project.gateway.model.Reminder;
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
@UIBeanSpecifier(id = "1", beanClass = BNEReminder.class)
@TransactionalService(value = "BNEReminder")
public class BNEReminder implements BNE {

    @Autowired
    CommonUtility commonUtility;
    @Autowired
    Environment environment;

    @Override
    public List process(List list) {
        List<ReminderReportDTO> ReminderReportDTOS = new ArrayList<>();
        if (list != null && list.size() > 0) {
            for (Reminder reminder : (List<Reminder>) list) {

                ReminderReportDTO reminderReportDTO = new ReminderReportDTO();

                Client client = new Client(reminder.getUser().getId());
                client.setName((reminder.getUser().getTitle() != null ?
                        (reminder.getUser().getTitle().getLabel() != null ? reminder.getUser().getTitle().getLabel() : reminder.getUser().getTitle().getTitle() + " ") : "")
                        + reminder.getUser().getFullName());
                client.setImage(reminder.getUser().getImageName() != null ? environment.getRequiredProperty("gateway.api.url") + "assets/user/profile?randomKey=" + reminder.getUser().getRandomKey() : null);
                reminderReportDTO.setClient(client);

                Pet pet = new Pet(reminder.getPet().getId());
                pet.setName(reminder.getPet().getName());
                reminderReportDTO.setPet(pet);

                reminderReportDTO.setReminderType(reminder.getReminderType().getLabel() != null ? reminder.getReminderType().getLabel() : reminder.getReminderType().getReminderType());
                reminderReportDTO.setServiceType(reminder.getServiceType().getLabel() != null ? reminder.getServiceType().getLabel() : reminder.getServiceType().getServiceTypeName());
                reminderReportDTO.setStatusType(reminder.getStatusType().getLabel() != null ? reminder.getStatusType().getLabel() : reminder.getStatusType().getStatusTypeName());

                reminderReportDTO.setCreationDate(commonUtility.getDateFormatReport(reminder.getCreationDate()));
                reminderReportDTO.setScheduleDate(commonUtility.getDateFormatReport(reminder.getScheduleDate()));
                reminderReportDTO.setNotificationDate(commonUtility.getDateFormatReport(reminder.getNotificationDate()));

                ReminderReportDTOS.add(reminderReportDTO);
            }
        }
        return ReminderReportDTOS;
    }

    @Override
    public Object process(Object o) {
        return null;
    }
}

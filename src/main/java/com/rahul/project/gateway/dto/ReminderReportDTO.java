
package com.rahul.project.gateway.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReminderReportDTO {

    private Client client;
    private String reminderType;
    private String serviceType;
    private String statusType;
    private Pet pet;
    private String creationDate;
    private String notificationDate;
    private String scheduleDate;

}

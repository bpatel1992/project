package com.rahul.project.gateway.serialize;

import com.rahul.project.gateway.model.Appointment;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;


public class AppointmentStatusSerializer extends JsonSerializer<Appointment.AppointmentStatus> {

    @Override
    public void serialize(Appointment.AppointmentStatus appointmentStatus, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();
        jsonGenerator.writeFieldName("code");
        jsonGenerator.writeString(appointmentStatus.name());
        jsonGenerator.writeFieldName("name");
        jsonGenerator.writeString(appointmentStatus.getName());
        jsonGenerator.writeFieldName("type");
        jsonGenerator.writeString(appointmentStatus.getType().toString());
        jsonGenerator.writeEndObject();
    }
}

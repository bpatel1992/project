package com.rahul.project.gateway.serialize;

import com.rahul.project.gateway.enums.FeeStatus;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class FeeStatusSerializer extends JsonSerializer<FeeStatus> {


    @Override
    public void serialize(FeeStatus feeStatus, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeFieldName("code");
        jsonGenerator.writeString(feeStatus.name());
        jsonGenerator.writeFieldName("name");
        jsonGenerator.writeString(feeStatus.getName());
        jsonGenerator.writeFieldName("type");
        jsonGenerator.writeString(feeStatus.getType().toString());
        jsonGenerator.writeEndObject();
    }
}

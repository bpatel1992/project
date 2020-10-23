package com.rahul.project.gateway.serialize;

import com.rahul.project.gateway.enums.TaxStatus;
import com.rahul.project.gateway.model.Fee;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class TaxStatusSerializer extends JsonSerializer<TaxStatus> {


    @Override
    public void serialize(TaxStatus taxStatus, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeFieldName("code");
        jsonGenerator.writeString(taxStatus.name());
        jsonGenerator.writeFieldName("name");
        jsonGenerator.writeString(taxStatus.getName());
        jsonGenerator.writeFieldName("type");
        jsonGenerator.writeString(taxStatus.getType().toString());
        jsonGenerator.writeEndObject();
    }
}

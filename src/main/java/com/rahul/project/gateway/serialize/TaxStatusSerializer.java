package com.rahul.project.gateway.serialize;

import com.rahul.project.gateway.enums.TaxType;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class TaxStatusSerializer extends JsonSerializer<TaxType> {


    @Override
    public void serialize(TaxType taxType, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeFieldName("code");
        jsonGenerator.writeString(taxType.name());
        jsonGenerator.writeFieldName("name");
        jsonGenerator.writeString(taxType.getName());
        jsonGenerator.writeFieldName("type");
        jsonGenerator.writeString(taxType.getType().toString());
        jsonGenerator.writeEndObject();
    }
}

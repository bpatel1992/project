package com.rahul.project.gateway.serialize;

import com.rahul.project.gateway.model.Transaction;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import java.io.IOException;

public class TransactionStatusSerializer extends JsonSerializer<Transaction.TransactionStatus> {

    @Override
    public void serialize(Transaction.TransactionStatus transactionStatus, JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) throws IOException {

        jsonGenerator.writeStartObject();
        jsonGenerator.writeFieldName("code");
        jsonGenerator.writeString(transactionStatus.name());
        jsonGenerator.writeFieldName("name");
        jsonGenerator.writeString(transactionStatus.getName());
        jsonGenerator.writeFieldName("type");
        jsonGenerator.writeString(transactionStatus.getType().toString());
        jsonGenerator.writeEndObject();
    }
}

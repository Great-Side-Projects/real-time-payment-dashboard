package org.dev.paymentprocessingdashboard.infraestructure.adapter;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.dev.paymentprocessingdashboard.application.port.ITransactionConvertProviderPort;
import org.dev.paymentprocessingdashboard.domain.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionConverterProviderAdapter implements ITransactionConvertProviderPort
{
    @Override
    public Transaction loadFromStringJson(String json) {
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject().getAsJsonObject("Transaction");
        return new Transaction(jsonObject.get("id").getAsString(),
                jsonObject.get("userid").getAsString(),
                jsonObject.get("amount").getAsDouble(),
                jsonObject.get("status").getAsString(),
                jsonObject.get("time").getAsString(),
                jsonObject.get("location").getAsString());
    }
}

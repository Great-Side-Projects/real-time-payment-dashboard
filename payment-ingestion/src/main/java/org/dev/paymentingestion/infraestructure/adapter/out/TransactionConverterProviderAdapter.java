package org.dev.paymentingestion.infraestructure.adapter.out;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.dev.paymentingestion.application.port.out.ITransactionConvertProviderPort;
import org.dev.paymentingestion.domain.Transaction;
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

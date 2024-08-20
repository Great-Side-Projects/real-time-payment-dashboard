package org.dev.paymentingestion.application.port.out;

import org.dev.paymentingestion.domain.Transaction;

public interface ITransactionConvertProviderPort {
    Transaction loadFromStringJson(String json);
}

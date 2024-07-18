package org.dev.paymentprocessingdashboard.application.port;

import org.dev.paymentprocessingdashboard.domain.Transaction;

public interface ITransactionConvertProviderPort {
    Transaction loadFromStringJson(String json);
}

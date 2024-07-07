package org.dev.paymentprocessingdashboard.application.port;

import org.dev.paymentprocessingdashboard.domain.Transaction;

public interface ITransactionFormatProviderPort {

    Transaction getTransactionFromLine(String line);

}

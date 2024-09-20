package org.dev.paymentingestion.application.port.in;

import org.dev.paymentingestion.domain.Transaction;
import java.util.List;

public interface ITransactionServicePort {
    void processTransaction(List<Transaction> transactions);

}

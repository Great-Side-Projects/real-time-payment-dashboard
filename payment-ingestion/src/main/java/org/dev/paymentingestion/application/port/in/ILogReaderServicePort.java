package org.dev.paymentingestion.application.port.in;

import org.dev.paymentingestion.domain.Transaction;
import java.io.IOException;
import java.util.List;

public interface ILogReaderServicePort {
    List<Transaction> readTransactionLogFile() throws IOException;
    void saveLastKnownPosition() throws IOException;
}

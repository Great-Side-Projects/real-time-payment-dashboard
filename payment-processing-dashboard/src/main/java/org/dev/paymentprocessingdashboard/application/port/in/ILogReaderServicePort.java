package org.dev.paymentprocessingdashboard.application.port.in;

import org.dev.paymentprocessingdashboard.domain.Transaction;

import java.io.IOException;
import java.util.List;

public interface ILogReaderServicePort {
    List<Transaction> readLogFile() throws IOException;
}

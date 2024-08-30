package org.dev.paymentlog.application.service;

import org.dev.paymentlog.application.port.ITransactionServicePort;
import org.dev.paymentlog.application.port.out.ILogPersistencePort;
import org.dev.paymentlog.common.UseCase;
import org.dev.paymentlog.domain.Log;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@UseCase
public class TransactionService implements ITransactionServicePort {

    private final ILogPersistencePort logPersistenceAdapter;
    private final String SEPARATOR = "::";

    public TransactionService(ILogPersistencePort logPersistenceAdapter) {
        this.logPersistenceAdapter = logPersistenceAdapter;
    }

    public void LogEvent(String[] data) {
        List<Log> logs = new ArrayList<>();
        for (String logMessage : data) {
            String[] actionLogData = logMessage.split(SEPARATOR);
            Log actionLog = new Log(actionLogData[0], actionLogData[1]);
            logs.add(actionLog);
        }
        logPersistenceAdapter.saveAll(logs);
        System.out.println(" [x] Received '" + logs.size() + " action logs " +  LocalDateTime.now());
        }
}

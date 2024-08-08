package org.dev.paymentprocessingdashboard.infraestructure.adapter.out.persistence;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.dev.paymentprocessingdashboard.application.port.out.IActionLogPersistencePort;
import org.dev.paymentprocessingdashboard.application.port.out.ILogRepository;
import org.dev.paymentprocessingdashboard.application.port.out.ILogTemplatePort;
import org.dev.paymentprocessingdashboard.common.PersistenceAdapter;
import org.dev.paymentprocessingdashboard.domain.Log;
import java.util.List;
import java.util.stream.Collectors;

@PersistenceAdapter
public class LogPersistenceAdapter implements IActionLogPersistencePort {

    private final ILogRepository logRepository;
    private final ILogTemplatePort logTemplateAdapter;

    public LogPersistenceAdapter(ILogRepository logRepository,
                                 ILogTemplatePort logTemplateAdapter) {
        this.logRepository = logRepository;
        this.logTemplateAdapter = logTemplateAdapter;
    }

    @Override
    @CircuitBreaker(name = "actionLogPersistence", fallbackMethod = "fallbackSave")
    public void save(Log actionLog) {
        LogEntity log = TransactionMapper.toLogEntity(actionLog);
        try {
            logRepository.save(log);
        } catch (Exception e) {
            System.out.println("Error saving action log: " + e.getMessage());
            throw new RuntimeException("Error saving action log");
        }
    }

    public void fallbackSave(Log actionLog, Throwable t) {
        //Implamentation of fallback method for save method in case of failure in the circuit breaker
        //This method will be called when the circuit breaker is open
        //Maybe we can send an email to the admin to notify the error
        System.out.println("Error saving action log: " + t.getMessage());
    }

    @Override
    @CircuitBreaker(name = "actionLogPersistence", fallbackMethod = "fallbackSaveAll")
    public void saveAll(List<Log> actionLogs) {

        List<LogEntity> logs = actionLogs.stream()
                .map(TransactionMapper::toLogEntity)
                .collect(Collectors.toList());
        try {
            logTemplateAdapter.saveAll(logs);
        } catch (Exception e) {
            System.out.println("Error saving action logs: " + e.getMessage());
            throw new RuntimeException("Error saving action logs");
        }
    }

    public void fallbackSaveAll(List<Log> actionLogs, Throwable t) {
        //Implamentation of fallback method for saveAll method in case of failure in the circuit breaker
        //This method will be called when the circuit breaker is open
        //Maybe we can send an email to the admin to notify the error
        System.out.println("Error saving action logs: " + t.getMessage());
    }
}

package org.dev.paymentprocessingdashboard.infraestructure.adapter.out.persistence;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.dev.paymentprocessingdashboard.application.port.out.IActionLogPersistencePort;
import org.dev.paymentprocessingdashboard.application.port.out.IActionLogRepository;
import org.dev.paymentprocessingdashboard.common.PersistenceAdapter;
import org.dev.paymentprocessingdashboard.domain.ActionLog;
import java.util.List;
import java.util.stream.Collectors;

@PersistenceAdapter
public class ActionLogPersistenceAdapter implements IActionLogPersistencePort {

    private final IActionLogRepository actionLogRepository;

    public ActionLogPersistenceAdapter(IActionLogRepository actionLogRepository) {
        this.actionLogRepository = actionLogRepository;
    }

    @Override
    @CircuitBreaker(name = "actionLogPersistence", fallbackMethod = "fallbackSave")
    public void save(ActionLog actionLog) {
        ActionLogEntity log = TransactionMapper.toActionLogEntity(actionLog);
        try {
            actionLogRepository.save(log);
        } catch (Exception e) {
            System.out.println("Error saving action log: " + e.getMessage());
            throw new RuntimeException("Error saving action log");
        }
    }

    public void fallbackSave(ActionLog actionLog, Throwable t) {
        //Implamentation of fallback method for save method in case of failure in the circuit breaker
        //This method will be called when the circuit breaker is open
        //Maybe we can send an email to the admin to notify the error
        System.out.println("Error saving action log: " + t.getMessage());
    }

    @Override
    @CircuitBreaker(name = "actionLogPersistence", fallbackMethod = "fallbackSaveAll")
    public void saveAll(List<ActionLog> actionLogs) {

        List<ActionLogEntity> logs = actionLogs.stream()
                .map(TransactionMapper::toActionLogEntity)
                .collect(Collectors.toList());
        try {
            actionLogRepository.saveAll(logs);
        } catch (Exception e) {
            System.out.println("Error saving action logs: " + e.getMessage());
            throw new RuntimeException("Error saving action logs");
        }
    }

    public void fallbackSaveAll(List<ActionLog> actionLogs, Throwable t) {
        //Implamentation of fallback method for saveAll method in case of failure in the circuit breaker
        //This method will be called when the circuit breaker is open
        //Maybe we can send an email to the admin to notify the error
        System.out.println("Error saving action logs: " + t.getMessage());
    }
}

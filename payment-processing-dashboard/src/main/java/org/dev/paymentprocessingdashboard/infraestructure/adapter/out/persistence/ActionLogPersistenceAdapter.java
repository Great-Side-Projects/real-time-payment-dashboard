package org.dev.paymentprocessingdashboard.infraestructure.adapter.out.persistence;

import org.dev.paymentprocessingdashboard.application.port.out.IActionLogPersistencePort;
import org.dev.paymentprocessingdashboard.application.port.out.IActionLogRepository;
import org.dev.paymentprocessingdashboard.common.PersistenceAdapter;

import java.time.LocalDateTime;
import java.util.List;

@PersistenceAdapter
public class ActionLogPersistenceAdapter implements IActionLogPersistencePort {

    private final IActionLogRepository actionLogRepository;

    public ActionLogPersistenceAdapter(IActionLogRepository actionLogRepository) {
        this.actionLogRepository = actionLogRepository;
    }

    @Override
    public void save(String action, String details) {
        // Save action log
        ActionLogEntity log = new ActionLogEntity();
        log.setAction(action);
        log.setDetails(details);
        log.setTimestamp(LocalDateTime.now());
        actionLogRepository.save(log);

    }

    @Override
    public void saveAll(String action, List<String> details) {

        List<ActionLogEntity> logs = details.stream()
                .map(detail -> {
                    ActionLogEntity log = new ActionLogEntity();
                    log.setAction(action);
                    log.setDetails(detail);
                    log.setTimestamp(LocalDateTime.now());
                    return log;
                })
                .toList();
        actionLogRepository.saveAll(logs);
    }
}

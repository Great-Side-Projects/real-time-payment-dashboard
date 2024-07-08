package org.dev.paymentprocessingdashboard.infraestructure.adapter.out.persistence;

import org.dev.paymentprocessingdashboard.application.port.out.IActionLogPersistencePort;
import org.dev.paymentprocessingdashboard.application.port.out.IActionLogRepository;
import org.dev.paymentprocessingdashboard.common.PersistenceAdapter;
import org.dev.paymentprocessingdashboard.domain.ActionLog;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@PersistenceAdapter
public class ActionLogPersistenceAdapter implements IActionLogPersistencePort {

    private final IActionLogRepository actionLogRepository;

    public ActionLogPersistenceAdapter(IActionLogRepository actionLogRepository) {
        this.actionLogRepository = actionLogRepository;
    }

    @Override
    public void save(ActionLog actionLog) {
        ActionLogEntity log = TransactionMapper.toActionLogEntity(actionLog);
        actionLogRepository.save(log);
    }

    @Override
    public void saveAll(List<ActionLog> actionLogs) {

        List<ActionLogEntity> logs = actionLogs.stream()
                .map(TransactionMapper::toActionLogEntity)
                .collect(Collectors.toList());
        actionLogRepository.saveAll(logs);
    }
}

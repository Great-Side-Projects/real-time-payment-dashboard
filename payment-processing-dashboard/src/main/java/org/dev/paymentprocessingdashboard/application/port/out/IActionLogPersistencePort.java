package org.dev.paymentprocessingdashboard.application.port.out;

import org.dev.paymentprocessingdashboard.domain.ActionLog;
import java.util.List;

public interface IActionLogPersistencePort {
    void save(ActionLog actionLog);
    void saveAll(List<ActionLog> actionLogs);
}

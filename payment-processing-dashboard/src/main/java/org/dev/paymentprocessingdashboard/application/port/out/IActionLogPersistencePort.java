package org.dev.paymentprocessingdashboard.application.port.out;

import org.dev.paymentprocessingdashboard.domain.Log;
import java.util.List;

public interface IActionLogPersistencePort {
    void save(Log actionLog);
    void saveAll(List<Log> actionLogs);
}

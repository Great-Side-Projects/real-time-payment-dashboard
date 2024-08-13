package org.dev.paymentlog.application.port.out;

import org.dev.paymentlog.domain.Log;
import java.util.List;

public interface ILogPersistencePort {
    void save(Log log);
    void saveAll(List<Log> logs);
}

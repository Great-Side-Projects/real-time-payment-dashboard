package org.dev.paymentprocessingdashboard.application.port.out;

import java.util.List;

public interface IActionLogPersistencePort {
    void save(String action, String details);
    void saveAll(String action, List<String> details);
}

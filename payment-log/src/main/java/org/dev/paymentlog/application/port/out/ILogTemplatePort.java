package org.dev.paymentlog.application.port.out;

import org.dev.paymentlog.infraestructure.adapter.out.persistence.LogEntity;
import java.util.List;

public interface ILogTemplatePort {
    void saveAll(List<LogEntity> actionLogs);
}

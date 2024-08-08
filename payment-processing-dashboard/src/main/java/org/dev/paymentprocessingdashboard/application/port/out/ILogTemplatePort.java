package org.dev.paymentprocessingdashboard.application.port.out;

import org.dev.paymentprocessingdashboard.infraestructure.adapter.out.persistence.LogEntity;

import java.util.List;

public interface ILogTemplatePort {
    void saveAll(List<LogEntity> actionLogs);
}

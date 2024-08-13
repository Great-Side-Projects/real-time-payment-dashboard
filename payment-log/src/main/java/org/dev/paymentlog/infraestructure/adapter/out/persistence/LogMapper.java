package org.dev.paymentlog.infraestructure.adapter.out.persistence;

import org.dev.paymentlog.domain.Log;

public class LogMapper {

    public static LogEntity toLogEntity(Log actionLog) {
        LogEntity log = new LogEntity();
        log.setAction(actionLog.getAction());
        log.setDetails(actionLog.getDetails());
        return log;
    }
}

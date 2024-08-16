package org.dev.paymentlog.infraestructure.adapter.in;

import org.dev.paymentlog.application.port.in.ILoggingEventPort;
import org.dev.paymentlog.application.port.out.ILogPersistencePort;
import org.dev.paymentlog.domain.Log;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class LoggingAdapter implements ILoggingEventPort {

    private final ILogPersistencePort logPersistenceAdapter;
    private final String SEPARATOR = "::";

    public LoggingAdapter(ILogPersistencePort actionLogPersistenceAdapter) {
        this.logPersistenceAdapter = actionLogPersistenceAdapter;
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.name}")
    @Override
    public void LogEvent(String[] data) {

        List<Log> logs = new ArrayList<>();
        for (String logMessage : data) {
            String[] actionLogData = logMessage.split(SEPARATOR);
            Log actionLog = new Log(actionLogData[0], actionLogData[1]);
            logs.add(actionLog);
        }
        logPersistenceAdapter.saveAll(logs);
        System.out.println(" [x] Received '" + logs.size() + " action logs");
    }
}

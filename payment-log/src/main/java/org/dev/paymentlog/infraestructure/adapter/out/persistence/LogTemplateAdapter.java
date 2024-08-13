package org.dev.paymentlog.infraestructure.adapter.out.persistence;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BatchStatement;
import com.datastax.oss.driver.api.core.cql.BatchStatementBuilder;
import com.datastax.oss.driver.api.core.cql.BatchType;
import com.datastax.oss.driver.api.core.cql.PreparedStatement;
import org.dev.paymentlog.application.port.out.ILogTemplatePort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class LogTemplateAdapter implements ILogTemplatePort {
    private final CqlSession cqlSession;
    private final String INSERT_ACTION_LOG = "INSERT INTO log (id, action, details, timestamp) VALUES (?, ?, ?, ?)";
    private final int batchSize = 10000;

    public LogTemplateAdapter(CqlSession cqlSession) {
        this.cqlSession = cqlSession;
    }

    @Override
    @Transactional
    public void saveAll(List<LogEntity> actionLogs) {

        try {
            PreparedStatement ps = cqlSession.prepare(INSERT_ACTION_LOG);
            BatchStatementBuilder batchStatementBuilder = BatchStatement.builder(BatchType.UNLOGGED);
            for (LogEntity actionLog : actionLogs) {
                batchStatementBuilder.addStatement(ps.bind(
                                actionLog.getId(),
                                actionLog.getAction(),
                                actionLog.getDetails(),
                                actionLog.getTimestamp()
                        )
                );
            }
            cqlSession.execute(batchStatementBuilder.build());

        } finally {
            if (cqlSession != null) {
                //cqlSession.close();
            }
        }
    }
}

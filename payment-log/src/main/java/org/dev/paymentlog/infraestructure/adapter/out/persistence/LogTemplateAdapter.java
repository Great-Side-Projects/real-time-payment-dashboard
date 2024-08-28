package org.dev.paymentlog.infraestructure.adapter.out.persistence;

import org.dev.paymentlog.application.port.out.ILogTemplatePort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Component
public class LogTemplateAdapter implements ILogTemplatePort {
    private final JdbcTemplate jdbcTemplate;
    private final String INSERT_ACTION_LOG = "INSERT INTO log (id, action, details, created_at) VALUES (?, ?, ?, ?)";
    private final int batchSize = 5000;

    public LogTemplateAdapter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void saveAll(List<LogEntity> actionLogs) {

        try (Connection conn = jdbcTemplate.getDataSource().getConnection()) {
            conn.setAutoCommit(false);
            int count = 0;
            try (PreparedStatement ps = conn.prepareStatement(INSERT_ACTION_LOG)) {
                for (LogEntity  actionLog : actionLogs) {
                    ps.setString(1, actionLog.getId());
                    ps.setString(2, actionLog.getAction());
                    ps.setString(3, actionLog.getDetails());
                    ps.setTimestamp(4, Timestamp.valueOf(actionLog.getCreatedAt()));
                    ps.addBatch();

                    if (++count % batchSize == 0) {
                        ps.executeBatch();
                    }
                }
                ps.executeBatch();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new RuntimeException("Error saving action logs", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error saving action logs", e);
        }
    }
}

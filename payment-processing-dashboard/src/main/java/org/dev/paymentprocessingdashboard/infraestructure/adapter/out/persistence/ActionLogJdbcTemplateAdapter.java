package org.dev.paymentprocessingdashboard.infraestructure.adapter.out.persistence;

import jakarta.transaction.Transactional;
import org.dev.paymentprocessingdashboard.application.port.out.IJdbcTemplatePort;
import org.dev.paymentprocessingdashboard.domain.ActionLog;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Component
public class ActionLogJdbcTemplateAdapter implements IJdbcTemplatePort<ActionLog> {
    private final JdbcTemplate jdbcTemplate;
    private final String INSERT_ACTION_LOG = "INSERT INTO action_log_entity (action, details) VALUES (?, ?)";
    private final int batchSize = 10000;

    public ActionLogJdbcTemplateAdapter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void saveAll(List<ActionLog> actionLogs) {

        try (Connection conn = jdbcTemplate.getDataSource().getConnection()) {
            conn.setAutoCommit(false);
            int count = 0;
            try ( PreparedStatement ps = conn.prepareStatement(INSERT_ACTION_LOG)) {
                for (ActionLog  actionLog : actionLogs) {
                    ps.setString(1, actionLog.getAction());
                    ps.setString(2, actionLog.getDetails());
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

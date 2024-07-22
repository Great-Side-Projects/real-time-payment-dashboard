package org.dev.paymentprocessingdashboard.infraestructure.adapter.out.persistence;

import jakarta.transaction.Transactional;
import org.dev.paymentprocessingdashboard.application.port.out.IJdbcTemplatePort;
import org.dev.paymentprocessingdashboard.domain.Transaction;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class TransactionJdbcTemplateAdapter implements IJdbcTemplatePort<Transaction> {
    private final JdbcTemplate jdbcTemplate;
    private final String INSERT_TRANSACTION = "INSERT INTO transaction_entity (id, userid, amount, status, time, location) VALUES (?, ?, ?, ?, ?, ?)";
    private final int batchSize = 10000;
    private final ZonedDateTime zonedDateTime = ZonedDateTime.now();

    public TransactionJdbcTemplateAdapter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void saveAll(List<Transaction> transactions) {

        try (Connection conn = jdbcTemplate.getDataSource().getConnection()) {
            conn.setAutoCommit(false);
            int count = 0;
            ZonedDateTime zonedDateTime;

            try ( PreparedStatement ps = conn.prepareStatement(INSERT_TRANSACTION)) {
                for (Transaction transaction : transactions) {
                    ps.setString(1, transaction.getId().toString());
                    ps.setString(2, transaction.getUserId());
                    ps.setDouble(3, transaction.getAmount());
                    ps.setString(4, transaction.getStatus());
                    ps.setString(5, transaction.getTime());
                    ps.setString(6, transaction.getLocation());
                    ps.addBatch();

                    if (++count % batchSize == 0) {
                        // Ejecutar el batch cada batchSize registros
                        ps.executeBatch();
                    }
                }
                ps.executeBatch();
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw new RuntimeException("Error saving transactions", e);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error saving transactions", e);
        }
        //jdbcTemplate.batchUpdate(INSERT_TRANSACTION, transactions, transactions.size(), (
        //        (ps, transaction) -> {
        //    ps.setObject(1, transaction.getId().toString());
        //    ps.setString(2, transaction.getUserid());
        //    ps.setDouble(3, transaction.getAmount());
        //    ps.setString(4, transaction.getStatus());
        //    ps.setObject(5, transaction.getTimestamp());
        //    ps.setString(6, transaction.getLocation());
        //}));
    }
}

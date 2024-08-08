package org.dev.paymentprocessingdashboard.application.port.out;

import org.dev.paymentprocessingdashboard.domain.TransactionPerMinuteSummaryProjection;
import org.dev.paymentprocessingdashboard.domain.TransactionSummaryProjection;
import org.dev.paymentprocessingdashboard.infraestructure.adapter.out.persistence.TransactionEntity;
import org.springframework.data.cassandra.repository.AllowFiltering;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;
@EnableCassandraRepositories
public interface ITransactionRepository extends CrudRepository<TransactionEntity, UUID> {

    @Query(value = """
            SELECT
            SUM(t.amount) as totalAmount,
            SUM(CASE WHEN t.status = 'success' THEN 1 ELSE 0 END) as totalSuccess,
            SUM(CASE WHEN t.status = 'failure' THEN 1 ELSE 0 END) as totalFailed,
            t.userid as userid,
            COUNT(t.id) as totalTransactions
            FROM transaction_entity t
            GROUP BY t.userid""")
    List<TransactionSummaryProjection> findTransactionSummary();

    @Query(value = """
            SELECT DATE_FORMAT(t.time, '%Y-%m-%d %H:%i') AS minute,
            COUNT(*) AS totalTransactions
            FROM transaction_entity t
            GROUP BY minute
            ORDER BY minute DESC LIMIT 10;""")
    List<TransactionPerMinuteSummaryProjection> findTransactionPerMinuteSummary();


    @AllowFiltering
    Slice<TransactionEntity> findAllByStatus(String status, Pageable pageable);

}

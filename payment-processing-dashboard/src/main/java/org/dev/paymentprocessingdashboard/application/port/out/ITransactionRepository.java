package org.dev.paymentprocessingdashboard.application.port.out;

import org.dev.paymentprocessingdashboard.domain.TransactionPerMinuteSummaryProjection;
import org.dev.paymentprocessingdashboard.infraestructure.adapter.out.persistence.TransactionEntity;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.UUID;

public interface ITransactionRepository extends CrudRepository<TransactionEntity, UUID> {

    @Query(value = """
            SELECT DATE_FORMAT(t.time, '%Y-%m-%d %H:%i') AS minute,
            COUNT(*) AS totalTransactions
            FROM transaction_entity t
            GROUP BY minute
            ORDER BY minute DESC LIMIT 10;""")
    List<TransactionPerMinuteSummaryProjection> findTransactionPerMinuteSummary();
}

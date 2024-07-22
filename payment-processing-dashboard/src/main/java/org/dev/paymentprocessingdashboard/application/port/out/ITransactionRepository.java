package org.dev.paymentprocessingdashboard.application.port.out;

import org.dev.paymentprocessingdashboard.domain.TransactionPerMinuteSummaryProjection;
import org.dev.paymentprocessingdashboard.domain.TransactionSummaryProjection;
import org.dev.paymentprocessingdashboard.infraestructure.adapter.out.persistence.TransactionEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.UUID;

public interface ITransactionRepository extends CrudRepository<TransactionEntity, UUID>, JpaSpecificationExecutor<TransactionEntity>, PagingAndSortingRepository<TransactionEntity, UUID> {
    @Query(value = """
            SELECT 
            SUM(t.amount) as totalAmount, 
            SUM(CASE WHEN t.status = 'success' THEN 1 ELSE 0 END) as totalSuccess, 
            SUM(CASE WHEN t.status = 'failure' THEN 1 ELSE 0 END) as totalFailed, 
            t.userid as userid, 
            COUNT(t.id) as totalTransactions 
            FROM transaction_entity t 
            GROUP BY t.userid""",
            nativeQuery = true)
    List<TransactionSummaryProjection> findTransactionSummary();

    @Query(value = """
            SELECT DATE_FORMAT(t.time, '%Y-%m-%d %H:%i') AS minute,
            COUNT(*) AS totalTransactions
            FROM transaction_entity t
            GROUP BY minute
            ORDER BY minute DESC LIMIT 10;""",
            nativeQuery = true)
    List<TransactionPerMinuteSummaryProjection> findTransactionPerMinuteSummary( );
}

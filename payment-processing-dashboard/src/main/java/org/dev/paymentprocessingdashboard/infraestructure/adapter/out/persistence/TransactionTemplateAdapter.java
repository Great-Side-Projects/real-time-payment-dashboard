package org.dev.paymentprocessingdashboard.infraestructure.adapter.out.persistence;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.*;
import org.dev.paymentprocessingdashboard.application.port.out.ITransactionTemplatePort;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.cassandra.core.query.CriteriaDefinition;
import org.springframework.data.cassandra.core.query.Query;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

@Component
public class TransactionTemplateAdapter implements ITransactionTemplatePort {
    private final CqlSession cqlSession;
    private final String INSERT_TRANSACTION = "INSERT INTO transaction (rowid, id, userid, amount, status, time, location) VALUES (? ,?, ?, ?, ?, ?, ?)";
    private final String SELECT_TRANSACTION_SUMMARY = "SELECT COUNT(id) AS totalCount, SUM(amount) AS totalValue FROM transaction";
    private final String SELECT_TRANSACTION_SUMMARY_BY_STATUS = "SELECT COUNT(id) AS totalCount, SUM(amount) AS totalValue FROM transaction WHERE status = ?";
    private final String SELECT_TRANSACTION_SUMMARY_BY_USERID = "SELECT COUNT(id) AS totalCount, SUM(amount) AS totalValue FROM transaction WHERE userid = ?";
    private final int batchSize = 5000; //65535 max
    private final CassandraTemplate cassandraTemplate;


    public TransactionTemplateAdapter(CqlSession cqlSession, CassandraTemplate cassandraTemplate) {
        this.cqlSession = cqlSession;
        this.cassandraTemplate = cassandraTemplate;
    }

    @Override
    @Transactional
    public void saveAll(List<TransactionEntity> transactions) {

        try {
            PreparedStatement ps = cqlSession.prepare(INSERT_TRANSACTION);
            BatchStatementBuilder batchStatementBuilder = BatchStatement.builder(BatchType.UNLOGGED);
            List<BatchStatement> batchStatements = new ArrayList<>();
            for (TransactionEntity transactionEntity : transactions) {
                batchStatementBuilder.addStatement(
                        ps.bind(
                                transactionEntity.getRowid(),
                                transactionEntity.getId(),
                                transactionEntity.getUserid(),
                                transactionEntity.getAmount(),
                                transactionEntity.getStatus(),
                                transactionEntity.getTime(),
                                transactionEntity.getLocation()
                        )
                );
                if (transactions.indexOf(transactionEntity) > 0)
                    if (transactions.indexOf(transactionEntity) % batchSize == 0) {
                        batchStatements.add(batchStatementBuilder.build());
                        batchStatementBuilder = BatchStatement.builder(BatchType.UNLOGGED);
                    }
            }
            batchStatements.add(batchStatementBuilder.build());

            for (BatchStatement batchStatement : batchStatements) {
                cqlSession.execute(batchStatement);
            }
        } finally {
            if (cqlSession != null) {
                //cqlSession.close();
            }
        }
    }

    @Override
    public Slice<TransactionEntity> findAll(CassandraPageRequest passandraPageRequest, List<CriteriaDefinition> criterias) {
        Query query = Query.query(criterias.toArray(new CriteriaDefinition[0]))
                .pageRequest(passandraPageRequest)
                .withAllowFiltering();
        return cassandraTemplate.slice(query, TransactionEntity.class);
    }

    @Override
    public CassandraPageRequest createCassandraPageRequest(int pageSize, String pagingState) {

        if (pagingState != null) {
            try {
                ByteBuffer byteBuffer = com.datastax.oss.protocol.internal.util.Bytes.fromHexString(pagingState);
                // a page number might be any and the result depends only on paging state
                return CassandraPageRequest.of(PageRequest.of(0, pageSize), byteBuffer);
            } catch (Exception e) {
                throw new RuntimeException("Error parsing paging state " + e.getMessage());
            }
        }
        return CassandraPageRequest.of(PageRequest.of(0, pageSize), null);
    }

    @Override
    public TransactionSummary getTransactionSummary() {
        return cassandraTemplate.selectOne(SELECT_TRANSACTION_SUMMARY, TransactionSummary.class);
    }

    @Override
    public TransactionSummary getTransactionSummaryByStatus(String status) {

        return cassandraTemplate.selectOne(
                SimpleStatement.newInstance(SELECT_TRANSACTION_SUMMARY_BY_STATUS, status),
                TransactionSummary.class);
    }

    @Override
    public TransactionSummary getTransactionSummaryByUserId(String userId) {
        return cassandraTemplate.selectOne(
                SimpleStatement.newInstance(SELECT_TRANSACTION_SUMMARY_BY_USERID, userId),
                TransactionSummary.class);
    }
}

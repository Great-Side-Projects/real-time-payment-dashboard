package org.dev.paymentprocessing.application.port.out;

import org.dev.paymentprocessing.infraestructure.adapter.out.persistence.TransactionEntity;
import org.dev.paymentprocessing.infraestructure.adapter.out.persistence.TransactionSummary;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.cassandra.core.query.CriteriaDefinition;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface ITransactionTemplatePort {
    void saveAll(List<TransactionEntity> transactions);
    Slice<TransactionEntity> findAll(CassandraPageRequest cassandraPageRequest, List<CriteriaDefinition> criterias);
    CassandraPageRequest createCassandraPageRequest(int pageSize, String pagingState);
    TransactionSummary getTransactionSummary();
    TransactionSummary getTransactionSummaryByStatus(String status);
    TransactionSummary getTransactionSummaryByUserId(String userId);
}

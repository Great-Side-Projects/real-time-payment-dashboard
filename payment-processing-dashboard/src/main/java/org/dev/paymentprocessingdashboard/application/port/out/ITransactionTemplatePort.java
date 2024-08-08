package org.dev.paymentprocessingdashboard.application.port.out;

import org.dev.paymentprocessingdashboard.infraestructure.adapter.out.persistence.TransactionEntity;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.cassandra.core.query.CriteriaDefinition;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface ITransactionTemplatePort {
    void saveAll(List<TransactionEntity> transactions);
    Slice<TransactionEntity> findAll(CassandraPageRequest cassandraPageRequest, List<CriteriaDefinition> criterias);
    CassandraPageRequest createCassandraPageRequest(int pageSize, String pagingState);
}

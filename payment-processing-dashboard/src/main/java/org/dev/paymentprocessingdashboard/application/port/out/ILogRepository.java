package org.dev.paymentprocessingdashboard.application.port.out;

import org.dev.paymentprocessingdashboard.infraestructure.adapter.out.persistence.LogEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;


public interface ILogRepository extends CassandraRepository<LogEntity, Long> {
}

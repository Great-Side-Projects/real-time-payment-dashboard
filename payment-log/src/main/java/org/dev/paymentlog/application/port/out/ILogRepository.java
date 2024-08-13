package org.dev.paymentlog.application.port.out;

import org.dev.paymentlog.infraestructure.adapter.out.persistence.LogEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;


public interface ILogRepository extends CassandraRepository<LogEntity, Long> {
}
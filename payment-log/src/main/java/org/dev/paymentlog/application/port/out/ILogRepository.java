package org.dev.paymentlog.application.port.out;

import org.dev.paymentlog.infraestructure.adapter.out.persistence.LogEntity;
import org.springframework.data.repository.CrudRepository;

public interface ILogRepository extends CrudRepository<LogEntity, String> {
}

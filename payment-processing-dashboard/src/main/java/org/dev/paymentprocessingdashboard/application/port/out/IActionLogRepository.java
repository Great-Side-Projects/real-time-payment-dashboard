package org.dev.paymentprocessingdashboard.application.port.out;

import org.dev.paymentprocessingdashboard.infraestructure.adapter.out.persistence.ActionLogEntity;
import org.springframework.data.repository.CrudRepository;


public interface IActionLogRepository extends CrudRepository<ActionLogEntity, Long>{
}

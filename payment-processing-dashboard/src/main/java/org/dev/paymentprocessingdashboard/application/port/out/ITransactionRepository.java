package org.dev.paymentprocessingdashboard.application.port.out;

import org.dev.paymentprocessingdashboard.infraestructure.adapter.out.persistence.TransactionEntity;
import org.springframework.data.repository.CrudRepository;
import java.util.UUID;

public interface ITransactionRepository extends CrudRepository<TransactionEntity, UUID> {

}

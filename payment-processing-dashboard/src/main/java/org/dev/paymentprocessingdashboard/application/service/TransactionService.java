package org.dev.paymentprocessingdashboard.application.service;

import org.dev.paymentprocessingdashboard.application.port.in.ITransactionServicePort;
import org.dev.paymentprocessingdashboard.application.port.out.ITransactionPersistencePort;
import org.dev.paymentprocessingdashboard.common.UseCase;
import org.dev.paymentprocessingdashboard.domain.Transaction;
import org.springframework.data.domain.Page;

@UseCase
public class TransactionService implements ITransactionServicePort {

    private final ITransactionPersistencePort transactionPersistenceAdapter;

    public TransactionService(ITransactionPersistencePort transactionPersistenceAdapter) {
        this.transactionPersistenceAdapter = transactionPersistenceAdapter;
    }

    @Override
    public Page<Transaction> filterTransactions(String status, String userId, Double minAmount, Double maxAmount, String transactionId, int page, int size) {
        //validation logic


        return transactionPersistenceAdapter.findAll(status, userId, minAmount, maxAmount, transactionId, page, size);
    }
}

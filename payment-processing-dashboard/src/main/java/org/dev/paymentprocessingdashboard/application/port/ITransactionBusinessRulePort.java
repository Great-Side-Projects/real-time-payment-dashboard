package org.dev.paymentprocessingdashboard.application.port;

import org.dev.paymentprocessingdashboard.domain.Transaction;

public interface ITransactionBusinessRulePort {
   boolean isFailureTransaction(Transaction transaction);
   boolean isHighAmountTransaction(Transaction transaction);
}
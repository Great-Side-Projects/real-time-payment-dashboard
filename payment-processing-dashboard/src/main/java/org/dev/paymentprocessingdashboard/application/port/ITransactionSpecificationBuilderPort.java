package org.dev.paymentprocessingdashboard.application.port;

import org.dev.paymentprocessingdashboard.infraestructure.adapter.TransactionSpecificationBuilderAdapter;
import org.dev.paymentprocessingdashboard.infraestructure.adapter.out.persistence.TransactionEntity;
import org.springframework.data.jpa.domain.Specification;

public interface ITransactionSpecificationBuilderPort {
    <T> TransactionSpecificationBuilderAdapter with(String dbField, T value);
    <T> TransactionSpecificationBuilderAdapter withOr(String dbField, T value);
    <T> TransactionSpecificationBuilderAdapter withLike(String dbField, T value);
    <T> TransactionSpecificationBuilderAdapter withBetween(String dbField, T from, T to);
    Specification<TransactionEntity> build();
    ITransactionSpecificationBuilderPort New();
}
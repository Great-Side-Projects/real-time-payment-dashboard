package org.dev.paymentprocessingdashboard.infraestructure.adapter;

import org.dev.paymentprocessingdashboard.application.port.ITransactionSpecificationBuilderPort;
import org.dev.paymentprocessingdashboard.infraestructure.adapter.out.persistence.TransactionEntity;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class TransactionSpecificationBuilderAdapter implements ITransactionSpecificationBuilderPort {
    private Specification<TransactionEntity> spec;

    public TransactionSpecificationBuilderAdapter() {
        this.spec = Specification.where(null);
    }

    public ITransactionSpecificationBuilderPort New(){
        this.spec = Specification.where(null);
        return this;
    }

    @Override
    public <T> TransactionSpecificationBuilderAdapter with(String dbField, T value) {
        if (value != null) {
            this.spec = this.spec.and(TransactionSpecification.hasEqual(dbField,value));
        }
        return this;
    }

    @Override
    public <T> TransactionSpecificationBuilderAdapter withOr(String dbField, T value) {
        if (value != null) {
            this.spec = this.spec.or(TransactionSpecification.hasEqual(dbField, value));
        }
        return this;
    }

    @Override
    public <T> TransactionSpecificationBuilderAdapter withLike(String dbField, T value) {
        if (value != null) {
            this.spec = this.spec.and(TransactionSpecification.hasLike(dbField, value));
        }
        return this;
    }

    @Override
    public <T> TransactionSpecificationBuilderAdapter withBetween(String dbField, T from, T to) {
        if (from != null && to != null) {
            this.spec = this.spec.and(TransactionSpecification.hasBetween(dbField, from, to));
        }
        return this;
    }

    @Override
    public Specification<TransactionEntity> build() {
        return this.spec;
    }
}
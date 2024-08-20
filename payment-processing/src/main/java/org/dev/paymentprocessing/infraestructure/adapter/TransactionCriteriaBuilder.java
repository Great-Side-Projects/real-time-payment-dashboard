package org.dev.paymentprocessing.infraestructure.adapter;

import org.springframework.data.cassandra.core.query.Criteria;
import org.springframework.data.cassandra.core.query.CriteriaDefinition;
import java.util.ArrayList;
import java.util.List;

public class TransactionCriteriaBuilder {
    private final List<CriteriaDefinition> criterias;

    public TransactionCriteriaBuilder() {
        this.criterias = new ArrayList<>();
    }

    public TransactionCriteriaBuilder withStatus(String status) {
        if (status != null) {
            criterias.add(Criteria.where("status").is(status));
        }
        return this;
    }

    public TransactionCriteriaBuilder withUserId(String userId) {
        if (userId != null) {
            criterias.add(Criteria.where("userid").is(userId));
        }
        return this;
    }

    public TransactionCriteriaBuilder withMinAmount(Double minAmount) {
        if (minAmount != null) {
            criterias.add(Criteria.where("amount").gte(minAmount));
        }
        return this;
    }

    public TransactionCriteriaBuilder withMaxAmount(Double maxAmount) {
        if (maxAmount != null) {
            criterias.add(Criteria.where("amount").lte(maxAmount));
        }
        return this;
    }

    public TransactionCriteriaBuilder withTransactionId(String transactionId) {
        if (transactionId != null) {
            criterias.add(Criteria.where("id").is(transactionId));
        }
        return this;
    }

    public List<CriteriaDefinition> build() {
        return criterias;
    }
}


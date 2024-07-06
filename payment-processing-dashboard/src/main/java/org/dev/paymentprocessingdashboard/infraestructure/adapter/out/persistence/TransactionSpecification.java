package org.dev.paymentprocessingdashboard.infraestructure.adapter.out.persistence;

import org.dev.paymentprocessingdashboard.domain.Transaction;
import org.springframework.data.jpa.domain.Specification;

public class TransactionSpecification {

    public static Specification<TransactionEntity> hasStatus(String status) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<TransactionEntity> hasUserId(String userId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("userId"), userId);
    }

    public static Specification<TransactionEntity> hasAmountBetween(Double minAmount, Double maxAmount) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get("amount"), minAmount, maxAmount);
    }

    public static Specification<TransactionEntity> hasTransactionId(String transactionId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("Id"), transactionId);
    }

    public static Specification<TransactionEntity> hasUserIdContaining(String userId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("userId"), "%" + userId + "%");
    }
}


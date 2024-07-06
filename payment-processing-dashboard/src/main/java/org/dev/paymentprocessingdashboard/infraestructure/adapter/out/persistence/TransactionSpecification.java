package org.dev.paymentprocessingdashboard.infraestructure.adapter.out.persistence;

import org.springframework.data.jpa.domain.Specification;
import java.util.UUID;

public class TransactionSpecification {

    public static Specification<TransactionEntity> hasStatus(String status) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("status"), status);
    }

    public static Specification<TransactionEntity> hasUserId(String userId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("userid"), userId);
    }

    public static Specification<TransactionEntity> hasAmountBetween(Double minAmount, Double maxAmount) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get("amount"), minAmount, maxAmount);
    }

    public static Specification<TransactionEntity> hasTransactionId(UUID transactionId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("id"), transactionId);
    }

    public static Specification<TransactionEntity> hasUserIdContaining(String userId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("userid"), "%" + userId + "%");
    }
}


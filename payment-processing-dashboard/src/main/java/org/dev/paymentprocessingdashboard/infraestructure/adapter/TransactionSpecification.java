package org.dev.paymentprocessingdashboard.infraestructure.adapter;

import org.dev.paymentprocessingdashboard.infraestructure.adapter.out.persistence.TransactionEntity;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDateTime;

public class TransactionSpecification {

    public static <T> Specification<TransactionEntity> hasEqual(String dbField, T value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(dbField), value);
    }

    public static <T, V> Specification<TransactionEntity> hasBetween(String dbField, T from, V to) {

        if (from instanceof Double && to instanceof Double) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.between(root.get(dbField), (Double) from, (Double) to);
        }

        if (from instanceof LocalDateTime && to instanceof LocalDateTime) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.between(root.get(dbField), (LocalDateTime) from, (LocalDateTime) to);
        }

        if (from instanceof Integer && to instanceof Integer) {
            return (root, query, criteriaBuilder) ->
                    criteriaBuilder.between(root.get(dbField), (Integer) from, (Integer) to);
        }

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get(dbField), (String) from, (String) to);
    }

    public static <T> Specification<TransactionEntity> hasLike(String dbField, T value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get(dbField), "%" + value + "%");
    }
}


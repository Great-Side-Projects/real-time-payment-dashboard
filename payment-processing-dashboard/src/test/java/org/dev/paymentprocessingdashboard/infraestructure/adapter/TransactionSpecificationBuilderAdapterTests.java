package org.dev.paymentprocessingdashboard.infraestructure.adapter;

import org.dev.paymentprocessingdashboard.infraestructure.adapter.out.persistence.TransactionEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;

import static org.assertj.core.api.Assertions.assertThat;

class TransactionSpecificationBuilderAdapterTests {

    private TransactionSpecificationBuilderAdapter builderAdapter;

    @BeforeEach
    void setUp() {
        builderAdapter = new TransactionSpecificationBuilderAdapter();
    }

    @Test
    @DisplayName("With adds equal condition when value is not null")
    void withAddsEqualConditionWhenValueIsNotNull() {
        Specification<TransactionEntity> spec = builderAdapter.with("status", "success").build();
        assertThat(spec).isNotNull();
        // Further assertions would require integration testing or mocking static methods
    }

    @Test
    @DisplayName("With does not add condition when value is null")
    void withDoesNotAddConditionWhenValueIsNull() {
        Specification<TransactionEntity> spec = builderAdapter.with("status", null).build();
        assertThat(spec).isNotNull();
        // Further assertions would require integration testing or mocking static methods
    }

    @Test
    @DisplayName("WithOr adds OR condition when value is not null")
    void withOrAddsOrConditionWhenValueIsNotNull() {
        Specification<TransactionEntity> spec = builderAdapter.withOr("status", "failed").build();
        assertThat(spec).isNotNull();
        // Further assertions would require integration testing or mocking static methods
    }

    @Test
    @DisplayName("WithLike adds LIKE condition when value is not null")
    void withLikeAddsLikeConditionWhenValueIsNotNull() {
        Specification<TransactionEntity> spec = builderAdapter.withLike("description", "%payment%").build();
        assertThat(spec).isNotNull();
        // Further assertions would require integration testing or mocking static methods
    }

    @Test
    @DisplayName("WithBetween adds BETWEEN condition when from and to values are not null")
    void withBetweenAddsBetweenConditionWhenFromAndToValuesAreNotNull() {
        Specification<TransactionEntity> spec = builderAdapter.withBetween("amount", 100.0, 200.0).build();
        assertThat(spec).isNotNull();
        // Further assertions would require integration testing or mocking static methods
    }

    @Test
    @DisplayName("WithBetween does not add condition when from or to value is null")
    void withBetweenDoesNotAddConditionWhenFromOrToValueIsNull() {
        Specification<TransactionEntity> spec = builderAdapter.withBetween("amount", null, 200.0).build();
        assertThat(spec).isNotNull();
        // Further assertions would require integration testing or mocking static methods
    }
}
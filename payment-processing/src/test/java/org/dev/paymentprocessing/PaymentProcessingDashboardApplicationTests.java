package org.dev.paymentprocessing;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PaymentProcessingDashboardApplicationTests {

    @Autowired
    private ApplicationContext context;

    @Test
    void applicationContextLoadsSuccessfully() {
        assertThat(context).isNotNull();
    }

    @Test
    void mainApplicationClassIsAnnotatedWithSpringBootApplication() {
        assertThat(PaymentProcessingDashboardApplication.class)
                .hasAnnotation(SpringBootApplication.class);
    }

    @Test
    void schedulingIsEnabledInApplication() {
        assertThat(PaymentProcessingDashboardApplication.class)
                .hasAnnotation(EnableScheduling.class);
    }

}

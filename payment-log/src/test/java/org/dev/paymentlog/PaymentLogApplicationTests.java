package org.dev.paymentlog;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PaymentLogApplicationTests {

    @Autowired
    private ApplicationContext context;
    @Test
    void applicationContextLoadsSuccessfully() {
        assertThat(context).isNotNull();
    }
}

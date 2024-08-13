package org.dev.paymentlog;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PaymentLogApplicationTests {

    @Test
    @DisplayName("Application context loads successfully")
    void applicationContextLoadsSuccessfully() {
        PaymentLogApplication.main(new String[]{});
    }
}

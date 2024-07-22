package org.dev.paymentprocessingdashboard.application.port.out;

import java.util.List;

public interface IJdbcTemplatePort<T> {
    void saveAll(List<T> transactions);
}

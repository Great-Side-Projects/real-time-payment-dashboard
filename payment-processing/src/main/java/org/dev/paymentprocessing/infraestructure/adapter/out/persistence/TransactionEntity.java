package org.dev.paymentprocessing.infraestructure.adapter.out.persistence;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import java.util.UUID;

@Getter
@Setter
@Table("transaction")
public class TransactionEntity {

    @Indexed
    private String eventId;

    @PrimaryKeyColumn(name = "id", type = PrimaryKeyType.PARTITIONED)
    private String id;

    @Indexed
    private String userid;

    @Indexed
    private double amount;

    @Indexed
    private String status;

    private String time;
    private String location;
}

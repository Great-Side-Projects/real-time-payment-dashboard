package org.dev.paymentlog.infraestructure.adapter.out.persistence;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import java.time.Instant;
import java.util.UUID;

@Setter
@Getter
@Table("log")
public class LogEntity {

    @PrimaryKeyColumn(name = "id",
            ordinal = 0,
            type = PrimaryKeyType.PARTITIONED)
    private UUID id = UUID.randomUUID();
    private String action;
    private String details;
    private Instant timestamp = Instant.now();
}

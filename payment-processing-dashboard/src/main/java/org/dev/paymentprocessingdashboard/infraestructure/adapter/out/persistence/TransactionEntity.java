package org.dev.paymentprocessingdashboard.infraestructure.adapter.out.persistence;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.cassandra.core.mapping.Indexed;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.UUID;

@Getter
@Setter
@Table("transaction")
public class TransactionEntity {

    @Id
    private UUID rowid = UUID.randomUUID();
    @Indexed
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

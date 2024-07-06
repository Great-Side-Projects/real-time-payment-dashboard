package org.dev.paymentprocessingdashboard.infraestructure.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(indexes = @Index(name = "uniqueIndex", columnList = "id", unique = true))
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqGen")
    @SequenceGenerator(name = "seqGen", sequenceName = "seq_transaction", initialValue = 1)
    private long rowid;
    private UUID id;
    private String userid;
    private double amount;
    private String status;
    private LocalDateTime timestamp;
    private String location;
}

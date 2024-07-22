package org.dev.paymentprocessingdashboard.infraestructure.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(indexes = @Index(name = "uniqueIndex", columnList = "id", unique = true))
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long rowid;
    private String id;
    private String userid;
    private double amount;
    private String status;
    private String time;
    private String location;
}

package org.dev.paymentprocessingdashboard.infraestructure.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.Setter;
import java.time.LocalDateTime;
@Setter
@Entity
public class ActionLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqGenLog")
    @SequenceGenerator(name = "seqGenLog", sequenceName = "seq_actionlog", initialValue = 1)
    private Long id;
    private String action;
    private String details;
    private LocalDateTime timestamp;

    // Getters and Setters
}

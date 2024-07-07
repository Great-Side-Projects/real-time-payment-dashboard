package org.dev.paymentprocessingdashboard.infraestructure.adapter.out.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Setter;
import java.time.LocalDateTime;
@Setter
@Entity
public class ActionLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String action;
    private String details;
    private LocalDateTime timestamp;

    // Getters and Setters
}

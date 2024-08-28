package org.dev.paymentlog.infraestructure.adapter.out.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Entity(name = "log")
public class LogEntity {

    @Id
    private String id = UUID.randomUUID().toString();
    private String action;
    private String details;
    private LocalDateTime createdAt = LocalDateTime.now();
}

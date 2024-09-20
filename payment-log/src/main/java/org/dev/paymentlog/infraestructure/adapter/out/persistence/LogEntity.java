package org.dev.paymentlog.infraestructure.adapter.out.persistence;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Setter
@Getter
//create index log_created_at on log(created_at);
@Entity(name = "log")
@Table(indexes = {
        @Index(name = "idx_created_at", columnList = "created_at")
})
public class LogEntity {

    @Id
    private String id = UUID.randomUUID().toString();
    private String action;
    @Column(columnDefinition="varchar(1000)")
    private String details;
    private LocalDateTime createdAt = LocalDateTime.now(ZoneOffset.UTC);
}

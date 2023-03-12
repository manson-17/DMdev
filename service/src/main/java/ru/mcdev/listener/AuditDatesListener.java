package ru.mcdev.listener;

import ru.mcdev.entity.AuditableEntity;

import javax.persistence.PrePersist;
import java.time.Instant;

public class AuditDatesListener {

    @PrePersist
    public void prePersist(AuditableEntity<?> entity) {
        entity.setCreatedAt(Instant.now());
    }
}

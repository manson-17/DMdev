package ru.mcdev.entity;

import lombok.Getter;
import lombok.Setter;
import ru.mcdev.listener.AuditDatesListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditDatesListener.class)
public abstract class AuditableEntity<T extends Serializable> implements BaseEntity<T> {

    //  @CreatedDate
    private Instant createdAt;

    //  @CreatedBy
    private String createdBy;
}

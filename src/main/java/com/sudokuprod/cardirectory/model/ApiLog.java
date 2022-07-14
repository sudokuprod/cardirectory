package com.sudokuprod.cardirectory.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "logs")
@AllArgsConstructor
@NoArgsConstructor
public class ApiLog extends BaseEntityWithId {
    @Column(nullable = false)
    private Date callTIme;
    @Column(nullable = false)
    private long executionTime;
    @Column
    private String path;
    @Column(nullable = false)
    private Boolean successful;
    @Column
    private String exMessage;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ApiLog apiLog = (ApiLog) o;
        return getId() != null && Objects.equals(getId(), apiLog.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

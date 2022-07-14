package com.sudokuprod.cardirectory.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Setter
@Getter
@ToString
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEntityWithId {

    @Id
    @JsonIgnore
    @GeneratedValue(generator = "CAR_DIRECTORY_SEQUENCE_GENERATOR")
    @Access(AccessType.PROPERTY)
    private Long id;

    @JsonIgnore
    @CreationTimestamp
    @Column(nullable = false)
    private Date creationTime;

    @JsonIgnore
    @UpdateTimestamp
    @Column(nullable = false)
    private Date updateTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BaseEntityWithId that = (BaseEntityWithId) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
package com.sudokuprod.cardirectory.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

@Getter
@Setter
@Entity
@ToString(callSuper = true)
@Table(name = "cars")
@NoArgsConstructor
@AllArgsConstructor
public class Car extends BaseEntityWithId {
    @Column(nullable = false, unique = true)
    @Pattern(regexp = "[А-ЯA-Z]{2}\\d{3}[А-ЯA-Z]\\d{2,3}", message = "Invalid car number format (Example: АА999А96)")
    private String number;
    @Column(nullable = false, length = 21)
    @Size(min = 3, max = 20, message = "Brand length must be in interval [3;20]")
    private String brand;
    @Column(nullable = false, length = 20)
    @Size(min = 3, max = 20, message = "Color length must be in interval [3;20]")
    private String color;
    @Column(nullable = false)
    @Min(value = 0, message = "IssueAt can not be less zero")
    private Integer issueAt;
    @Column(nullable = false)
    @Min(value = 0, message = "Mileage can not be less zero")
    private Integer mileage;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Car car = (Car) o;
        return getId() != null && Objects.equals(getId(), car.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

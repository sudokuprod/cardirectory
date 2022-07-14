package com.sudokuprod.cardirectory.repo;

import com.sudokuprod.cardirectory.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepo extends JpaRepository<Car, Long> {

    void removeByNumber(String number);

    Car getCarByNumber(String number);

    boolean existsByNumber(String number);
}

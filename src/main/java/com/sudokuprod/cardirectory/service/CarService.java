package com.sudokuprod.cardirectory.service;

import com.sudokuprod.cardirectory.model.Car;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CarService {
    List<Car> getAllCars();

    List<Car> getAllCars(Sort.Direction direction, List<String> fields);

    Car addCar(Car car);

    Car getCar(String number);

    void removeCar(String number);
}

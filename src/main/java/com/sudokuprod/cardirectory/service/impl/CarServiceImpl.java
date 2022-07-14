package com.sudokuprod.cardirectory.service.impl;

import com.sudokuprod.cardirectory.cache.CacheNames;
import com.sudokuprod.cardirectory.model.Car;
import com.sudokuprod.cardirectory.repo.CarRepo;
import com.sudokuprod.cardirectory.service.CarService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepo carRepo;

    @Override
    public List<Car> getAllCars() {
        return carRepo.findAll();
    }

    @Override
    public List<Car> getAllCars(Sort.Direction direction, List<String> fields) {
        return carRepo.findAll(Sort.by(direction, fields.toArray(String[]::new)));
    }

    @Override
    @CachePut(
            cacheNames = {CacheNames.CAR},
            key = "#car.number"
    )
    @Transactional
    public Car addCar(Car car) {
        if (!carRepo.existsByNumber(car.getNumber())) {
            log.info("Add car to db {}", car);
            return carRepo.save(car);
        }

        throw new EntityExistsException("Car with number " + car.getNumber() + " already exists");
    }

    @Override
    @Cacheable(
            cacheNames = {CacheNames.CAR},
            key = "#number"
    )
    public Car getCar(String number) {
        log.info("Get car from db by number {}", number);
        Car car = carRepo.getCarByNumber(number);
        if (Objects.isNull(car)) {
            throw new EntityNotFoundException("Car with number " + number + " doesn't exists");
        }

        return car;
    }

    @CacheEvict(
            cacheNames = {CacheNames.CAR},
            key = "#number"
    )
    @Override
    @Transactional
    public void removeCar(String number) {
        log.info("Remove car from db by number {}", number);
        boolean existsByNumber = carRepo.existsByNumber(number);
        if (!existsByNumber) {
            throw new EntityNotFoundException("Car with number " + number + " doesn't exists");
        }

        carRepo.removeByNumber(number);
    }
}

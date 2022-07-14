package com.sudokuprod.cardirectory.controller;

import com.sudokuprod.cardirectory.logging.LogApiCall;
import com.sudokuprod.cardirectory.model.Car;
import com.sudokuprod.cardirectory.pojo.OrderPojo;
import com.sudokuprod.cardirectory.service.CarService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/car")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @LogApiCall
    @ApiOperation("Get all cars")
    @ApiResponse(code = 200, response = Car.class, message = "List of all cars")
    @RequestMapping(method = RequestMethod.GET, path = "/get/all")
    public List<Car> getAll() {
        return carService.getAllCars();
    }

    @LogApiCall
    @ApiResponse(code = 200, response = Car.class, message = "List of all cars")
    @RequestMapping(method = RequestMethod.POST, path = "/get/all")
    public List<Car> getAll(@ApiParam(required = true) @RequestBody OrderPojo pojo) {
        return carService.getAllCars(pojo.getDirection(), pojo.getFields());
    }

    @LogApiCall
    @ApiResponse(code = 200, response = Car.class, message = "Adding success")
    @RequestMapping(method = RequestMethod.POST, path = "/add")
    public boolean addCar(@RequestBody Car car) {
        carService.addCar(car);
        return true;
    }

    @LogApiCall
    @ApiResponse(code = 200, response = Car.class, message = "Removal success")
    @RequestMapping(method = RequestMethod.DELETE, path = "/delete")
    public boolean removeCar(String number) {
        carService.removeCar(number);
        return true;
    }

    @LogApiCall
    @ApiResponse(code = 200, response = Car.class, message = "Сar by number")
    @RequestMapping(method = RequestMethod.GET, path = "/get")
    public Car getCar(@RequestParam String number) {
        return carService.getCar(number);
    }
}

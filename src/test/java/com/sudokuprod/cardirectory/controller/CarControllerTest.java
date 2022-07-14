package com.sudokuprod.cardirectory.controller;

import com.sudokuprod.cardirectory.model.Car;
import com.sudokuprod.cardirectory.pojo.ExceptionPojo;
import com.sudokuprod.cardirectory.pojo.OrderPojo;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@SpringBootTest
@TestMethodOrder(value = MethodOrderer.Random.class)
class CarControllerTest {

    @Autowired
    private ApplicationContext applicationContext;
    private WebTestClient webTestClient;

    @BeforeEach
    public void before() {
        webTestClient = WebTestClient
                .bindToApplicationContext(applicationContext)
                .build();
    }

    @Test
    void getAll() {
        List<Car> cars = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            Car car = new Car();
            car.setColor("RED" + i);
            car.setMileage(ThreadLocalRandom.current().nextInt(0, 100));
            car.setBrand("BWM");
            car.setNumber("AA" + ThreadLocalRandom.current().nextInt(100, 1000) + "A" + ThreadLocalRandom.current().nextInt(10, 100));
            car.setIssueAt(ThreadLocalRandom.current().nextInt(2000, 2023));
            cars.add(car);
        }

        cars.parallelStream().forEach(car -> {
            webTestClient
                    .post()
                    .uri(builder -> builder.path("/car/add").host("localhost").port(8080).build())
                    .bodyValue(car)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectBody(Boolean.TYPE)
                    .isEqualTo(Boolean.TRUE);
        });

        List<Car> fromServiceCars = webTestClient
                .get()
                .uri(builder -> builder.path("/car/get/all").host("localhost").port(8080).build())
                .exchange()
                .expectBody(new ParameterizedTypeReference<List<Car>>() {
                })
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(fromServiceCars);
        Assertions.assertEquals(cars.size(), fromServiceCars.size());

        for (Car car : cars) {
            webTestClient
                    .delete()
                    .uri(builder -> builder.path("/car/delete").host("localhost").queryParam("number", car.getNumber()).port(8080).build())
                    .exchange()
                    .expectStatus()
                    .isEqualTo(HttpStatus.OK)
                    .expectBody(Boolean.TYPE)
                    .isEqualTo(Boolean.TRUE);
        }
    }

    @Test
    void getAllSorted() {
        List<Car> cars = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            Car car = new Car();
            car.setColor("RED" + i);
            car.setMileage(ThreadLocalRandom.current().nextInt(0, 100));
            car.setBrand("BWM");
            car.setNumber("AA" + ThreadLocalRandom.current().nextInt(100, 1000) + "A" + ThreadLocalRandom.current().nextInt(10, 100));
            car.setIssueAt(ThreadLocalRandom.current().nextInt(2000, 2023));
            cars.add(car);
        }

        cars.parallelStream().forEach(car -> {
            webTestClient
                    .post()
                    .uri(builder -> builder.path("/car/add").host("localhost").port(8080).build())
                    .bodyValue(car)
                    .accept(MediaType.APPLICATION_JSON)
                    .exchange()
                    .expectBody(Boolean.TYPE)
                    .isEqualTo(Boolean.TRUE);
        });

        List<Car> sortedCars = cars
                .stream()
                .sorted(Comparator.comparing(Car::getNumber))
                .collect(Collectors.toList());

        List<Car> fromServiceCars = webTestClient
                .post()
                .uri(builder -> builder.path("/car/get/all").host("localhost").port(8080).build())
                .bodyValue(new OrderPojo(Sort.Direction.ASC, Collections.singletonList("number")))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectBody(new ParameterizedTypeReference<List<Car>>() {
                })
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(fromServiceCars);
        Assertions.assertEquals(sortedCars.size(), fromServiceCars.size());
        Assertions.assertIterableEquals(
                sortedCars.stream().map(Car::getNumber).collect(Collectors.toList()),
                fromServiceCars.stream().map(Car::getNumber).collect(Collectors.toList())
        );

        for (Car car : cars) {
            webTestClient
                    .delete()
                    .uri(builder -> builder.path("/car/delete").host("localhost").queryParam("number", car.getNumber()).port(8080).build())
                    .exchange()
                    .expectStatus()
                    .isEqualTo(HttpStatus.OK)
                    .expectBody(Boolean.TYPE)
                    .isEqualTo(Boolean.TRUE);
        }
    }

    @Test
    void addCar() {
        final Car car = new Car();
        car.setIssueAt(2018);
        car.setBrand("BWM");
        car.setNumber("AAA999A99");
        car.setMileage(0);
        car.setColor("RED");

        webTestClient
                .post()
                .uri(builder -> builder.path("/car/add").host("localhost").port(8080).build())
                .bodyValue(car)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectBody(Boolean.TYPE)
                .isEqualTo(Boolean.TRUE);

        webTestClient
                .delete()
                .uri(builder -> builder.path("/car/delete").host("localhost").queryParam("number", car.getNumber()).port(8080).build())
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.OK)
                .expectBody(Boolean.TYPE)
                .isEqualTo(Boolean.TRUE);
    }

    @Test
    void addDuplicateCar() {
        final Car car = new Car();
        car.setIssueAt(2018);
        car.setBrand("BWM");
        car.setNumber("AAA888A88");
        car.setMileage(0);
        car.setColor("RED");

        webTestClient
                .post()
                .uri(builder -> builder.path("/car/add").host("localhost").port(8080).build())
                .bodyValue(car)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.OK)
                .expectBody(Boolean.TYPE)
                .isEqualTo(Boolean.TRUE);

        webTestClient
                .post()
                .uri(builder -> builder.path("/car/add").host("localhost").port(8080).build())
                .bodyValue(car)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR)
                .expectBody(ExceptionPojo.class);

        webTestClient
                .delete()
                .uri(builder -> builder.path("/car/delete").host("localhost").queryParam("number", car.getNumber()).port(8080).build())
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.OK)
                .expectBody(Boolean.TYPE)
                .isEqualTo(Boolean.TRUE);
    }

    @Test
    void removeCar() {
        final Car car = new Car();
        car.setIssueAt(2018);
        car.setBrand("BWM");
        car.setNumber("AAA777A77");
        car.setMileage(0);
        car.setColor("RED");

        webTestClient
                .post()
                .uri(builder -> builder.path("/car/add").host("localhost").port(8080).build())
                .bodyValue(car)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.OK)
                .expectBody(Boolean.TYPE)
                .isEqualTo(Boolean.TRUE);

        deleteCar(car.getNumber())
                .expectStatus()
                .isEqualTo(HttpStatus.OK)
                .expectBody(Boolean.TYPE)
                .isEqualTo(Boolean.TRUE);
    }

    private WebTestClient.ResponseSpec deleteCar(String number) {
        return webTestClient
                .delete()
                .uri(builder -> builder.path("/car/delete").host("localhost").queryParam("number", number).port(8080).build())
                .exchange();
    }

    @Test
    void removeNotExistsCar() {
        webTestClient
                .delete()
                .uri(builder -> builder.path("/car/delete").host("localhost").queryParam("number", "KEKIS").port(8080).build())
                .exchange()
                .expectStatus()
                .isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR)
                .expectBody(ExceptionPojo.class);
    }

    @Test
    void getCar() {
        Car car = new Car();
        car.setColor("RED");
        car.setMileage(ThreadLocalRandom.current().nextInt(0, 100));
        car.setBrand("BWM");
        car.setNumber("AA" + ThreadLocalRandom.current().nextInt(100, 1000) + "A" + ThreadLocalRandom.current().nextInt(10, 100));
        car.setIssueAt(ThreadLocalRandom.current().nextInt(2000, 2023));

        webTestClient
                .post()
                .uri(builder -> builder.path("/car/add").host("localhost").port(8080).build())
                .bodyValue(car)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectBody(Boolean.TYPE)
                .isEqualTo(Boolean.TRUE);

        Car carFromGet = webTestClient
                .get()
                .uri(builder -> builder.path("/car/get").queryParam("number", car.getNumber()).host("localhost").port(8080).build())
                .exchange()
                .expectBody(new ParameterizedTypeReference<Car>() {
                })
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(carFromGet);

        deleteCar(car.getNumber())
                .expectStatus()
                .isEqualTo(HttpStatus.OK)
                .expectBody(Boolean.TYPE)
                .isEqualTo(Boolean.TRUE);
    }
}
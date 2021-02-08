package com.project.service.carservice;

import com.project.model.Car;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface CarInterface {

    ResponseEntity<List<Car>> getAllCars();

    ResponseEntity<Car> addCar(Car inputCar);

    ResponseEntity<Car> updateCar(Car inputCar);

    ResponseEntity<Car> getOneByID(String id);

    ResponseEntity<HttpStatus> deleteByID(String id);
}

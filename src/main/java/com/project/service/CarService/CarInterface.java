package com.project.service.carservice;

import com.project.model.Car;
import com.project.exception.ServiceOperationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import javax.servlet.ServletException;
import java.util.List;

public interface CarInterface {

    ResponseEntity<List<Car>> getAllCars();

    ResponseEntity<?> addCar(Car inputCar) throws ServletException, ServiceOperationException;

    ResponseEntity<?> modifyCar(Car inputCar) throws ServletException, ServiceOperationException;

    ResponseEntity<Car> getOneByID(String id);

    ResponseEntity<HttpStatus> deleteByID(String id);
}

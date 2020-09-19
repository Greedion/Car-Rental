package com.project.demo.Service.CarService;

import com.project.demo.DataTransferObject.CarDTA;
import org.springframework.http.ResponseEntity;

import javax.servlet.ServletException;

public interface CarInterface {

    ResponseEntity<?> getAllCars();
    ResponseEntity<?> addCar(CarDTA inputCarDTA) throws ServletException;
    ResponseEntity<?> modifyCar(CarDTA inputCarDTA) throws ServletException;
    ResponseEntity<?> getOneByID(String id);
    ResponseEntity<?> deleteByID(String id);
}

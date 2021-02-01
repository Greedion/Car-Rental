package com.project.demo.Service.CarService;
import com.project.demo.DataTransferObject.CarDTO;
import org.springframework.http.ResponseEntity;
import javax.servlet.ServletException;

public interface CarInterface {
    ResponseEntity<?> getAllCars();

    ResponseEntity<?> addCar(CarDTO inputCarDTO) throws ServletException;

    ResponseEntity<?> modifyCar(CarDTO inputCarDTO) throws ServletException;

    ResponseEntity<?> getOneByID(String id);

    ResponseEntity<?> deleteByID(String id);
}

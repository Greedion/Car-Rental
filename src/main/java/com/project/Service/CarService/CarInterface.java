package com.project.Service.CarService;
import com.project.DataTransferObject.CarDTO;
import com.project.Exception.ServiceOperationException;
import org.springframework.http.ResponseEntity;
import javax.servlet.ServletException;

public interface CarInterface {

    ResponseEntity<?> getAllCars();

    ResponseEntity<?> addCar(CarDTO inputCarDTO) throws ServletException, ServiceOperationException;

    ResponseEntity<?> modifyCar(CarDTO inputCarDTO) throws ServletException, ServiceOperationException;

    ResponseEntity<?> getOneByID(String id);

    ResponseEntity<?> deleteByID(String id);
}

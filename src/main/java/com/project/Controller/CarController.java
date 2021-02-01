package com.project.Controller;

import com.project.DataTransferObject.CarDTO;
import com.project.Exception.ServiceOperationException;
import com.project.Service.CarService.CarServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/car/")
public class CarController {

    private final CarServiceImpl carService;

    public CarController(CarServiceImpl carService) {
        this.carService = carService;
    }

    @GetMapping(value = "getAll")
    ResponseEntity<?> returnAllCars() {
        return carService.getAllCars();
    }

    @PostMapping(value = "add")
    ResponseEntity<?> addCar(@Valid @RequestBody CarDTO inputCarDTO, BindingResult result) throws ServiceOperationException {
        if (result.hasErrors()) {
            return new ResponseEntity<>(hadErrors(result), HttpStatus.BAD_REQUEST);
        }
        return carService.addCar(inputCarDTO);
    }

    @PutMapping(value = "update")
    ResponseEntity<?> updateCar(@RequestBody CarDTO inputCarDTO, BindingResult result) throws ServiceOperationException {
        if (result.hasErrors()) {
            return new ResponseEntity<>(hadErrors(result), HttpStatus.BAD_REQUEST);
        }
        return carService.modifyCar(inputCarDTO);
    }

    @GetMapping(value = "getOne/{id}")
    ResponseEntity<?> getOneByID(@PathVariable String id) {
        return carService.getOneByID(id);
    }

    @DeleteMapping(value = "delete/{id}")
    ResponseEntity<?> deleteByID(@PathVariable String id) {
        return carService.deleteByID(id);
    }


    private Map<String, String> hadErrors(BindingResult result) {
        Map<String, String> errorMap = new HashMap<>();

        for (FieldError error : result.getFieldErrors()
        ) {
            errorMap.put(error.getField(), error.getDefaultMessage());
        }
        return errorMap;
    }
}

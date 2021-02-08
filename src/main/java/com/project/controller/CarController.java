package com.project.controller;

import com.project.model.Car;
import com.project.exception.ServiceOperationException;
import com.project.repository.CarRepository;
import com.project.service.carservice.CarServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/car")
public class CarController {

    private final Logger logger = LoggerFactory.getLogger(CarController.class);
    private final CarServiceImpl carService;
    private final CarRepository carRepository;

    public CarController(CarServiceImpl carService, CarRepository carRepository) {
        this.carService = carService;
        this.carRepository = carRepository;
    }

    @ApiOperation(value = "Get all cars.")
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Car>> getAllCars() {
        return carService.getAllCars();
    }

    @ApiOperation(value = "Get a single car by id.")
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Car> getOneByID(@PathVariable String id) {
        if(id == null)
        {
            logger.error("Attempt get car with empty input data.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attempt get car with empty input data.");
        }
        try {
            if (carRepository.existsById(Long.parseLong(id))) {
                return carService.getOneByID(id);
            } else {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Expected data not found.");
            }
        } catch (NumberFormatException e) {
            logger.error("Attempt parse String to Long");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attempt parse String to Long.");
        }
    }

    @ApiOperation(value = "Add car.", notes = "Needed authorization from Admin account")
    @PostMapping(produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addCar(@Valid @RequestBody Car inputCar, BindingResult result) throws ServiceOperationException {
        if (inputCar == null) {
            logger.error("Attempt adding car with empty input data.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attempt adding car with empty input data.");
        } else if (inputCar.getBrand() == null || inputCar.getDescription() == null || inputCar.getPricePerHour() == null) {
            logger.error("Attempt adding car with empty input data.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attempt adding car with empty input data.");
        } else if (result.hasErrors()) {
            logger.error("Attempt to add Car with wrong data structure.");
            return new ResponseEntity<>(hadErrors(result), HttpStatus.BAD_REQUEST);
        } else
            return carService.addCar(inputCar);
    }

    @ApiOperation(value = "Update car.", notes = "Needed authorization from Admin account")
    @PutMapping(produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateCar(@Valid @RequestBody Car inputCar, BindingResult result) throws ServiceOperationException {
        if (inputCar == null) {
            logger.error("Attempt update car with empty input data.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attempt update car with empty input data.");
        } else if (inputCar.getBrand() == null ||
                inputCar.getDescription() == null ||
                inputCar.getPricePerHour() == null ||
                inputCar.getId() == null) {
            logger.error("Attempt update car with empty input data.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attempt update car with empty input data.");
        } else if (result.hasErrors()) {
            logger.error("Attempt to update Car with wrong data structure.");
            return new ResponseEntity<>(hadErrors(result), HttpStatus.BAD_REQUEST);
        } else
            return carService.modifyCar(inputCar);
    }

    @ApiOperation(value = "Delete car.", notes = "Needed authorization from Admin account")
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteByID(@PathVariable String id) {
        try {
            if (carRepository.existsById(Long.parseLong(id))) {
                return carService.deleteByID(id);
            } else {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Expected data not found.");
            }
        } catch (NumberFormatException e) {
            logger.error("Attempt parse String to Long");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attempt parse String to Long.");
        }
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

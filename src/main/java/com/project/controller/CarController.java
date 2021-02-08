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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/car")
public class CarController {

    private final Logger logger = LoggerFactory.getLogger(CarController.class);
    private final CarServiceImpl carService;
    private final CarRepository carRepository;

    private static final String ID_COULD_NOT_BE_NULL = "Id could not be null";
    private static final String EXPECTED_DATA_NOT_FOUND = "Attempt to remove car by id that does not exist in database.";
    private static final String NOT_FOUND_EXCEPTION = "Attempt to get car by id that does not exist in database.";
    private static final String PARSE_EXCEPTION = "Attempt parse String to Long.";


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
        try {
            if (id == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ID_COULD_NOT_BE_NULL);
            } else if (carRepository.existsById(Long.parseLong(id))) {
                return carService.getOneByID(id);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_EXCEPTION);
            }
        } catch (NumberFormatException e) {
            logger.error(PARSE_EXCEPTION);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, PARSE_EXCEPTION);
        }
    }

    @ApiOperation(value = "Add car.", notes = "Needed authorization from Admin account")
    @PostMapping(produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Car> addCar(@Valid @RequestBody Car inputCar) {
        return carService.addCar(inputCar);
    }

    @ApiOperation(value = "Update car.", notes = "Needed authorization from Admin account")
    @PutMapping(value = "/{inputId}", produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Car> updateCar(@Valid @RequestBody Car inputCar, @PathVariable String inputId) {
        inputCar.setId(Optional.ofNullable(inputId).orElse("0"));
        return carService.updateCar(inputCar);
    }

    @ApiOperation(value = "Delete car.", notes = "Needed authorization from Admin account")
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteByID(@PathVariable String id) {
        try {
            if (id == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ID_COULD_NOT_BE_NULL);
            } else if (carRepository.existsById(Long.parseLong(id))) {
                return carService.deleteByID(id);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, EXPECTED_DATA_NOT_FOUND);
            }
        } catch (NumberFormatException | ServiceOperationException e) {
            logger.error(PARSE_EXCEPTION);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, PARSE_EXCEPTION);
        }
    }
}

package com.project.Controller;

import com.project.DataTransferObject.CarDTO;
import com.project.Exception.ServiceOperationException;
import com.project.Repository.CarRepository;
import com.project.Service.CarService.CarServiceImpl;
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

    @GetMapping(produces = "application/json")
    ResponseEntity<List<CarDTO>> getAllCars() {
        return carService.getAllCars();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    ResponseEntity<CarDTO> getOneByID(@PathVariable String id) {
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

    @PostMapping(produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> addCar(@Valid @RequestBody CarDTO inputCarDTO, BindingResult result) throws ServiceOperationException {
        if (inputCarDTO == null) {
            logger.error("Attempt adding car with empty input data.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attempt adding car with empty input data.");
        } else if (inputCarDTO.getBrand() == null || inputCarDTO.getDescription() == null || inputCarDTO.getPricePerHour() == null) {
            logger.error("Attempt adding car with empty input data.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attempt adding car with empty input data.");
        } else if (result.hasErrors()) {
            logger.error("Attempt to add Car with wrong data structure.");
            return new ResponseEntity<>(hadErrors(result), HttpStatus.BAD_REQUEST);
        } else
            return carService.addCar(inputCarDTO);
    }

    @PutMapping(produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> updateCar(@RequestBody CarDTO inputCarDTO, BindingResult result) throws ServiceOperationException {

        if (inputCarDTO == null) {
            logger.error("Attempt update car with empty input data.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attempt update car with empty input data.");
        } else if (inputCarDTO.getBrand() == null ||
                inputCarDTO.getDescription() == null ||
                inputCarDTO.getPricePerHour() == null ||
                inputCarDTO.getId() == null) {
            logger.error("Attempt update car with empty input data.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attempt update car with empty input data.");
        } else if (result.hasErrors()) {
            logger.error("Attempt to update Car with wrong data structure.");
            return new ResponseEntity<>(hadErrors(result), HttpStatus.BAD_REQUEST);
        } else
            return carService.modifyCar(inputCarDTO);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> deleteByID(@PathVariable String id) {
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

package com.project.demo.Controller;
import com.project.demo.DataTransferObject.CarDTA;
import com.project.demo.Service.CarService.CarServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("car")
public class CarController {

    CarServiceImpl carService;

    @Autowired
    public CarController(CarServiceImpl carService) {
        this.carService = carService;
    }

    @GetMapping(value = "getAll")
    ResponseEntity<?> returnAllCars() {
        return carService.getAllCars();
    }

    @PostMapping(value = "add")
    ResponseEntity<?> addCar(@Valid @RequestBody CarDTA inputCarDTA, BindingResult result) throws ServletException {
        if (result.hasErrors()) {
            return new ResponseEntity<>(hadErrors(result), HttpStatus.BAD_REQUEST);
        }
        return carService.addCar(inputCarDTA);
    }

    @PutMapping(value = "update")
    ResponseEntity<?> updateCar(@RequestBody CarDTA inputCarDTA, BindingResult result) throws ServletException {
        if (result.hasErrors()) {
            return new ResponseEntity<>(hadErrors(result), HttpStatus.BAD_REQUEST);
        }
        return carService.modifyCar(inputCarDTA);
    }

    @PostMapping(value = "getOne")
    ResponseEntity<?> getOneByID(@RequestParam String id) {
        return carService.getOneByID(id);
    }

    @DeleteMapping(value = "delete")
    ResponseEntity<?> deleteByID(@RequestParam String id) {
        return carService.deleteByID(id);
    }


    Map<String, String> hadErrors(BindingResult result) {
        Map<String, String> errorMap = new HashMap<>();

        for (FieldError error : result.getFieldErrors()
        ) {
            errorMap.put(error.getField(), error.getDefaultMessage());
        }
        return errorMap;
    }
}

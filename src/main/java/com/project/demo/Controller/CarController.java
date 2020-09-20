package com.project.demo.Controller;
import com.project.demo.DataTransferObject.CarDTA;
import com.project.demo.Service.CarService.CarServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.ServletException;

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
    ResponseEntity<?> addCar(@RequestBody CarDTA inputCarDTA) throws ServletException {
        return carService.addCar(inputCarDTA);
    }

    @PutMapping(value = "update")
    ResponseEntity<?> updateCar(@RequestBody CarDTA inputCarDTA) throws ServletException {
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
}

package com.project.demo.Service.CarService;
import com.project.demo.DataTransferObject.CarDTA;
import com.project.demo.Entity.BrandEntity;
import com.project.demo.Entity.CarEntity;
import com.project.demo.Respository.BrandRepository;
import com.project.demo.Respository.CarRepository;
import com.project.demo.Utils.CarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarInterface {

    private final String EXCEPTION_ALERT = "Wrong input data format";
    CarRepository carRepository;
    BrandRepository brandRepository;
    CarMapper carMapper;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, BrandRepository brandRepository, CarMapper carMapper) {
        this.carRepository = carRepository;
        this.brandRepository = brandRepository;
        this.carMapper = carMapper;
    }

    public ResponseEntity<?> getAllCars() {
        List<CarEntity> carsFromDatabase = carRepository.findAll();
        List<CarDTA> carsDTA = new ArrayList<>();

        for (CarEntity x : carsFromDatabase
        ) {
            carsDTA.add(carMapper.mapperFroMCarEntityToCarDTA(x));
        }
        return ResponseEntity.ok(carsDTA);
    }

    public ResponseEntity<?> addCar(CarDTA inputCarDTA) throws ServletException {
        CarEntity carEntity = carMapper.mapperFromCarDTAToCarEntity(inputCarDTA);
        if (carEntity != null) {
            carRepository.save(carEntity);
            return ResponseEntity.ok().build();
        } else return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<?> modifyCar(CarDTA inputCarDTA) throws ServletException {
        if (carRepository.existsById(Long.parseLong(inputCarDTA.getId()))) {
            Optional<CarEntity> carEntity = carRepository.findById(Long.parseLong(inputCarDTA.getId()));
            if (carEntity.isPresent()) {
                try {
                    if (Long.parseLong(inputCarDTA.getBrand()) != carEntity.get().getBrand().getId()) {
                        Optional<BrandEntity> newBrand = brandRepository.findById(Long.parseLong(inputCarDTA.getBrand()));
                        newBrand.ifPresent(brandEntity -> carEntity.get().setBrand(brandEntity));
                        if (!newBrand.isPresent())
                            return ResponseEntity.badRequest().build();
                    }
                    if (inputCarDTA.getDescription() != null && !inputCarDTA.getDescription().equals("")) {
                        if (!carEntity.get().getDescription().equals(inputCarDTA.getDescription()))
                            carEntity.get().setDescription(inputCarDTA.getDescription());
                    }
                    if (inputCarDTA.getPricePerHour() != null && !inputCarDTA.getPricePerHour().equals("")) {
                        if (!carEntity.get().getPricePerHour().equals(Double.parseDouble(inputCarDTA.getPricePerHour())))
                            carEntity.get().setPricePerHour(Double.parseDouble(inputCarDTA.getPricePerHour()));
                    }
                } catch (NumberFormatException e) {
                    throw new ServletException(EXCEPTION_ALERT);
                }
                carRepository.save(carEntity.get());
                return ResponseEntity.ok().build();
            } else return ResponseEntity.badRequest().build();
        } else return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<?> getOneByID(String id) {
        if (carRepository.existsById(Long.parseLong(id))) {
            Optional<CarEntity> carEntity = carRepository.findById(Long.parseLong(id));
            if (carEntity.isPresent()) {
                return ResponseEntity.ok(carMapper.mapperFroMCarEntityToCarDTA(carEntity.get()));
            }
        }
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<?> deleteByID(String id) {
        if (carRepository.existsById(Long.parseLong(id))) {
            Optional<CarEntity> carEntity = carRepository.findById(Long.parseLong(id));
            if (carEntity.isPresent()) {
                carRepository.delete(carEntity.get());
                return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }

}

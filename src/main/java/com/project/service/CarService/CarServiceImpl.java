package com.project.service.carservice;

import com.project.model.Car;
import com.project.entity.BrandEntity;
import com.project.entity.CarEntity;
import com.project.exception.ServiceOperationException;
import com.project.repository.BrandRepository;
import com.project.repository.CarRepository;
import com.project.utils.CarMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarInterface {

    private final Logger logger = LoggerFactory.getLogger(CarServiceImpl.class);
    private static final String EXCEPTION_ALERT = "Wrong input data format Exception";

    private final CarRepository carRepository;
    private final BrandRepository brandRepository;
    private final CarMapper carMapper;

    public CarServiceImpl(CarRepository carRepository, BrandRepository brandRepository, CarMapper carMapper) {
        this.carRepository = carRepository;
        this.brandRepository = brandRepository;
        this.carMapper = carMapper;
    }

    public ResponseEntity<List<Car>> getAllCars() {
        List<CarEntity> carsFromDatabase = carRepository.findAll();
        List<Car> carsDTA = new ArrayList<>();

        for (CarEntity x : carsFromDatabase
        ) {
            carsDTA.add(carMapper.mapperFroMCarEntityToCarDTO(x));
        }
        return ResponseEntity.ok(carsDTA);
    }

    public ResponseEntity<?> addCar(Car inputCar) throws ServiceOperationException {
        try {
            CarEntity carEntity = carMapper.mapperFromCarDTOToCarEntity(inputCar);

        if (carEntity != null) {
            carRepository.save(carEntity);
            return ResponseEntity.ok().build();
        } else return ResponseEntity.badRequest().build();
        }catch (ServletException e){
            logger.error("Attempt mapping object CarEntity to CarDTO.");
            throw new ServiceOperationException("Mapping from CarDTO to CarEntity Exception");
        }
    }

    public ResponseEntity<?> modifyCar(Car inputCar) throws ServiceOperationException {
            if (carRepository.existsById(Long.parseLong(inputCar.getId()))) {
                Optional<CarEntity> carEntity = carRepository.findById(Long.parseLong(inputCar.getId()));
                if (carEntity.isPresent()) {
                    try {
                        if (Long.parseLong(inputCar.getBrand()) != carEntity.get().getBrand().getId()) {
                            Optional<BrandEntity> newBrand = brandRepository.findById(Long.parseLong(inputCar.getBrand()));
                            newBrand.ifPresent(brandEntity -> carEntity.get().setBrand(brandEntity));
                            if (!newBrand.isPresent())
                                return ResponseEntity.badRequest().build();
                        }
                        if (inputCar.getDescription() != null && !inputCar.getDescription().equals("")) {
                            if (!carEntity.get().getDescription().equals(inputCar.getDescription()))
                                carEntity.get().setDescription(inputCar.getDescription());
                        }
                        if (inputCar.getPricePerHour() != null && !inputCar.getPricePerHour().equals("")) {
                            if (!carEntity.get().getPricePerHour().equals(Double.parseDouble(inputCar.getPricePerHour())))
                                carEntity.get().setPricePerHour(Double.parseDouble(inputCar.getPricePerHour()));
                        }
                    } catch (NumberFormatException e) {
                        logger.error("Attempt parse id / brand from String to Long.");
                        throw new ServiceOperationException(EXCEPTION_ALERT);
                    }
                    carRepository.save(carEntity.get());
                    return ResponseEntity.ok().build();
                } else {
                    logger.error("Attempt to use an empty CarEntity");
                    return ResponseEntity.badRequest().build();
                }
            } else {
                logger.error("Attempt to modify car using a non-existent id");
                return ResponseEntity.badRequest().build();
            }
    }

    public ResponseEntity<Car> getOneByID(String id) {
        if (carRepository.existsById(Long.parseLong(id))) {
            Optional<CarEntity> carEntity = carRepository.findById(Long.parseLong(id));
            if (carEntity.isPresent()) {
                return ResponseEntity.ok(carMapper.mapperFroMCarEntityToCarDTO(carEntity.get()));
            }
        }
        logger.error("Attempt to get car using a non-existent id");
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<HttpStatus> deleteByID(String id) {
        if (carRepository.existsById(Long.parseLong(id))) {
            Optional<CarEntity> carEntity = carRepository.findById(Long.parseLong(id));
            if (carEntity.isPresent()) {
                carRepository.delete(carEntity.get());
                return ResponseEntity.ok().build();
            }
        }
        logger.error("Attempt to delete car using a non-existent id");
        return ResponseEntity.badRequest().build();
    }

}

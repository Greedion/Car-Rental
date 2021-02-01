package com.project.Service.CarService;

import com.project.DataTransferObject.CarDTO;
import com.project.Entity.BrandEntity;
import com.project.Entity.CarEntity;
import com.project.Exception.ServiceOperationException;
import com.project.Repository.BrandRepository;
import com.project.Repository.CarRepository;
import com.project.Utils.CarMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarServiceImpl implements CarInterface {

    private final static String EXCEPTION_ALERT = "Wrong input data format Exception";

    private final CarRepository carRepository;
    private final BrandRepository brandRepository;
    private final CarMapper carMapper;

    public CarServiceImpl(CarRepository carRepository, BrandRepository brandRepository, CarMapper carMapper) {
        this.carRepository = carRepository;
        this.brandRepository = brandRepository;
        this.carMapper = carMapper;
    }

    public ResponseEntity<?> getAllCars() {
        List<CarEntity> carsFromDatabase = carRepository.findAll();
        List<CarDTO> carsDTA = new ArrayList<>();

        for (CarEntity x : carsFromDatabase
        ) {
            carsDTA.add(carMapper.mapperFroMCarEntityToCarDTO(x));
        }
        return ResponseEntity.ok(carsDTA);
    }

    public ResponseEntity<?> addCar(CarDTO inputCarDTO) throws ServiceOperationException {
        try {
            CarEntity carEntity = carMapper.mapperFromCarDTOToCarEntity(inputCarDTO);

        if (carEntity != null) {
            carRepository.save(carEntity);
            return ResponseEntity.ok().build();
        } else return ResponseEntity.badRequest().build();
        }catch (ServletException e){
            throw new ServiceOperationException("Mapping from CarDTO to CarEntity Exception");
        }
    }

    public ResponseEntity<?> modifyCar(CarDTO inputCarDTO) throws ServiceOperationException {
            if (carRepository.existsById(Long.parseLong(inputCarDTO.getId()))) {
                Optional<CarEntity> carEntity = carRepository.findById(Long.parseLong(inputCarDTO.getId()));
                if (carEntity.isPresent()) {
                    try {
                        if (Long.parseLong(inputCarDTO.getBrand()) != carEntity.get().getBrand().getId()) {
                            Optional<BrandEntity> newBrand = brandRepository.findById(Long.parseLong(inputCarDTO.getBrand()));
                            newBrand.ifPresent(brandEntity -> carEntity.get().setBrand(brandEntity));
                            if (!newBrand.isPresent())
                                return ResponseEntity.badRequest().build();
                        }
                        if (inputCarDTO.getDescription() != null && !inputCarDTO.getDescription().equals("")) {
                            if (!carEntity.get().getDescription().equals(inputCarDTO.getDescription()))
                                carEntity.get().setDescription(inputCarDTO.getDescription());
                        }
                        if (inputCarDTO.getPricePerHour() != null && !inputCarDTO.getPricePerHour().equals("")) {
                            if (!carEntity.get().getPricePerHour().equals(Double.parseDouble(inputCarDTO.getPricePerHour())))
                                carEntity.get().setPricePerHour(Double.parseDouble(inputCarDTO.getPricePerHour()));
                        }
                    } catch (NumberFormatException e) {
                        throw new ServiceOperationException(EXCEPTION_ALERT);
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
                return ResponseEntity.ok(carMapper.mapperFroMCarEntityToCarDTO(carEntity.get()));
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

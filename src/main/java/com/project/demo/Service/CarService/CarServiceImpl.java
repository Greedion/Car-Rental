package com.project.demo.Service.CarService;
import com.project.demo.DataTransferObject.CarDTO;
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
        List<CarDTO> carsDTA = new ArrayList<>();

        for (CarEntity x : carsFromDatabase
        ) {
            carsDTA.add(carMapper.mapperFroMCarEntityToCarDTA(x));
        }
        return ResponseEntity.ok(carsDTA);
    }

    public ResponseEntity<?> addCar(CarDTO inputCarDTO) throws ServletException {
        CarEntity carEntity = carMapper.mapperFromCarDTAToCarEntity(inputCarDTO);
        if (carEntity != null) {
            carRepository.save(carEntity);
            return ResponseEntity.ok().build();
        } else return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<?> modifyCar(CarDTO inputCarDTO) throws ServletException {
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

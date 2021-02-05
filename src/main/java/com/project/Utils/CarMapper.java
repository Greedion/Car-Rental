package com.project.utils;
import com.project.entity.BrandEntity;
import com.project.entity.CarEntity;
import com.project.model.Car;
import com.project.repository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import java.util.Optional;

@Component
public class CarMapper {

    BrandRepository brandRepository;
    private static final String EXCEPTION_ALERT = "Wrong input data format";

    @Autowired
    public CarMapper(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public CarEntity mapperFromCarDTOToCarEntity(Car inputCar) throws ServletException {
        Optional<BrandEntity> brand = brandRepository.findById(Long.parseLong(inputCar.getBrand()));
        try {
            if (brand.isPresent()) {
                if (inputCar.getId() == null || inputCar.getId().equals(""))
                    return new CarEntity(null, brand.get(), inputCar.getDescription(), Double.parseDouble(inputCar.getPricePerHour()));
                else
                    return new CarEntity(Long.parseLong(inputCar.getId()), brand.get(), inputCar.getDescription(), Double.parseDouble(inputCar.getPricePerHour()));
            } else return null;
        } catch (NumberFormatException e) {
            throw new ServletException(EXCEPTION_ALERT);
        }
    }

    public Car mapperFroMCarEntityToCarDTO(CarEntity inputCarEntity) {
        return new Car(String.valueOf(inputCarEntity.getId()), String.valueOf(inputCarEntity.getBrand().getId()), inputCarEntity.getDescription(), String.valueOf(inputCarEntity.getPricePerHour()));
    }
}

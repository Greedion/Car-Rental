package com.project.demo.Utils;
import com.project.demo.DataTransferObject.CarDTA;
import com.project.demo.Entity.BrandEntity;
import com.project.demo.Entity.CarEntity;
import com.project.demo.Respository.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.servlet.ServletException;
import java.util.Optional;

@Component
public class CarMapper {

    BrandRepository brandRepository;
    private final String EXCEPTION_ALERT = "Wrong input data format";

    @Autowired
    public CarMapper(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public CarEntity mapperFromCarDTAToCarEntity(CarDTA inputCarDTA) throws ServletException {
        Optional<BrandEntity> brand = brandRepository.findById(Long.parseLong(inputCarDTA.getBrand()));
        try {
            if (brand.isPresent()) {
                if (inputCarDTA.getId() == null || inputCarDTA.getId().equals(""))
                    return new CarEntity(null, brand.get(), inputCarDTA.getDescription(), Double.parseDouble(inputCarDTA.getPricePerHour()));
                else
                    return new CarEntity(Long.parseLong(inputCarDTA.getId()), brand.get(), inputCarDTA.getDescription(), Double.parseDouble(inputCarDTA.getPricePerHour()));
            } else return null;
        } catch (NumberFormatException e) {
            throw new ServletException(EXCEPTION_ALERT);
        }
    }

    public CarDTA mapperFroMCarEntityToCarDTA(CarEntity inputCarEntity) {
        return new CarDTA(String.valueOf(inputCarEntity.getId()), String.valueOf(inputCarEntity.getBrand().getId()), inputCarEntity.getDescription(), String.valueOf(inputCarEntity.getPricePerHour()));
    }
}

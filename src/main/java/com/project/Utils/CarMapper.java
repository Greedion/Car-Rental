package com.project.Utils;
import com.project.Entity.BrandEntity;
import com.project.Entity.CarEntity;
import com.project.DataTransferObject.CarDTO;
import com.project.Repository.BrandRepository;
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

    public CarEntity mapperFromCarDTAToCarEntity(CarDTO inputCarDTO) throws ServletException {
        Optional<BrandEntity> brand = brandRepository.findById(Long.parseLong(inputCarDTO.getBrand()));
        try {
            if (brand.isPresent()) {
                if (inputCarDTO.getId() == null || inputCarDTO.getId().equals(""))
                    return new CarEntity(null, brand.get(), inputCarDTO.getDescription(), Double.parseDouble(inputCarDTO.getPricePerHour()));
                else
                    return new CarEntity(Long.parseLong(inputCarDTO.getId()), brand.get(), inputCarDTO.getDescription(), Double.parseDouble(inputCarDTO.getPricePerHour()));
            } else return null;
        } catch (NumberFormatException e) {
            throw new ServletException(EXCEPTION_ALERT);
        }
    }

    public CarDTO mapperFroMCarEntityToCarDTA(CarEntity inputCarEntity) {
        return new CarDTO(String.valueOf(inputCarEntity.getId()), String.valueOf(inputCarEntity.getBrand().getId()), inputCarEntity.getDescription(), String.valueOf(inputCarEntity.getPricePerHour()));
    }
}

package com.project.Service.BrandService;
import com.project.DataTransferObject.BrandDTO;
import com.project.Entity.BrandEntity;
import com.project.Exception.GlobalExceptionHandler;
import com.project.Exception.ServiceOperationException;
import com.project.Repository.BrandRepository;
import com.project.Repository.CarRepository;
import com.project.Utils.BrandMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BrandServiceImpl implements BrandInterface {

    private final Logger logger = LoggerFactory.getLogger(BrandServiceImpl.class);
    private final BrandRepository brandRepository;
    private final CarRepository carRepository;
    public BrandServiceImpl(BrandRepository brandRepository, CarRepository carRepository) {
        this.brandRepository = brandRepository;
        this.carRepository = carRepository;
    }

    public ResponseEntity<List<BrandDTO>> getAllBrands() {
        List<BrandEntity> brandsFromDatabase = brandRepository.findAll();
        List<BrandDTO> brandsDTA = new ArrayList<>();

        for (BrandEntity x : brandsFromDatabase
        ) {
            brandsDTA.add(BrandMapper.mapperFromBrandEntityToBrandDTA(x));
        }
        return ResponseEntity.ok(brandsDTA);
    }

    public ResponseEntity<?> addBrand(BrandDTO inputBrandDTO) {
        BrandEntity brandForSave = BrandMapper.mapperFromBrandDTAToBrandEntity(inputBrandDTO);
        brandRepository.save(brandForSave);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> modifyBrand(BrandDTO inputBrandDTO) {
        if (brandRepository.existsById(Long.parseLong(inputBrandDTO.getId()))) {
            Optional<BrandEntity> brandForSave = brandRepository.findById(Long.parseLong(inputBrandDTO.getId()));
            if (brandForSave.isPresent()) {
                if (inputBrandDTO.getBrand() != null && !inputBrandDTO.getBrand().equals("")) {
                    if (!inputBrandDTO.getBrand().equals(brandForSave.get().getBrand())) {
                        brandForSave.get().setBrand(inputBrandDTO.getBrand());
                        brandRepository.save(brandForSave.get());
                        return ResponseEntity.ok().build();
                    }
                }
            }
        }
        logger.error("Attempt to modify the brand using a non-existent id");
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<BrandDTO> getOneByID(String id) {
        if (brandRepository.existsById(Long.parseLong(id))) {
            Optional<BrandEntity> brandObject = brandRepository.findById(Long.parseLong(id));
            if (brandObject.isPresent())
                return ResponseEntity.ok(BrandMapper.mapperFromBrandEntityToBrandDTA(brandObject.get()));
        }
        logger.error("Attempt to get the brand using a non-existent id");
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<?> deleteByID(String id) throws ServiceOperationException {
        if (brandRepository.existsById(Long.parseLong(id))) {
            Optional<BrandEntity> brandObject = brandRepository.findById(Long.parseLong(id));
            if (brandObject.isPresent()) {
                if(checkConstraintsOfIntegrityForBrand(Long.parseLong(id))) {
                    brandRepository.delete(brandObject.get());
                    return ResponseEntity.ok().build();
                }else{
                    logger.error("Attempt remove brand with assigned binding.");

                    throw new ResponseStatusException(HttpStatus.CONFLICT,
                            "Attempt remove brand with assigned binding.");
                }
            }
        }
        logger.error("Attempt to delete the brand using a non-existent id");
        return ResponseEntity.badRequest().build();
    }

    boolean checkConstraintsOfIntegrityForBrand(Long id) {
        if (brandRepository.existsById(id)) {
            BrandEntity brand = brandRepository.getOne(id);
            return !carRepository.existsByBrand(brand);
        }
        return true;
    }

}

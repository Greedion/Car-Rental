package com.project.service.brandservice;

import com.project.model.Brand;
import com.project.entity.BrandEntity;
import com.project.repository.BrandRepository;
import com.project.repository.CarRepository;
import com.project.utils.BrandMapper;
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

    public ResponseEntity<List<Brand>> getAllBrands() {
        List<BrandEntity> brandsFromDatabase = brandRepository.findAll();
        List<Brand> brandsDTA = new ArrayList<>();

        for (BrandEntity x : brandsFromDatabase
        ) {
            brandsDTA.add(BrandMapper.mapperFromBrandEntityToBrandDTA(x));
        }
        return ResponseEntity.ok(brandsDTA);
    }

    public ResponseEntity<Brand> addBrand(Brand inputBrand) {
        BrandEntity brandForSave = BrandMapper.mapperFromBrandDTAToBrandEntity(inputBrand);
        Brand responseObject = BrandMapper.mapperFromBrandEntityToBrandDTA(brandRepository.save(brandForSave));
        return ResponseEntity.ok(responseObject);
    }

    public ResponseEntity<Brand> modifyBrand(Brand inputBrand) {
        if (brandRepository.existsById(Long.parseLong(inputBrand.getId()))) {
            Optional<BrandEntity> brandForSave = brandRepository.findById(Long.parseLong(inputBrand.getId()));
            if (brandForSave.isPresent() && inputBrand.getBrand() != null &&
                    !inputBrand.getBrand().equals("") &&
                    !inputBrand.getBrand().equals(brandForSave.get().getBrand())) {
                brandForSave.get().setBrand(inputBrand.getBrand());
                Brand returnObject = BrandMapper.mapperFromBrandEntityToBrandDTA(
                        brandRepository.save(brandForSave.get()));
                return ResponseEntity.ok(returnObject);
            }
        }
        logger.error("Attempt to modify the brand using a non-existent id");
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<Brand> getOneByID(String id) {
        if (brandRepository.existsById(Long.parseLong(id))) {
            Optional<BrandEntity> brandObject = brandRepository.findById(Long.parseLong(id));
            if (brandObject.isPresent())
                return ResponseEntity.ok(BrandMapper.mapperFromBrandEntityToBrandDTA(brandObject.get()));
        }
        logger.error("Attempt to get the brand using a non-existent id");
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<HttpStatus> deleteByID(String id) {
        if (brandRepository.existsById(Long.parseLong(id))) {
            Optional<BrandEntity> brandObject = brandRepository.findById(Long.parseLong(id));
            if (brandObject.isPresent()) {
                if (checkConstraintsOfIntegrityForBrand(Long.parseLong(id))) {
                    brandRepository.delete(brandObject.get());
                    return ResponseEntity.ok().build();
                } else {
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

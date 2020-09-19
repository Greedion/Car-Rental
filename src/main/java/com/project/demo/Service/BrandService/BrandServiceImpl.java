package com.project.demo.Service.BrandService;

import com.project.demo.DataTransferObject.BrandDTA;
import com.project.demo.Entity.BrandEntity;
import com.project.demo.Respository.BrandRepository;
import com.project.demo.Utils.BrandMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BrandServiceImpl implements BrandInterface {

    BrandRepository brandRepository;

    @Autowired
    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }


    public ResponseEntity<?> getAllBrands() {
        List<BrandEntity> brandsFromDatabase = brandRepository.findAll();
        List<BrandDTA> brandsDTA = new ArrayList<>();

        for (BrandEntity x : brandsFromDatabase
        ) {
            brandsDTA.add(BrandMapper.mapperFromBrandEntityToBrandDTA(x));
        }
        return ResponseEntity.ok(brandsDTA);
    }

    public ResponseEntity<?> addBrand(BrandDTA inputBrandDTA) {
        BrandEntity brandForSave = BrandMapper.mapperFromBrandDTAToBrandEntity(inputBrandDTA);
        brandRepository.save(brandForSave);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> modifyBrand(BrandDTA inputBrandDTA) {
        if (brandRepository.existsById(Long.parseLong(inputBrandDTA.getId()))) {
            Optional<BrandEntity> brandForSave = brandRepository.findById(Long.parseLong(inputBrandDTA.getId()));
            if (brandForSave.isPresent()) {
                if (inputBrandDTA.getBrand() != null && !inputBrandDTA.getBrand().equals("")) {
                    if (!inputBrandDTA.getBrand().equals(brandForSave.get().getBrand())) {
                        brandForSave.get().setBrand(inputBrandDTA.getBrand());
                        brandRepository.save(brandForSave.get());
                        return ResponseEntity.ok().build();
                    }
                }
            }
        }
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<?> getOneByID(String id) {
        if (brandRepository.existsById(Long.parseLong(id))) {
            Optional<BrandEntity> brandObject = brandRepository.findById(Long.parseLong(id));
            if (brandObject.isPresent())
                return ResponseEntity.ok(BrandMapper.mapperFromBrandEntityToBrandDTA(brandObject.get()));
        }
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<?> deleteByID(String id) {
        if (brandRepository.existsById(Long.parseLong(id))) {
            Optional<BrandEntity> brandObject = brandRepository.findById(Long.parseLong(id));
            if (brandObject.isPresent()) {
                brandRepository.delete(brandObject.get());
                return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }

}

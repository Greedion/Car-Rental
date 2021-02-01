package com.project.Service.BrandService;
import com.project.DataTransferObject.BrandDTO;
import com.project.Entity.BrandEntity;
import com.project.Repository.BrandRepository;
import com.project.Utils.BrandMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BrandServiceImpl implements BrandInterface {

    private final BrandRepository brandRepository;

    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    public ResponseEntity<?> getAllBrands() {
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

package com.project.Service.BrandService;
import com.project.DataTransferObject.BrandDTO;
import org.springframework.http.ResponseEntity;

public interface BrandInterface {

    ResponseEntity<?> getAllBrands();

    ResponseEntity<?> addBrand(BrandDTO inputBrandDTO);

    ResponseEntity<?> modifyBrand(BrandDTO inputBrandDTO);

    ResponseEntity<?> getOneByID(String id);

    ResponseEntity<?> deleteByID(String id);
}

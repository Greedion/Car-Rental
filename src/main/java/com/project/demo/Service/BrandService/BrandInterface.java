package com.project.demo.Service.BrandService;

import com.project.demo.DataTransferObject.BrandDTA;
import org.springframework.http.ResponseEntity;

public interface BrandInterface {
    ResponseEntity<?> getAllBrands();
    ResponseEntity<?> addBrand(BrandDTA inputBrandDTA);
    ResponseEntity<?> modifyBrand(BrandDTA inputBrandDTA);
    ResponseEntity<?> getOneByID(String id);
    ResponseEntity<?> deleteByID(String id);
}

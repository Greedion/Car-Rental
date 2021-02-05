package com.project.service.brandservice;

import com.project.model.Brand;
import com.project.exception.ServiceOperationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BrandInterface {

    ResponseEntity<List<Brand>> getAllBrands();

    ResponseEntity<?> addBrand(Brand inputBrand);

    ResponseEntity<?> modifyBrand(Brand inputBrand);

    ResponseEntity<Brand> getOneByID(String id);

    ResponseEntity<HttpStatus> deleteByID(String id) throws ServiceOperationException;
}

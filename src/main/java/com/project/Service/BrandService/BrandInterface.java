package com.project.Service.BrandService;
import com.project.DataTransferObject.BrandDTO;
import com.project.Exception.ServiceOperationException;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BrandInterface {

    ResponseEntity<List<BrandDTO>> getAllBrands();

    ResponseEntity<?> addBrand(BrandDTO inputBrandDTO);

    ResponseEntity<?> modifyBrand(BrandDTO inputBrandDTO);

    ResponseEntity<BrandDTO> getOneByID(String id);

    ResponseEntity<?> deleteByID(String id) throws ServiceOperationException;
}

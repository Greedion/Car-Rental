package com.project.Controller;

import com.project.DataTransferObject.BrandDTO;
import com.project.Service.BrandService.BrandServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/brand/")
public class BrandController {

    private final BrandServiceImpl brandService;

    public BrandController(BrandServiceImpl brandService) {
        this.brandService = brandService;
    }

    @GetMapping(value = "getAll")
    ResponseEntity<?> getAllBrand() {
        return brandService.getAllBrands();
    }

    @PostMapping(value = "add")
    ResponseEntity<?> addBrand(@Valid @RequestBody BrandDTO inputBrandDTO, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(hadErrors(result), HttpStatus.BAD_REQUEST);

        }
        return brandService.addBrand(inputBrandDTO);
    }

    @PutMapping(value = "update")
    ResponseEntity<?> updateBrand(@Valid @RequestBody BrandDTO inputBrandDTO, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(hadErrors(result), HttpStatus.BAD_REQUEST);
        }
        return brandService.modifyBrand(inputBrandDTO);
    }

    @GetMapping(value = "getOne/{id}")
    ResponseEntity<?> getOneByID(@PathVariable String id) {
        return brandService.getOneByID(id);
    }

    @DeleteMapping(value = "delete/{id}")
    ResponseEntity<?> deleteByID(@PathVariable String id) {
        return brandService.deleteByID(id);
    }

    private Map<String, String> hadErrors(BindingResult result) {
        Map<String, String> errorMap = new HashMap<>();

        for (FieldError error : result.getFieldErrors()
        ) {
            errorMap.put(error.getField(), error.getDefaultMessage());
        }
        return errorMap;
    }
}
package com.project.Controller;

import com.project.DataTransferObject.BrandDTO;
import com.project.Repository.BrandRepository;
import com.project.Service.BrandService.BrandServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/brand")
public class BrandController {

    private final Logger logger = LoggerFactory.getLogger(BrandController.class);
    private final BrandServiceImpl brandService;
    private final BrandRepository brandRepository;

    public BrandController(BrandServiceImpl brandService, BrandRepository brandRepository) {
        this.brandService = brandService;
        this.brandRepository = brandRepository;
    }

    @PreAuthorize("permitAll()")
    @GetMapping(produces = "application/json")
    ResponseEntity<List<BrandDTO>> getAll() {
        return brandService.getAllBrands();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    ResponseEntity<BrandDTO> getOneByID(@PathVariable String id) throws ResponseStatusException {
        try {
            if (brandRepository.existsById(Long.parseLong(id))) {
                return brandService.getOneByID(id);
            } else {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Expected data not found.");
            }
        } catch (NumberFormatException e) {
            logger.error("Attempt parse String to Long");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attempt parse String to Long.");
        }
    }

    @PostMapping(produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> addBrand(@Valid @RequestBody BrandDTO inputBrandDTO, BindingResult result) {
        if (result.hasErrors()) {
            logger.error("Attempt to add Brand with wrong data structure.");
            return new ResponseEntity<>(hadErrors(result), HttpStatus.BAD_REQUEST);
        }
        return brandService.addBrand(inputBrandDTO);
    }

    @PutMapping(produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> updateBrand(@Valid @RequestBody BrandDTO inputBrandDTO, BindingResult result) {
        if (inputBrandDTO == null) {
            logger.error("Attempt update brand with empty input data.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attempt update brand with empty input data.");
        } else if (inputBrandDTO.getBrand() == null || inputBrandDTO.getId() == null) {
            logger.error("Attempt update brand with empty input data.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attempt update brand with empty input data.");
        } else if (result.hasErrors()) {
            logger.error("Attempt to update Brand with wrong data structure.");
            return new ResponseEntity<>(hadErrors(result), HttpStatus.BAD_REQUEST);
        }
        return brandService.modifyBrand(inputBrandDTO);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> deleteByID(@PathVariable String id) {
        try {
            if (brandRepository.existsById(Long.parseLong(id))) {
                return brandService.deleteByID(id);
            } else {
                throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Expected data not found.");
            }
        } catch (NumberFormatException e) {
            logger.error("Attempt parse String to Long");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attempt parse String to Long.");
        }
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

package com.project.controller;

import com.project.model.Brand;
import com.project.exception.ServiceOperationException;
import com.project.model.BrandName;
import com.project.repository.BrandRepository;
import com.project.service.brandservice.BrandServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/brand")
public class BrandController {

    private final Logger logger = LoggerFactory.getLogger(BrandController.class);
    private final BrandServiceImpl brandService;
    private final BrandRepository brandRepository;

    private static final String ID_COULD_NOT_BE_NULL = "Id could not be null";
    private static final String EXPECTED_DATA_NOT_FOUND = "Attempt to remove brand by id that does not exist in database.";
    private static final String NOT_FOUND_EXCEPTION = "Attempt to get brand by id that does not exist in database.";
    private static final String PARSE_EXCEPTION = "Attempt parse String to Long.";

    public BrandController(BrandServiceImpl brandService,
                           BrandRepository brandRepository) {
        this.brandService = brandService;
        this.brandRepository = brandRepository;

    }

    @PreAuthorize("permitAll()")
    @ApiOperation(value = "Get all brand.")
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Brand>> getAll() {
        return brandService.getAllBrands();
    }

    @ApiOperation(value = "Get a single brand by id.")
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Brand> getOneByID(@PathVariable(name = "id") String id) {
        try {
            if (id == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ID_COULD_NOT_BE_NULL);
            } else if (brandRepository.existsById(Long.parseLong(id))) {
                return brandService.getOneByID(id);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, NOT_FOUND_EXCEPTION);
            }
        } catch (NumberFormatException e) {
            logger.error(PARSE_EXCEPTION);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, PARSE_EXCEPTION);
        }
    }

    @ApiOperation(value = "Add brand.", notes = "Needed authorization from Admin account")
    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Brand> addBrand(@Valid BrandName brand) {
        return brandService.addBrand(new Brand("0", brand.getBrand()));
    }


    @ApiOperation(value = "Update brand.", notes = "Needed authorization from Admin account")
    @PutMapping(value = "/{inputId}", produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Brand> updateBrand(@Valid @RequestBody Brand inputBrand, @PathVariable String inputId) {
        inputBrand.setId(Optional.ofNullable(inputId).orElse("0"));
        return brandService.modifyBrand(inputBrand);
    }

    @ApiOperation(value = "Remove brand.", notes = "Needed authorization from Admin account")
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteByID(@PathVariable String id) {
        try {
            if (id == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ID_COULD_NOT_BE_NULL);
            } else if (brandRepository.existsById(Long.parseLong(id))) {
                return brandService.deleteByID(id);
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, EXPECTED_DATA_NOT_FOUND);
            }
        } catch (NumberFormatException | ServiceOperationException e) {
            logger.error(PARSE_EXCEPTION);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, PARSE_EXCEPTION);
        }
    }

}

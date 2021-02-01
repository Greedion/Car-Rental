package com.project.demo.Controller;
import com.project.demo.DataTransferObject.BrandDTA;
import com.project.demo.Service.BrandService.BrandServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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

    final
    private BrandServiceImpl brandService;


    public BrandController(BrandServiceImpl brandService) {
        this.brandService = brandService;
    }

    @GetMapping(value = "getAll")
    ResponseEntity<?> getAllBrand() {
        return brandService.getAllBrands();
    }

    @PostMapping(value = "add")
    ResponseEntity<?> addBrand(@Valid @RequestBody BrandDTA inputBrandDTA, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(hadErrors(result), HttpStatus.BAD_REQUEST);
        }
        return brandService.addBrand(inputBrandDTA);
    }

    @PutMapping(value = "update")
    ResponseEntity<?> updateBrand(@Valid @RequestBody BrandDTA inputBrandDTA, BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(hadErrors(result), HttpStatus.BAD_REQUEST);
        }
        return brandService.modifyBrand(inputBrandDTA);
    }

    @PostMapping(value = "getOne")
    ResponseEntity<?> getOneByID(@RequestParam String id) {
        return brandService.getOneByID(id);
    }

    @DeleteMapping(value = "delete")
    ResponseEntity<?> deleteByID(@RequestParam String id) {
        return brandService.deleteByID(id);
    }

    Map<String, String> hadErrors(BindingResult result) {
        Map<String, String> errorMap = new HashMap<>();

        for (FieldError error : result.getFieldErrors()
        ) {
            errorMap.put(error.getField(), error.getDefaultMessage());
        }
        return errorMap;
    }
}

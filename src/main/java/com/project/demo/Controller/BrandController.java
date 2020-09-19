package com.project.demo.Controller;

import com.project.demo.DataTransferObject.BrandDTA;
import com.project.demo.Service.BrandService.BrandServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("brand")
public class BrandController {

    BrandServiceImpl brandService;

    @Autowired
    public BrandController(BrandServiceImpl brandService) {
        this.brandService = brandService;
    }

    @GetMapping(value = "getAll")
    ResponseEntity<?> getAllBrand(){
        return brandService.getAllBrands();
    }

    @PostMapping(value = "add")
    ResponseEntity<?> addBrand(@RequestBody BrandDTA inputBrandDTA){
        return brandService.addBrand(inputBrandDTA);
    }

    @PutMapping(value = "update")
    ResponseEntity<?> updateBrand(@RequestBody BrandDTA inputBrandDTA){
        return brandService.modifyBrand(inputBrandDTA);
    }

    @PostMapping(value = "getone")
    ResponseEntity<?> getOneByID(@RequestParam String id){
        return brandService.getOneByID(id);
    }

    @DeleteMapping(value = "delete")
    ResponseEntity<?> deleteByID(@RequestParam String id){
        return brandService.deleteByID(id);
    }
}

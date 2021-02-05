package com.project.utils;

import com.project.entity.BrandEntity;
import com.project.model.Brand;

public class BrandMapper {

    private BrandMapper(){}

    public static BrandEntity mapperFromBrandDTAToBrandEntity(Brand inputBrand) {
        if (inputBrand.getId() != null && !inputBrand.getId().equals(""))
            return new BrandEntity(Long.parseLong(inputBrand.getId()), inputBrand.getBrand());
        else return new BrandEntity(null, inputBrand.getBrand());
    }

    public static Brand mapperFromBrandEntityToBrandDTA(BrandEntity inputBrandEntity) {
        return new Brand(String.valueOf(inputBrandEntity.getId()), inputBrandEntity.getBrand());
    }
}

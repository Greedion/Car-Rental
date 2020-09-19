package com.project.demo.Utils;


import com.project.demo.DataTransferObject.BrandDTA;
import com.project.demo.Entity.BrandEntity;

public class BrandMapper {

    public static BrandEntity mapperFromBrandDTAToBrandEntity(BrandDTA inputBrandDTA){
        if(inputBrandDTA.getId() != null && !inputBrandDTA.getId().equals(""))
        return new BrandEntity(Long.parseLong(inputBrandDTA.getId()), inputBrandDTA.getBrand());
        else return new BrandEntity(null, inputBrandDTA.getBrand());
    }

    public static BrandDTA mapperFromBrandEntityToBrandDTA(BrandEntity inputBrandEntity){
        return new BrandDTA(String.valueOf(inputBrandEntity.getId()), inputBrandEntity.getBrand());
    }
}

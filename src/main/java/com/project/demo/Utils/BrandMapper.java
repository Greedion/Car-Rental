package com.project.demo.Utils;
import com.project.demo.DataTransferObject.BrandDTO;
import com.project.demo.Entity.BrandEntity;

public class BrandMapper {

    public static BrandEntity mapperFromBrandDTAToBrandEntity(BrandDTO inputBrandDTO) {
        if (inputBrandDTO.getId() != null && !inputBrandDTO.getId().equals(""))
            return new BrandEntity(Long.parseLong(inputBrandDTO.getId()), inputBrandDTO.getBrand());
        else return new BrandEntity(null, inputBrandDTO.getBrand());
    }

    public static BrandDTO mapperFromBrandEntityToBrandDTA(BrandEntity inputBrandEntity) {
        return new BrandDTO(String.valueOf(inputBrandEntity.getId()), inputBrandEntity.getBrand());
    }
}

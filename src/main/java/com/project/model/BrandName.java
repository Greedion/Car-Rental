package com.project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BrandName {

    @NotBlank(message = "{Brand.brand.notBlank}")
    @NotNull(message = "{Brand.brand.notEmpty}")
    @NotEmpty(message = "{Brand.brand.notNull}")
    private String brand;
}


package com.project.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class BrandName {

    @NotNull(message = "Brand can't be null")
    @NotEmpty(message = "Brand can't be empty")
    private String brand;

    public BrandName(final String brand) {
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(final String brand) {
        this.brand = brand;
    }
}

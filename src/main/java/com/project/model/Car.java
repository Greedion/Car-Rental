package com.project.model;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Car implements Serializable {

    @Pattern(regexp = "^[0-9]*$", message = "Accept only digits")
    private String id;

    @NotNull(message = "Brand can't be null")
    @NotEmpty(message = "Brand can't be empty")
    private String brand;

    @NotNull(message = "Description can't be null")
    @NotEmpty(message = "Description can't be empty")
    private String description;

    @NotNull(message = "PricePerHour can't be null")
    @NotEmpty(message = "PricePerHour can't be empty")
    @Pattern(regexp = "^\\d+(\\.\\d+)?$", message = "Accepts only integers or floating point numbers")
    private String pricePerHour;
}

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

    @Pattern(regexp = "^[0-9]*$", message = "{Car.id.digit}")
    private String id;

    @NotNull(message = "{Car.brand.notNull}")
    @NotEmpty(message = "{Car.brand.notEmpty}")
    private String brand;

    @NotNull(message = "{Car.description.notNull}")
    @NotEmpty(message = "{Car.description.notEmpty}")
    private String description;

    @NotNull(message = "{Car.pricePerHour.notNull}")
    @NotEmpty(message = "{Car.pricePerHour.notEmpty}")
    @Pattern(regexp = "^\\d+(\\.\\d+)?$", message = "{Car.pricePerHour.pattern}")
    private String pricePerHour;
}

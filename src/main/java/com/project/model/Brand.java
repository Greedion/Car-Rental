package com.project.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Brand implements Serializable {

    @Pattern(regexp = "^[0-9]*$", message = "{Brand.id.pattern}")
    private String id;

    @NotBlank(message = "{Brand.brand.notBlank}")
    @NotNull(message = "{Brand.brand.notEmpty}")
    @NotEmpty(message = "{Brand.brand.notNull}")
    private String brand;
}

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

    @Pattern(regexp = "^[0-9]*$", message = "Accept only digits")
    private String id;

    @NotBlank(message = "Password cannot be blank.")
    @NotNull(message = "Brand can't be null")
    @NotEmpty(message = "Brand can't be empty")
    private String brand;

}

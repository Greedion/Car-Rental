package com.project.DataTransferObject;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
public class BrandDTO implements Serializable {

    @Pattern(regexp = "^[0-9]*$", message = "Accept only digits")
    private String id;

    @NotNull(message = "Brand can't be null")
    @NotEmpty(message = "Brand can't be empty")
    private String brand;

}

package com.project.model;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    @NotBlank(message = "Username cannot be blank.")
    @NotNull(message = "Username cannot be null.")
    @NotEmpty(message = "Username cannot be empty.")
    @Size(min = 3, max = 13, message = "Required length 6-13 characters")
    private String username;

    @NotBlank(message = "Password cannot be blank.")
    @NotNull(message = "Password cannot be null.")
    @NotEmpty(message = "Password cannot be empty.")
    @Size(min = 3, max = 13, message = "Required length 6-13 characters")
    private String password;
}

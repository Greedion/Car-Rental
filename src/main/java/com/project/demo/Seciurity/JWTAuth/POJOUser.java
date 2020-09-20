package com.project.demo.Seciurity.JWTAuth;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;


@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class POJOUser implements Serializable {

    @NotNull(message = "Username can't be null")
    @NotEmpty(message = "Username can't be empty")
    @Pattern(regexp = "^\\w+\\d+", message = "Incorrect formula for username. Required char and digit")
    @Size(min = 6, max = 13, message = "Required length 6-13 characters")
    private String username;

    @NotNull(message = "Password can't be null")
    @NotEmpty(message = "Password can't be empty")
    @Pattern(regexp = "^\\w+\\d+", message = "Incorrect formula for password")
    @Size(min = 6, max = 13, message = "Required length 6-13 characters")
    private String password;
}

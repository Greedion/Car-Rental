package com.project.POJO;
import lombok.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class POJOUser implements Serializable {

    @NotNull(message = "Username can't be null")
    @NotEmpty(message = "Username can't be empty")
    @Size(min = 3, max = 13, message = "Required length 6-13 characters")
    private String username;

    @NotNull(message = "Password can't be null")
    @NotEmpty(message = "Password can't be empty")
    @Size(min = 3, max = 13, message = "Required length 6-13 characters")
    private String password;
}

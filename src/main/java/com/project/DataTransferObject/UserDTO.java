package com.project.DataTransferObject;
import lombok.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter
@Getter
public class UserDTO implements Serializable {
    @Pattern(regexp = "^[0-9]*$", message = "Accept only digits")
    private String id;

    @NotNull(message = "Username can't be null")
    @NotEmpty(message = "Username can't be empty")
    @Size(min = 6, max = 13, message = "Required length 6-13 characters")
    private String username;

    @NotNull(message = "Password can't be null")
    @NotEmpty(message = "Password can't be empty")
    @Size(min = 6, max = 13, message = "Required length 6-13 characters")
    private String password;

    @NotNull(message = "Role can't be null")
    @NotEmpty(message = "Role can't be empty")
    @Pattern(regexp = "^[0-9]*$", message = "Accept only digits")
    private String role;

    @NotNull(message = "PricePerHour can't be null")
    @NotEmpty(message = "PricePerHour can't be empty")
    @Pattern(regexp = "^\\d+(\\.\\d+)?$", message = "Accepts only integers or floating point numbers")
    private String moneyOnTheAccount;

}
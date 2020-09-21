package com.project.demo.DataTransferObject;
import lombok.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter
@Getter
public class LoanDTA implements Serializable {

    @Pattern(regexp = "^[0-9]*$", message = "Accept only digits")
    String id;

    @NotNull(message = "CarID can't be null")
    @NotEmpty(message = "CarID can't be empty")
    @Pattern(regexp = "^[0-9]*$", message = "Accept only digits")
    String carID;

    @NotNull(message = "StartOfLoan can't be null")
    @NotEmpty(message = "StartOfLoan can't be empty")
    @Pattern(regexp = "(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2})", message = "Date requires format YYYY-MM-DD HH:MM:SS")
    String startOfLoan;

    @NotNull(message = "EndOfLoan can't be null")
    @NotEmpty(message = "EndOfLoan can't be empty")
    @Pattern(regexp = "(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2})", message = "Date requires format YYYY-MM-DD HH:MM:SS")
    String endOfLoan;

    @NotNull(message = "Username can't be null")
    @NotEmpty(message = "Username can't be empty")
    String username;
}

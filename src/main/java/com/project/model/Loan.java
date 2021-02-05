package com.project.model;
import lombok.*;
import javax.validation.constraints.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Loan implements Serializable {

    @NotEmpty(message = "Car id can't be empty")
    @NotNull(message = "Car id can't be null")
    @Pattern(regexp = "^[0-9]*$", message = "Accept only digit")
    private String carID;

    @NotEmpty(message = "Start date can't be empty")
    @NotNull(message = "Start date  can't be null")
    @Pattern(regexp = "^\\d\\d\\d\\d-(0?[1-9]|1[0-2])-(0?[1-9]|[12][0-9]|3[01]) (00|[0-9]|1[0-9]|2[0-3]):([0-9]|[0-5][0-9]):([0-9]|[0-5][0-9])$",
            message = "Accept only data-time format like 2010-01-11 13:57:24")
    private String startOfLoan;

    @NotEmpty(message = "End date can't be empty")
    @NotNull(message = "End date  can't be null")
    @Pattern(regexp = "^\\d\\d\\d\\d-(0?[1-9]|1[0-2])-(0?[1-9]|[12][0-9]|3[01]) (00|[0-9]|1[0-9]|2[0-3]):([0-9]|[0-5][0-9]):([0-9]|[0-5][0-9])$",
            message = "Accept only data-time format like 2010-01-11 13:57:24")
    private String endOfLoan;
}

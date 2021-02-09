package com.project.model;
import lombok.*;
import javax.validation.constraints.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Loan implements Serializable {

    @NotEmpty(message = "{Loan.carID.notEmpty}")
    @NotNull(message = "{Loan.carID.notNull}")
    @NotBlank(message = "{Loan.carID.notBlank}")
    @Pattern(regexp = "^[0-9]*$", message = "{Loan.carID.pattern}")
    private String carID;

    @NotEmpty(message = "{Loan.startOfLoan.notEmpty}")
    @NotNull(message = "{Loan.startOfLoan.notNull}")
    @NotBlank(message = "{Loan.startOfLoan.notBlank}")
    @Pattern(regexp = "^\\d\\d\\d\\d-(0?[1-9]|1[0-2])-(0?[1-9]|[12][0-9]|3[01]) (00|[0-9]|1[0-9]|2[0-3]):([0-9]|[0-5][0-9]):([0-9]|[0-5][0-9])$",
            message = "{Loan.startOfLoan.pattern}")
    private String startOfLoan;

    @NotEmpty(message = "{Loan.endOfLoan.notEmpty}")
    @NotNull(message = "{Loan.endOfLoan.notNull}")
    @NotBlank(message = "{Loan.endOfLoan.notBlank}")
    @Pattern(regexp = "^\\d\\d\\d\\d-(0?[1-9]|1[0-2])-(0?[1-9]|[12][0-9]|3[01]) (00|[0-9]|1[0-9]|2[0-3]):([0-9]|[0-5][0-9]):([0-9]|[0-5][0-9])$",
            message = "{Loan.endOfLoan.pattern}")
    private String endOfLoan;
}

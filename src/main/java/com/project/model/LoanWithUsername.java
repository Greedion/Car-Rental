package com.project.model;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoanWithUsername implements Serializable {

    @Pattern(regexp = "^[0-9]*$", message = "{LoanWithUsername.id.pattern}")
    private String id;

    @NotNull(message = "{LoanWithUsername.carID.notNull}")
    @NotEmpty(message = "{LoanWithUsername.carID.notEmpty}")
    @NotBlank(message = "{LoanWithUsername.carID.notBlank")
    @Pattern(regexp = "^[0-9]*$", message = "{LoanWithUsername.carID.pattern}")
    private String carID;

    @NotNull(message = "{LoanWithUsername.startOfLoan.notNull}")
    @NotEmpty(message = "{LoanWithUsername.startOfLoan.notEmpty}")
    @NotBlank(message = "{LoanWithUsername.startOfLoan.notBlank")
    @Pattern(regexp = "(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2})", message = "{LoanWithUsername.startOfLoan.pattern}")
    private String startOfLoan;

    @NotNull(message = "{LoanWithUsername.endOfLoan.notNull}")
    @NotEmpty(message = "{LoanWithUsername.endOfLoan.notEmpty}")
    @NotBlank(message = "{LoanWithUsername.endOfLoan.notBlank")
    @Pattern(regexp = "(\\d{4})-(\\d{2})-(\\d{2}) (\\d{2}):(\\d{2}):(\\d{2})", message = "{LoanWithUsername.endOfLoan.pattern}")
    private String endOfLoan;

    @NotBlank(message = "{LoanWithUsername.password.notBlank}")
    @NotNull(message = "{LoanWithUsername.password.notBlank}")
    @NotEmpty(message = "{LoanWithUsername.password.notEmpty}")
    @Length(min = 3, max = 13,message = "{LoanWithUsername.password.size}")
    private String username;
}

package com.project.demo.DataTransferObject;
import lombok.*;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter
@Getter
public class LoanDTA implements Serializable {

    String id;

    String carID;

    String startOfLoan;

    String endOfLoan;

    String username;
}

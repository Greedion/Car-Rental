package com.project.demo.DataTransferObject;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Setter
@Getter
public class LoanDTA {

    String id;

    String carID;

    String startOfLoan;

    String endOfLoan;

    String username;
}

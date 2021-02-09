package com.project.service.rentalservice;

import com.project.model.Loan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface RentalInterface {

    ResponseEntity<HttpStatus> rentalAttempt(Loan inputLoan, String username);

}

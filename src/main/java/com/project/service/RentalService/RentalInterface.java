package com.project.service.rentalservice;

import com.project.exception.ServiceOperationException;
import com.project.model.Loan;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;

public interface RentalInterface {

    ResponseEntity<?> rentalAttempt(Loan inputLoan, String username) throws ParseException, ServiceOperationException;

}

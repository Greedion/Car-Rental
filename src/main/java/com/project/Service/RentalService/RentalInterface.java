package com.project.Service.RentalService;
import com.project.Exception.ServiceOperationException;
import com.project.Model.LoanModel;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;

public interface RentalInterface {

    ResponseEntity<?> rentalAttempt(LoanModel inputLoanModel, String username) throws ParseException, ServiceOperationException;

}

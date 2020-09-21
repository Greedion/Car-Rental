package com.project.demo.Service.RentalService;
import com.project.demo.Model.LoanModel;
import org.springframework.http.ResponseEntity;

public interface RentalInterface {
    ResponseEntity<?> rentalAttempt(LoanModel inputLoanModel, String username);

}

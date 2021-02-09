package com.project.service.loanservice;

import com.project.model.LoanWithUsername;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface LoanInterface {

    ResponseEntity<List<LoanWithUsername>> getAllLoans();

}

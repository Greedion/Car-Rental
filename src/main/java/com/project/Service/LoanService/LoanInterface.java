package com.project.Service.LoanService;
import com.project.DataTransferObject.LoanDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface LoanInterface {

    ResponseEntity<List<LoanDTO>> getAllLoans();

}

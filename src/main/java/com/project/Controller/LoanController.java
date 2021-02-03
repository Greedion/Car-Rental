package com.project.Controller;

import com.project.DataTransferObject.LoanDTO;
import com.project.Service.LoanService.LoanServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/loan")
public class LoanController {

    private final Logger logger = LoggerFactory.getLogger(LoanController.class);
    private final LoanServiceImpl loanService;

    public LoanController(LoanServiceImpl loanService) {
        this.loanService = loanService;
    }

    @GetMapping(produces = "application/json")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    ResponseEntity<List<LoanDTO>> getAllLoan() {
        return loanService.getAllLoans();
    }
}

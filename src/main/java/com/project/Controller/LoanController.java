package com.project.controller;

import com.project.model.LoanWithUsername;
import com.project.service.loanservice.LoanServiceImpl;
import io.swagger.annotations.ApiOperation;
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

    private final LoanServiceImpl loanService;

    public LoanController(LoanServiceImpl loanService) {
        this.loanService = loanService;
    }

    @ApiOperation(value = "Get all loans.", notes = "Needed authentication.")
    @GetMapping(produces = "application/json")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<List<LoanWithUsername>> getAllLoan() {
        return loanService.getAllLoans();
    }
}

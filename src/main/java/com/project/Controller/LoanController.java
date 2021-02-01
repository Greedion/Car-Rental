package com.project.Controller;
import com.project.Service.LoanService.LoanServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/loan/")
public class LoanController {

    private final LoanServiceImpl loanService;

    public LoanController(LoanServiceImpl loanService) {
        this.loanService = loanService;
    }

    @GetMapping(value = "getAll")
    ResponseEntity<?> getAllLoan() {
        return loanService.getAllLoans();
    }
}

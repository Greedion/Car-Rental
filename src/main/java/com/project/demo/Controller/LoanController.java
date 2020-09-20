package com.project.demo.Controller;
import com.project.demo.Service.LoanService.LoanServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("loan")
public class LoanController {

    LoanServiceImpl loanService;

    @Autowired
    public LoanController(LoanServiceImpl loanService) {
        this.loanService = loanService;
    }

    @GetMapping(value = "getAll")
    ResponseEntity<?> getAllLoan() {
        return loanService.getAllLoans();
    }
}

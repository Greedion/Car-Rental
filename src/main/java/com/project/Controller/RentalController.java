package com.project.Controller;

import com.project.Exception.ServiceOperationException;
import com.project.Model.LoanModel;
import com.project.Service.RentalService.RentalServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/loan/")
public class RentalController {

    private final Logger logger = LoggerFactory.getLogger(RentalController.class);
    private final RentalServiceImpl rentalService;

    public RentalController(RentalServiceImpl rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping(value = "createreservation", produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    ResponseEntity<?> rentalAttempt(@RequestBody @Valid LoanModel loanModel, HttpServletRequest httpServletRequest, BindingResult result) throws ServiceOperationException {
        if (loanModel == null) {
            logger.error("Attempt update brand with empty input data.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attempt update brand with empty input data.");
        } else if (loanModel.getStartOfLoan() == null ||
                loanModel.getEndOfLoan() == null ||
                loanModel.getCarID() == null) {
            logger.error("Attempt rental car with empty input data.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Attempt rental car with empty input data.");
        } else if (result.hasErrors()) {
            logger.error("Attempt to update Brand with wrong data structure.");
            return new ResponseEntity<>(hadErrors(result), HttpStatus.BAD_REQUEST);
        } else {
            String username = (String) httpServletRequest.getAttribute("username");
            if (username == null) {
                logger.error("Attempt to create reservation with outdated login token. Or user doesn't exist.");
                return ResponseEntity.badRequest().build();
            }
            return rentalService.rentalAttempt(loanModel, username);
        }
    }

    private Map<String, String> hadErrors(BindingResult result) {
        Map<String, String> errorMap = new HashMap<>();

        for (FieldError error : result.getFieldErrors()
        ) {
            errorMap.put(error.getField(), error.getDefaultMessage());
        }
        return errorMap;
    }
}

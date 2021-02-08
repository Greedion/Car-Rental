package com.project.controller;

import com.project.model.Loan;
import com.project.service.rentalservice.RentalServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/loan/")
public class RentalController {

    private final Logger logger = LoggerFactory.getLogger(RentalController.class);
    private final RentalServiceImpl rentalService;
    private static final String NULL_USERNAME_EXCEPTION = "Attempt to create reservation with outdated login token. Or user doesn't exist.";

    public RentalController(RentalServiceImpl rentalService) {
        this.rentalService = rentalService;
    }

    @ApiOperation(value = "Create reservation for singe car.", notes = "Needed authentication")
    @PostMapping(value = "createreservation", produces = "application/json", consumes = "application/json")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<HttpStatus> rentalAttempt(@RequestBody @Valid Loan loan, HttpServletRequest httpServletRequest) {
            String username = (String) httpServletRequest.getAttribute("username");
            if (username == null) {
                logger.error(NULL_USERNAME_EXCEPTION);
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, NULL_USERNAME_EXCEPTION);
            }
            return rentalService.rentalAttempt(loan, username);

    }


}

package com.project.demo.Controller;

import com.project.demo.Model.LoanModel;
import com.project.demo.Service.RentalService.RentalServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("loan")
public class RentalController {

    RentalServiceImpl rentalService;

    @Autowired
    public RentalController(RentalServiceImpl rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping(value = "createReservation")
    ResponseEntity<?> rentalAttempt(@RequestBody @Valid LoanModel loanModel, HttpServletRequest httpServletRequest, BindingResult result) {
        if(result.hasErrors()){
            Map<String, String> errorMap = new HashMap<>();

            for (FieldError error : result.getFieldErrors()
            ) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
           return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
        }
        String username = (String) httpServletRequest.getAttribute("username");
        return rentalService.rentalAttempt(loanModel, username);
    }


}

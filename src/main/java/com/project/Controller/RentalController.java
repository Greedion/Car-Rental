package com.project.Controller;
import com.project.Exception.ServiceOperationException;
import com.project.Model.LoanModel;
import com.project.Service.RentalService.RentalServiceImpl;
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
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/loan/")
public class RentalController {

    private final RentalServiceImpl rentalService;

    public RentalController(RentalServiceImpl rentalService) {
        this.rentalService = rentalService;
    }

    @PostMapping(value = "createReservation")
    ResponseEntity<?> rentalAttempt(@RequestBody @Valid LoanModel loanModel, HttpServletRequest httpServletRequest, BindingResult result) throws ServiceOperationException {
        ResponseEntity<?> errorMap = validObject(result);
        if (errorMap != null) return errorMap;
        String username = (String) httpServletRequest.getAttribute("username");
        return rentalService.rentalAttempt(loanModel, username);
    }

    static ResponseEntity<?> validObject(BindingResult result) {
        if (result.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError error : result.getFieldErrors()
            ) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            return new ResponseEntity<>(errorMap, HttpStatus.BAD_REQUEST);
        }
        return null;
    }

}

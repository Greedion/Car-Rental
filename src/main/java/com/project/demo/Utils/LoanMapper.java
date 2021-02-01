package com.project.demo.Utils;
import com.project.demo.DataTransferObject.LoanDTO;
import com.project.demo.Entity.LoanEntity;

public class LoanMapper {

    public static LoanDTO mapperFromLoanEntityToLoanDTA(LoanEntity inputLoanEntity){
        return  new LoanDTO(String.valueOf(inputLoanEntity.getId()),
                String.valueOf(inputLoanEntity.getCar().getId()),
                String.valueOf(inputLoanEntity.getStartOfLoan()),
                String.valueOf(inputLoanEntity.getEndOfLoan()),
                inputLoanEntity.getUser().getUsername());
    }
}

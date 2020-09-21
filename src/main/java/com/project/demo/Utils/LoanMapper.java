package com.project.demo.Utils;
import com.project.demo.DataTransferObject.LoanDTA;
import com.project.demo.Entity.LoanEntity;

public class LoanMapper {

    public static LoanDTA mapperFromLoanEntityToLoanDTA(LoanEntity inputLoanEntity){
        return  new LoanDTA(String.valueOf(inputLoanEntity.getId()),
                String.valueOf(inputLoanEntity.getCar().getId()),
                String.valueOf(inputLoanEntity.getStartOfLoan()),
                String.valueOf(inputLoanEntity.getEndOfLoan()),
                inputLoanEntity.getUser().getUsername());
    }
}

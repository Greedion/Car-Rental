package com.project.Utils;
import com.project.DataTransferObject.LoanDTO;
import com.project.Entity.LoanEntity;

public class LoanMapper {

    public static LoanDTO mapperFromLoanEntityToLoanDTA(LoanEntity inputLoanEntity){
        return  new LoanDTO(String.valueOf(inputLoanEntity.getId()),
                String.valueOf(inputLoanEntity.getCar().getId()),
                String.valueOf(inputLoanEntity.getStartOfLoan()),
                String.valueOf(inputLoanEntity.getEndOfLoan()),
                inputLoanEntity.getUser().getUsername());
    }
}

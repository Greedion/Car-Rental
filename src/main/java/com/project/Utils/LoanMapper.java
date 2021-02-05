package com.project.utils;

import com.project.model.LoanWithUsername;
import com.project.entity.LoanEntity;

public class LoanMapper {

    private LoanMapper(){}
    public static LoanWithUsername mapperFromLoanEntityToLoanDTA(LoanEntity inputLoanEntity){
        return  new LoanWithUsername(String.valueOf(inputLoanEntity.getId()),
                String.valueOf(inputLoanEntity.getCar().getId()),
                String.valueOf(inputLoanEntity.getStartOfLoan()),
                String.valueOf(inputLoanEntity.getEndOfLoan()),
                inputLoanEntity.getUser().getUsername());
    }
}

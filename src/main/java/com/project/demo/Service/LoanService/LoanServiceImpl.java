package com.project.demo.Service.LoanService;

import com.project.demo.DataTransferObject.LoanDTA;
import com.project.demo.Entity.LoanEntity;
import com.project.demo.Respository.LoanRepository;
import com.project.demo.Utils.LoanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoanServiceImpl implements LoanInterface{

    LoanRepository loanRepository;

    @Autowired
    public LoanServiceImpl(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public ResponseEntity<?> getAllLoans(){
        List<LoanEntity> listLoanObjects = new ArrayList<>();
        listLoanObjects = loanRepository.findAll();
        List<LoanDTA> objectForReturn = new ArrayList<>();
        for (LoanEntity x: listLoanObjects
             ) {
            objectForReturn.add(LoanMapper.mapperFromLoanEntitytoLoanDTA(x));
        }
        return ResponseEntity.ok(objectForReturn);
    }
}

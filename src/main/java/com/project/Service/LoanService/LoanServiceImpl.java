package com.project.service.loanservice;

import com.project.model.LoanWithUsername;
import com.project.entity.LoanEntity;
import com.project.repository.LoanRepository;
import com.project.utils.LoanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoanServiceImpl implements LoanInterface {

    private final Logger logger = LoggerFactory.getLogger(LoanServiceImpl.class);
    private final LoanRepository loanRepository;

    public LoanServiceImpl(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public ResponseEntity<List<LoanWithUsername>> getAllLoans() {
        List<LoanEntity> listLoanObjects = loanRepository.findAll();
        List<LoanWithUsername> objectForReturn = new ArrayList<>();
        for (LoanEntity x : listLoanObjects
        ) {
            objectForReturn.add(LoanMapper.mapperFromLoanEntityToLoanDTA(x));
        }
        return ResponseEntity.ok(objectForReturn);
    }
}

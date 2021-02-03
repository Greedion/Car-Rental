package com.project.Service.LoanService;

import com.project.DataTransferObject.LoanDTO;
import com.project.Entity.LoanEntity;
import com.project.Repository.LoanRepository;
import com.project.Utils.LoanMapper;
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

    public ResponseEntity<List<LoanDTO>> getAllLoans() {
        List<LoanEntity> listLoanObjects = loanRepository.findAll();
        List<LoanDTO> objectForReturn = new ArrayList<>();
        for (LoanEntity x : listLoanObjects
        ) {
            objectForReturn.add(LoanMapper.mapperFromLoanEntityToLoanDTA(x));
        }
        return ResponseEntity.ok(objectForReturn);
    }
}

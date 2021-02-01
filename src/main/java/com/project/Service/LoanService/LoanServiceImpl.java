package com.project.Service.LoanService;

import com.project.DataTransferObject.LoanDTO;
import com.project.Entity.LoanEntity;
import com.project.Repository.LoanRepository;
import com.project.Utils.LoanMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LoanServiceImpl implements LoanInterface {

    private final LoanRepository loanRepository;

    public LoanServiceImpl(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public ResponseEntity<?> getAllLoans() {
        List<LoanEntity> listLoanObjects = loanRepository.findAll();
        List<LoanDTO> objectForReturn = new ArrayList<>();
        for (LoanEntity x : listLoanObjects
        ) {
            objectForReturn.add(LoanMapper.mapperFromLoanEntityToLoanDTA(x));
        }
        return ResponseEntity.ok(objectForReturn);
    }
}

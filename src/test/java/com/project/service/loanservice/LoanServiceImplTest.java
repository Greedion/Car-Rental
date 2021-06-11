package com.project.service.loanservice;

import com.project.entity.CarEntity;
import com.project.entity.LoanEntity;
import com.project.entity.UserEntity;
import com.project.model.LoanWithUsername;
import com.project.repository.LoanRepository;
import org.junit.jupiter.api.Test;
import java.sql.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class LoanServiceImplTest {

    LoanRepository loanRepository = LoanMockRepository.getLoanMockRepository();
    LoanServiceImpl loanService = new LoanServiceImpl(loanRepository);
    @Test
     void should_return_all_loan_and_username_objects()
    {
        //given
        loanRepository.save(new LoanEntity(1L, new CarEntity(),
                new Date(2000,1,1), new Date(2000,1,1), new UserEntity()));
        loanRepository.save(new LoanEntity(2L, new CarEntity(),
                new Date(2000,1,1), new Date(2000,1,1), new UserEntity()));

        //when
        List<LoanWithUsername> results = loanService.getAllLoans().getBody();

        //then
        assertEquals(results.size(), 2);
        assertEquals(results.get(0).getId(), "1");
        assertEquals(results.get(1).getId(), "2");
    }
}
package com.project.service.rentalservice;

import com.project.entity.CarEntity;
import com.project.entity.UserEntity;
import com.project.entity.UserRoleEntity;
import com.project.model.Loan;
import com.project.repository.CarRepository;
import com.project.repository.LoanRepository;
import com.project.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.*;

class RentalServiceImplTest {

    CarRepository carRepository = CarMockRepository.getCarMockRepository();
    LoanRepository loanRepository = LoanMockRepository.getLoanMockRepository();
    UserRepository userRepository = UserMockRepository.getUserMockRepository();
    private final RentalServiceImpl rentalService =
            new RentalServiceImpl(userRepository,loanRepository,carRepository);

    @Test
    void should_return_bad_request_when_money_are_null() {
        //given
        CarEntity carEntity = new CarEntity(1L, null,"",5.5);
        carRepository.save(carEntity);

        Loan loan = new Loan();
        loan.setCarID("1");

        //when
        ResponseEntity<?> responseEntity = rentalService.rentalAttempt(loan,"Username");

        //then
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    void should_return_bad_request_when_carPrice_is_null() {
        //given
        UserEntity userEntity = new UserEntity("Username", "Password",
                new UserRoleEntity(1L, "Admin"), null);
        CarEntity carEntity = new CarEntity(1L, null,"",null);
        carRepository.save(carEntity);

        Loan loan = new Loan();
        loan.setCarID("1");

        //when
        ResponseEntity<?> responseEntity = rentalService.rentalAttempt(loan,"Username");

        //then
        assertEquals(responseEntity.getStatusCode(), HttpStatus.BAD_REQUEST);
    }
}
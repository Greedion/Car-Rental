package com.project.service.rentalservice;

import com.project.entity.CarEntity;
import com.project.entity.LoanEntity;
import com.project.entity.UserEntity;
import com.project.exception.ExceptionsMessageArchive;
import com.project.exception.ServiceOperationException;
import com.project.model.Loan;
import com.project.repository.CarRepository;
import com.project.repository.LoanRepository;
import com.project.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RentalServiceImpl implements RentalInterface {

    private final Logger logger = LoggerFactory.getLogger(RentalServiceImpl.class);
    private final UserRepository userRepository;
    private final LoanRepository loanRepository;
    private final CarRepository carRepository;

    public RentalServiceImpl(UserRepository userRepository, LoanRepository loanRepository, CarRepository carRepository) {
        this.userRepository = userRepository;
        this.loanRepository = loanRepository;
        this.carRepository = carRepository;
    }

    public ResponseEntity<HttpStatus> rentalAttempt(Loan inputLoan, String username) {
        try {
            BigDecimal money = getMoney(username);
            Double carPrice = getCarPricePerHour(inputLoan.getCarID());
            if (money != null && carPrice != null) {
                long lengthOfTheLoan = countingHowManyHours(inputLoan.getStartOfLoan(), inputLoan.getEndOfLoan());
                MathContext mc = new MathContext(3);
                BigDecimal price = new BigDecimal(lengthOfTheLoan * carPrice);
                if (money.subtract(price, mc).compareTo(BigDecimal.ZERO) > 0 &&
                        timeAreaIsFree(inputLoan.getStartOfLoan(), inputLoan.getEndOfLoan(), inputLoan.getCarID())) {
                    Optional<CarEntity> car = carRepository.findById(Long.parseLong(inputLoan.getCarID()));
                    if (car.isPresent()) {
                        if (createReservation(car.get(), inputLoan.getStartOfLoan(), inputLoan.getEndOfLoan(), username)) {
                            UserEntity user = userRepository.findByUsername(username);
                            subtractMoney(user, price);
                            return ResponseEntity.ok().build();
                        } else {
                            logger.error(ExceptionsMessageArchive.RENTAL_S_FAILED_CREATE_ACCOUNT_EXCEPTION);
                            return ResponseEntity.badRequest().build();
                        }
                    }
                } else {
                    ResponseEntity.badRequest().build();
                }
            }
            logger.error(ExceptionsMessageArchive.RENTAL_S_EMPTY_VALUE_FOR_MONEY_EXCEPTION);
            return ResponseEntity.badRequest().build();

        } catch (ParseException e) {
            logger.error(ExceptionsMessageArchive.RENTAL_S_PARSE_TO_BIG_DECIMAL_EXCEPTION);
            throw new ServiceOperationException(ExceptionsMessageArchive.RENTAL_S_PARSE_EXCEPTION);
        }
    }

    private BigDecimal getMoney(String username) {
        if (userRepository.existsByUsername(username)) {
            return userRepository.findByUsername(username).getMoneyOnTheAccount();
        } else {
            logger.error(ExceptionsMessageArchive.RENTAL_S_GET_MONEY_FROM_NON_EXISTS_USERNAME);
            return null;
        }
    }

    private long countingHowManyHours(String dateStart, String dateStop) throws ParseException {
        Date startDate = getDateFromString(dateStart);
        Date endDate = getDateFromString(dateStop);
        if (startDate != null && endDate != null) {
            long diff = endDate.getTime() - startDate.getTime();
            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000);
            if (diffSeconds == 0 && diffMinutes == 0) return diffHours;
            else return diffHours + 1;
        } else {
            logger.error(ExceptionsMessageArchive.RENTAL_S_PARSE_STRING_TO_DATE_EXCEPTION);
            throw new NumberFormatException(ExceptionsMessageArchive.RENTAL_S_DATE_FORMAT_EXCEPTION);
        }
    }

    private Double getCarPricePerHour(String carID) {
        if (carRepository.existsById(Long.parseLong(carID))) {
            Optional<CarEntity> car = carRepository.findById(Long.parseLong(carID));
            if (car.isPresent()) {
                return car.get().getPricePerHour();
            }
        }
        logger.error(ExceptionsMessageArchive.RENTAL_S_GET_CAR_PRICE_PER_HOUR_FROM_NON_EXISTS_CAR);
        return null;
    }

    private Date getDateFromString(String date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(ExceptionsMessageArchive.RENTAL_S_DATE_PATTERN);
        Date formattedDate;
        formattedDate = format.parse(date);
        return formattedDate;
    }

    private boolean timeAreaIsFree(String dateStart, String dateStop, String carID) throws ParseException {
        Date startDate = getDateFromString(dateStart);
        Date endDate = getDateFromString(dateStop);
        Optional<CarEntity> car = carRepository.findById(Long.parseLong(carID));
        if (car.isPresent()) {
            List<LoanEntity> loans = loanRepository.findAllByCar(car.get());
            for (LoanEntity x : loans
            ) {
                assert endDate != null;
                if ((startDate.after(x.getStartOfLoan()) && startDate.before(x.getEndOfLoan())) ||
                        (endDate.after(x.getStartOfLoan()) && endDate.before(x.getEndOfLoan())) ||
                        (startDate.before(x.getStartOfLoan()) && endDate.after(x.getEndOfLoan()) ||
                                startDate.equals(x.getStartOfLoan()) || endDate.equals(x.getEndOfLoan()))
                ) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean createReservation(CarEntity inputCar, String inputStartDate, String inputEndDate, String inputUsername) throws ParseException {
        Date startDate = getDateFromString(inputStartDate);
        Date endDate = getDateFromString(inputEndDate);
        if (startDate != null && endDate != null &&
                userRepository.existsByUsername(inputUsername)) {
            UserEntity user = userRepository.findByUsername(inputUsername);
            loanRepository.save(new LoanEntity(null, inputCar, startDate, endDate, user));
            return true;
        }
        return false;
    }

    private void subtractMoney(UserEntity user, BigDecimal subtractMoney) {
        BigDecimal money = user.getMoneyOnTheAccount();
        MathContext mc = new MathContext(3);
        BigDecimal newValue;
        newValue = money.subtract(subtractMoney, mc);
        user.setMoneyOnTheAccount(newValue);
        userRepository.save(user);
    }
}

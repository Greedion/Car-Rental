package com.project.Service.RentalService;
import com.project.Entity.CarEntity;
import com.project.Entity.LoanEntity;
import com.project.Entity.UserEntity;
import com.project.Exception.ServiceOperationException;
import com.project.Model.LoanModel;
import com.project.Repository.CarRepository;
import com.project.Repository.LoanRepository;
import com.project.Repository.UserRepository;
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

    private final UserRepository userRepository;
    private final LoanRepository loanRepository;
    private final CarRepository carRepository;

    private final static String DATE_FORMAT_EXCEPTION = "Input Data is empty";
    private final static String DATE_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private final static String PARSE_EXCEPTION = "PARSE FROM METHOD HowManyHours Exception";

    public RentalServiceImpl(UserRepository userRepository, LoanRepository loanRepository, CarRepository carRepository) {
        this.userRepository = userRepository;
        this.loanRepository = loanRepository;
        this.carRepository = carRepository;
    }

    public ResponseEntity<?> rentalAttempt(LoanModel inputLoanModel, String username) throws ServiceOperationException {
        try {
            BigDecimal money = getMoney(username);
            Double carPrice = getCarPricePerHour(inputLoanModel.getCarID());
            if (money != null && carPrice != null) {
                long lengthOfTheLoan = countingHowManyHours(inputLoanModel.getStartOfLoan(), inputLoanModel.getEndOfLoan());
                MathContext mc = new MathContext(3);
                BigDecimal price = new BigDecimal(lengthOfTheLoan * carPrice);
                if (money.subtract(price, mc).compareTo(BigDecimal.ZERO) > 0 &&
                        timeAreaIsFree(inputLoanModel.getStartOfLoan(), inputLoanModel.getEndOfLoan(), inputLoanModel.getCarID())) {
                    Optional<CarEntity> car = carRepository.findById(Long.parseLong(inputLoanModel.getCarID()));
                    if (car.isPresent()) {
                        if (createReservation(car.get(), inputLoanModel.getStartOfLoan(), inputLoanModel.getEndOfLoan(), username)) {
                            UserEntity user = userRepository.findByUsername(username);
                            subtractMoney(user, price);
                            return ResponseEntity.ok().build();
                        } else
                            return ResponseEntity.badRequest().build();
                    }
                } else ResponseEntity.badRequest().build();
            }
            return ResponseEntity.badRequest().build();
        }catch (ParseException e){
           throw new ServiceOperationException(PARSE_EXCEPTION);
        }
    }


    private BigDecimal getMoney(String username) {
        if (userRepository.existsByUsername(username)) {
            UserEntity user = userRepository.findByUsername(username);
            return user.getMoneyOnTheAccount();
        } else return null;
    }

    private long countingHowManyHours(String dateStart, String dateStop) throws ParseException {
        Date startDate = getDateFromString(dateStart);
        Date endDate = getDateFromString(dateStop);
        if (startDate != null && endDate != null) {
            long diff = endDate.getTime() - startDate.getTime();
            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000);
            System.out.println("Time in seconds: " + diffSeconds + " seconds.");
            System.out.println("Time in minutes: " + diffMinutes + " minutes.");
            System.out.println("Time in hours: " + diffHours + " hours.");
            if (diffSeconds == 0 && diffMinutes == 0) return diffHours;
            else return diffHours + 1;
        } else throw new NumberFormatException(DATE_FORMAT_EXCEPTION);
    }

    private Double getCarPricePerHour(String carID) {
        if (carRepository.existsById(Long.parseLong(carID))) {
            Optional<CarEntity> car = carRepository.findById(Long.parseLong(carID));
            if (car.isPresent()) {
                return car.get().getPricePerHour();
            }
        }
        return null;
    }

    private Date getDateFromString(String date) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN);
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
        if (startDate != null && endDate != null) {
            if (userRepository.existsByUsername(inputUsername)) {
                UserEntity user = userRepository.findByUsername(inputUsername);
                loanRepository.save(new LoanEntity(null, inputCar, startDate, endDate, user));
                return true;
            }
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

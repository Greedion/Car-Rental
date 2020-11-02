package com.project.demo.Service.RentalService;
import com.project.demo.Entity.CarEntity;
import com.project.demo.Entity.LoanEntity;
import com.project.demo.Entity.UserEntity;
import com.project.demo.Model.LoanModel;
import com.project.demo.Respository.CarRepository;
import com.project.demo.Respository.LoanRepository;
import com.project.demo.Respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    UserRepository userRepository;
    LoanRepository loanRepository;
    CarRepository carRepository;
    private final String DATE_FORMAT_EXCEPTION = "Input Data is empty";

    @Autowired
    public RentalServiceImpl(UserRepository userRepository, LoanRepository loanRepository, CarRepository carRepository) {
        this.userRepository = userRepository;
        this.loanRepository = loanRepository;
        this.carRepository = carRepository;
    }


    public ResponseEntity<?> rentalAttempt(LoanModel inputLoanModel, String username) {
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
    }


    BigDecimal getMoney(String username) {
        if (userRepository.existsByUsername(username)) {
            UserEntity user = userRepository.findByUsername(username);
            return user.getMoneyOnTheAccount();
        } else return null;
    }


    private long countingHowManyHours(String dateStart, String dateStop) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = format.parse(dateStart);
            endDate = format.parse(dateStop);
        } catch (ParseException e) {
            e.printStackTrace();
        }
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


    Double getCarPricePerHour(String carID) {
        if (carRepository.existsById(Long.parseLong(carID))) {
            Optional<CarEntity> car = carRepository.findById(Long.parseLong(carID));
            if (car.isPresent()) {
                return car.get().getPricePerHour();
            }
        }
        return null;
    }


    boolean timeAreaIsFree(String dateStart, String dateStop, String carID) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = null;
        Date endDate = null;
        try {
            startDate = format.parse(dateStart);
            endDate = format.parse(dateStop);
        } catch (ParseException e) {
            e.printStackTrace();
        }
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

    boolean createReservation(CarEntity inputCar, String inputStartDate, String inputEndDate, String inputUsername) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate;
        Date endDate;
        try {
            startDate = format.parse(inputStartDate);
            endDate = format.parse(inputEndDate);
        } catch (ParseException e) {
            throw new NumberFormatException(DATE_FORMAT_EXCEPTION);
        }

        if (startDate != null && endDate != null) {
            if (userRepository.existsByUsername(inputUsername)) {
                UserEntity user = userRepository.findByUsername(inputUsername);
                loanRepository.save(new LoanEntity(null, inputCar, startDate, endDate, user));
                return true;
            }
        }
        return false;
    }


    void subtractMoney(UserEntity user, BigDecimal subtractMoney) {
        BigDecimal money = user.getMoneyOnTheAccount();
        MathContext mc = new MathContext(3);
        BigDecimal newValue;
        newValue = money.subtract(subtractMoney, mc);
        user.setMoneyOnTheAccount(newValue);
        userRepository.save(user);
    }

}

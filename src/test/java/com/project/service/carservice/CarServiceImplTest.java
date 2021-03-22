package com.project.service.carservice;

import com.project.entity.BrandEntity;
import com.project.entity.CarEntity;
import com.project.model.Brand;
import com.project.model.Car;
import com.project.repository.BrandRepository;
import com.project.repository.CarRepository;
import com.project.service.brandservice.BrandMockRepository;
import com.project.service.brandservice.BrandServiceImpl;
import com.project.utils.CarMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.ServletException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CarServiceImplTest {

    @Test
    @DisplayName("Should return all car's.")
    void shouldReturnAllCar() {
        //given
        CarRepository carRepositoryMock = CarMockRepository.getCarMockRepository();
        carRepositoryMock.save(new CarEntity(1L, new BrandEntity(1L, "Audi")
                , "Description1", 4.5));
        carRepositoryMock.save(new CarEntity(2L, new BrandEntity(2L, "BMW")
                , "Description2", 8.5));
        //and
        CarMapper carMapperMock = getCarMapperMock();

        //and
        CarServiceImpl carService = new CarServiceImpl(carRepositoryMock, null, carMapperMock);

        //when
        ResponseEntity<List<Car>> results = carService.getAllCars();
        List<Car> resultList = results.getBody();

        //then
        assertThat(results).isNotNull();
        assertEquals(HttpStatus.OK, results.getStatusCode());
        assert resultList != null;
        assertEquals(2, resultList.size());
    }

    @Test
    @DisplayName("Should add car.")
    void shouldAddCar() throws ServletException {
        //given
        CarRepository carRepositoryMock = CarMockRepository.getCarMockRepository();
        Car car = new Car("1", "1", "TestDescription", "4.5");
        //and
        CarMapper carMapperMock = getCarMapperMock();

        //and
        CarServiceImpl carService = new CarServiceImpl(carRepositoryMock, null, carMapperMock);

        //when
        ResponseEntity<Car> response = carService.addCar(car);

        //then
        assertThat(response).isNotNull();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(carRepositoryMock.existsById(1L));
        assertEquals(carRepositoryMock.getOne(1L), carMapperMock.mapperFromCarDTOToCarEntity(car));
    }


    CarMapper getCarMapperMock() {
        return new CarMapper(null) {
            private static final String EXCEPTION_ALERT = "Wrong input data format";

            public CarEntity mapperFromCarDTOToCarEntity(Car inputCar) throws ServletException {
                Optional<BrandEntity> brand = Optional.of(new BrandEntity(1L, "AUDI"));
                try {
                    if (inputCar.getId() == null || inputCar.getId().equals(""))
                        return new CarEntity(null, brand.get(), inputCar.getDescription(), Double.parseDouble(inputCar.getPricePerHour()));
                    else
                        return new CarEntity(Long.parseLong(inputCar.getId()), brand.get(), inputCar.getDescription(), Double.parseDouble(inputCar.getPricePerHour()));
                } catch (NumberFormatException e) {
                    throw new ServletException(EXCEPTION_ALERT);
                }
            }

            public Car mapperFroMCarEntityToCarDTO(CarEntity inputCarEntity) {
                return new Car(String.valueOf(inputCarEntity.getId()), String.valueOf(inputCarEntity.getBrand().getId()), inputCarEntity.getDescription(), String.valueOf(inputCarEntity.getPricePerHour()));
            }
        };
    }

}
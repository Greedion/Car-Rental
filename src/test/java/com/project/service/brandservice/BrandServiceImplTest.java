package com.project.service.brandservice;

import com.project.entity.BrandEntity;
import com.project.exception.ExceptionsMessageArchive;
import com.project.model.Brand;
import com.project.repository.BrandRepository;
import com.project.repository.CarRepository;
import com.project.service.brandservice.MockRepository.BrandMockRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BrandServiceImplTest {

    @Test
    @DisplayName("Should return all brand's.")
    void shouldReturnAllBrand() {
        //given
        BrandRepository brandRepositoryMock = mock(BrandRepository.class);
        CarRepository carRepositoryMock = mock(CarRepository.class);
        when(brandRepositoryMock.findAll()).thenReturn(getBrands());

        //and
        BrandServiceImpl brandService = new BrandServiceImpl(brandRepositoryMock, carRepositoryMock);

        //when
        ResponseEntity<List<Brand>> results = brandService.getAllBrands();
        List<Brand> resultList = results.getBody();

        //then
        assertThat(results).isNotNull();
        assertEquals(HttpStatus.OK, results.getStatusCode());
        assert resultList != null;
        assertEquals(3, resultList.size());
    }

    @Test
    @DisplayName("Should add brand.")
    void shouldAddBrand() {
        //given
        BrandRepository brandRepositoryMock = BrandMockRepository.getMockBrandRepository();
        CarRepository carRepositoryMock = mock(CarRepository.class);
        Brand inputBrand = new Brand("1", "TestBrand");
        BrandEntity brandEntity = new BrandEntity(1L, "TestBrand");

        //and
        BrandServiceImpl brandService = new BrandServiceImpl(brandRepositoryMock, carRepositoryMock);

        //when
        ResponseEntity<?> response = brandService.addBrand(inputBrand);

        //then
        assertThat(response).isNotNull();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(brandRepositoryMock.existsById(1L));
    }

    @Test
    @DisplayName("Should return BadRequest status for modifyBrand method when brand non exist in database.")
    void shouldReturnBadRequestForModifyBrandWhenBrandNonExist() {
        //given
        BrandRepository brandRepositoryMock = BrandMockRepository.getMockBrandRepository();
        CarRepository carRepositoryMock = mock(CarRepository.class);

        //and
        BrandServiceImpl brandService = new BrandServiceImpl(brandRepositoryMock, carRepositoryMock);

        //when
        ResponseEntity<Brand> result = brandService.modifyBrand(new Brand("1", "TEST"));

        //then
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    @DisplayName("Should return BadRequest status for modifyBrand method when Brand is null.")
    void shouldReturnBadRequestForModifyBrandWhenBrandIsNull() {
        //given
        BrandRepository brandRepositoryMock = BrandMockRepository.getMockBrandRepository();
        CarRepository carRepositoryMock = mock(CarRepository.class);

        //and
        BrandServiceImpl brandService = new BrandServiceImpl(brandRepositoryMock, carRepositoryMock);

        //when
        ResponseEntity<Brand> result = brandService.modifyBrand(new Brand("1", null));

        //then
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    @DisplayName("Should return BadRequest status for modifyBrand when brand is empty.")
    void shouldReturnBadRequestForModifyBrandWhenBrandIsEmpty() {
        //given
        BrandRepository brandRepositoryMock = BrandMockRepository.getMockBrandRepository();
        CarRepository carRepositoryMock = mock(CarRepository.class);

        //and
        BrandServiceImpl brandService = new BrandServiceImpl(brandRepositoryMock, carRepositoryMock);

        //when
        ResponseEntity<Brand> result = brandService.modifyBrand(new Brand("1", ""));

        //then
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    @DisplayName("Should update a brand by method modifyBrand.")
    void shouldModifyBrandForMethodModifyBrand() {
        //given
        BrandRepository brandRepositoryMock = BrandMockRepository.getMockBrandRepository();
        CarRepository carRepositoryMock = mock(CarRepository.class);

        //and
        BrandServiceImpl brandService = new BrandServiceImpl(brandRepositoryMock, carRepositoryMock);
        Brand modifiedBrand = new Brand("1", "MODIFIEDTEST");

        //when
        brandService.addBrand(new Brand("1", "TEST"));
        ResponseEntity<Brand> result = brandService.modifyBrand(modifiedBrand);

        //then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(brandService.getOneByID("1").getBody(), modifiedBrand);
        assertEquals(1, brandRepositoryMock.count());
    }

    @Test
    @DisplayName("Should return BadRequest status for method getOneById when brand non exist.")
    void shouldReturnBadRequestForGetOneByIdWhenBrandNonExist() {
        //given
        BrandRepository brandRepositoryMock = BrandMockRepository.getMockBrandRepository();
        CarRepository carRepositoryMock = mock(CarRepository.class);

        //and
        BrandServiceImpl brandService = new BrandServiceImpl(brandRepositoryMock, carRepositoryMock);

        //when
        ResponseEntity<Brand> result = brandService.getOneByID("5");

        //then
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    @DisplayName("Should return one brand for Method getOneById.")
    void shouldReturnOneBrandForMethodGetOneById() {
        //given
        BrandRepository brandRepositoryMock = BrandMockRepository.getMockBrandRepository();
        CarRepository carRepositoryMock = mock(CarRepository.class);

        //and
        BrandServiceImpl brandService = new BrandServiceImpl(brandRepositoryMock, carRepositoryMock);
        Brand brand = new Brand("1", "TEST");

        //when
        brandService.addBrand(brand);
        ResponseEntity<Brand> results = brandService.getOneByID("1");
        Brand resultList = results.getBody();

        //then
        assertThat(results).isNotNull();
        assertEquals(HttpStatus.OK, results.getStatusCode());
        assertEquals(resultList, brand);

    }

    @Test
    @DisplayName("Should return BadRequest for method deleteById when brand non exist.")
    void shouldReturnBadRequestForMethodDeleteByIDWhenBrandNonExist() {
        //given
        BrandRepository brandRepositoryMock = BrandMockRepository.getMockBrandRepository();
        CarRepository carRepositoryMock = mock(CarRepository.class);

        //and
        BrandServiceImpl brandService = new BrandServiceImpl(brandRepositoryMock, carRepositoryMock);

        //when
        ResponseEntity<?> result = brandService.deleteByID("5");

        //then
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    @DisplayName("Should throw exception when try remove object with active relation with another objects.")
    void shouldReturnExceptionBecauseRemovedObjectHaveRelationWithOtherObject() {
        //given
        BrandRepository brandRepositoryMock = BrandMockRepository.getMockBrandRepository();
        CarRepository carRepositoryMock = mock(CarRepository.class);

        //and
        BrandServiceImpl brandService = new BrandServiceImpl(brandRepositoryMock, carRepositoryMock);
        when(carRepositoryMock.existsByBrand(any())).thenReturn(true);

        //and
        brandService.addBrand(new Brand("10", "TEST"));

        //when
        Throwable exception = catchThrowable(() -> brandService.deleteByID("10"));

        //then
        assertThat(exception)
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(ExceptionsMessageArchive.BRAND_S_REMOVE_BRAND_WITH_ASSIGNING_BIND);
    }

    @Test
    @DisplayName("Should successful remove a object by method deleteById.")
    void shouldDeleteObject() {
        //given
        BrandRepository brandRepositoryMock = BrandMockRepository.getMockBrandRepository();
        CarRepository carRepositoryMock = mock(CarRepository.class);

        //and
        BrandServiceImpl brandService = new BrandServiceImpl(brandRepositoryMock, carRepositoryMock);
        when(carRepositoryMock.existsByBrand(any())).thenReturn(false);

        //and
        brandService.addBrand(new Brand("10", "TEST"));

        //when
        ResponseEntity<?> result = brandService.deleteByID("10");

        //then
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertThat(brandRepositoryMock.existsById(10L)).isEqualTo(false);
    }


    List<BrandEntity> getBrands() {
        List<BrandEntity> list = new ArrayList<>();
        list.add(new BrandEntity(1L, "AUDI"));
        list.add(new BrandEntity(2L, "BMW"));
        list.add(new BrandEntity(3L, "NISSAN"));
        return list;
    }
}
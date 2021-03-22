package com.project.service.carservice;

import com.project.entity.BrandEntity;
import com.project.entity.CarEntity;
import com.project.repository.CarRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

 class CarMockRepository {

     static CarRepository getCarMockRepository(){
        return new CarRepository() {
            public List<CarEntity> cars = new ArrayList<>();
            @Override
            public Boolean existsByBrand(final BrandEntity brandEntity) {
                for (CarEntity x: cars
                     ) {
                    if(x.getBrand().equals(brandEntity))
                        return true;
                }
                return false;
            }

            @Override
            public List<CarEntity> findAll() {
                return cars;
            }

            @Override
            public List<CarEntity> findAll(final Sort sort) {
                return null;
            }

            @Override
            public List<CarEntity> findAllById(final Iterable<Long> iterable) {
                return null;
            }

            @Override
            public <S extends CarEntity> List<S> saveAll(final Iterable<S> iterable) {
                return null;
            }

            @Override
            public void flush() {

            }

            @Override
            public <S extends CarEntity> S saveAndFlush(final S s) {
                return null;
            }

            @Override
            public void deleteInBatch(final Iterable<CarEntity> iterable) {

            }

            @Override
            public void deleteAllInBatch() {

            }

            @Override
            public CarEntity getOne(final Long aLong) {
                for (CarEntity x: cars
                     ) {
                    if(x.getId().equals(aLong))
                        return x;
                }
                return null;
            }

            @Override
            public <S extends CarEntity> List<S> findAll(final Example<S> example) {
                return null;
            }

            @Override
            public <S extends CarEntity> List<S> findAll(final Example<S> example, final Sort sort) {
                return null;
            }

            @Override
            public Page<CarEntity> findAll(final Pageable pageable) {
                return null;
            }

            @Override
            public <S extends CarEntity> S save(final S s) {
                cars.removeIf(x -> x.getId().equals(s.getId()));
                cars.add(s);
              return s;
            }

            @Override
            public Optional<CarEntity> findById(final Long aLong) {
                for (CarEntity x: cars
                ) {
                    if(x.getId().equals(aLong))
                        return Optional.of(x);
                }
                return Optional.empty();
            }

            @Override
            public boolean existsById(final Long aLong) {
                for (CarEntity x: cars
                ) {
                    if(x.getId().equals(aLong))
                        return true;
                }
                return false;
            }

            @Override
            public long count() {
                return cars.size();
            }

            @Override
            public void deleteById(final Long aLong) {
                cars.removeIf(x -> x.getId().equals(aLong));

            }

            @Override
            public void delete(final CarEntity carEntity) {
                cars.removeIf(x -> x.equals(carEntity));
            }

            @Override
            public void deleteAll(final Iterable<? extends CarEntity> iterable) {

            }

            @Override
            public void deleteAll() {

            }

            @Override
            public <S extends CarEntity> Optional<S> findOne(final Example<S> example) {
                return Optional.empty();
            }

            @Override
            public <S extends CarEntity> Page<S> findAll(final Example<S> example, final Pageable pageable) {
                return null;
            }

            @Override
            public <S extends CarEntity> long count(final Example<S> example) {
                return 0;
            }

            @Override
            public <S extends CarEntity> boolean exists(final Example<S> example) {
                return false;
            }
        };
    }
}
